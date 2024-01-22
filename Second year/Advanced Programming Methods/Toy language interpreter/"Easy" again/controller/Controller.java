package controller;


import exceptions.InterpreterException;
import exceptions.MyException;
import model.ADTs.*;
import model.ProgramState;
import model.values.RefValue;
import model.values.StringValue;
import model.values.Value;
import repo.RepositoryInterface;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private RepositoryInterface repo;
    boolean displayFlag;
    private ExecutorService executor;

    public Controller(RepositoryInterface repo) {
        this.repo = repo;
        this.displayFlag = false;
    }


    public List<Integer> getAllIdsThread() {
        return this.repo.getProgramList().stream().map(ProgramState::getId_thread).collect(Collectors.toList());
    }

    public List<ProgramState> getAllPrograms(){
        return this.repo.getProgramList();
    }

    public void addProgram (ProgramState newProgram){
        this.repo.addProgram(newProgram);
    }


    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inputProgramList) {
        return inputProgramList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public String displayState(ProgramState state) {
        return state.toString();
    }

    public void setDisplayAll(Boolean flag) {
        this.displayFlag = flag;
    }

    public void allSteps() throws MyException {
        this.executor = Executors.newFixedThreadPool(2);
        // remove the completed programs
        List<ProgramState> prgList = removeCompletedPrograms(repo.getProgramList());
        while(prgList.size() > 0){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            // remove the completed programs
            prgList = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        // HERE the repo still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repo in order to change the repo

        // update the repo state
        repo.setProgramList(prgList);
    }

    public void oneStepForAllPrgGUI() throws MyException{
        this.executor= Executors.newFixedThreadPool(2);
        // remove the completed programs
        List<ProgramState> prgList=removeCompletedPrograms(repo.getProgramList());
        repo.setProgramList(prgList);
        if(prgList.size() == 0)
            throw new MyException("Program is done!");
        conservativeGarbageCollector(prgList);
        oneStepForAllPrg(prgList);
        executor.shutdown();
        // HERE the repo still contains at lest one Completed Prg
        //and its List<PrgStat> is not empty, note that oneStepForAllPrg calls for method
        // setPrgList of repo in order to change the repo
        // update the repo state

    }

    public void oneStepForAllPrg(List<ProgramState> programList) throws MyException {
        programList.forEach(state -> {
            //PRINT the PrgState List into the log file
            //-----------------------------------------------------------------------
            try {
                this.repo.logProgramStateExecution(state);
            } catch (MyException exception) {
                // prints the throwable along with other details like the line number and class name where the exception occurred
                exception.printStackTrace();
            }
        });

        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        // prepare the list of callables -> task that is intended to be executed concurrently by a separate thread
        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState p)->(Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        // start the execution of the callables
        // it returns the list of new created PrgStates(namely, threads)
        try {
            List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            programList.addAll(newProgramList);
        } catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }

        // after the execution, print the PrgState List into the log file
        programList.forEach(prg -> {
            try {
                this.repo.logProgramStateExecution(prg);
            } catch (MyException exception) {
                exception.printStackTrace();
            }
        });

        // Save the current programs in the repo
        this.repo.setProgramList(programList);
    }


    private void conservativeGarbageCollector(List<ProgramState> programStateList) {
        // get the heap of each program state and concatenate it
        // there is one HEAP shared by multiple PrgStates and multiple SymbolTables
        var heap = Objects.requireNonNull(
                programStateList.stream()
                        .map(p -> getAddressesFromHeapAndSymbolTable(
                                p.getSymbolTable().valuesAsCollection(),
                                p.getHeap().valuesAsCollection()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null)).collect(Collectors.toList());

        // call safeGarbageCollector for each heap
        programStateList.forEach(programState -> programState.getHeap().setContent(
                safeGarbageCollector(heap, programStateList.get(0).getHeap().getContent())
        ));
    }


    private Map<Integer, Value> safeGarbageCollector(List<Integer> addresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //safe
    private List<Integer> getAddressesFromHeapAndSymbolTable(Collection<Value> symbolTableValue, Collection<Value> heap) {
        return Stream.concat(
                heap.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();}),
                symbolTableValue.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
        ).collect(Collectors.toList());
    }

    public void typeCheck() throws MyException {
        this.repo.getProgramList().get(0).typeCheck();
    }

    //unsafe
//    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues) {
//        /*dai filter ca sa ai doar RefValues, dupa aceea dai .map ca sa mapezi din Value in adresa, dupa colectezi intr o lista*/
//        return symbolTableValues.stream()
//                .filter(v ->v instanceof RefValue)
//                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
//                .collect(Collectors.toList());
//    }

    public MyListInterface<Value> getOutGUI() {return this.getAllPrograms().get(0).getOut();}
    public MyDictionaryInterface<StringValue, BufferedReader> getFileTableGUI() {return this.getAllPrograms().get(0).getFileTable();}
    public MyHeapInterface<Value> getHeapTableGUI() {return this.getAllPrograms().get(0).getHeap();}
}
