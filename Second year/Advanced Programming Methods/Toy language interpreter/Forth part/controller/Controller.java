package controller;

import exceptions.MyException;
import model.programState.ProgramState;
import model.statement.IStatement;
import model.utils.MyIStack;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;
import java.io.IOException;
import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;



public class Controller {
    IRepository repo;
    boolean displayFlag = false;

    public Controller(IRepository repository){
        this.repo = repository;
    }

    public void setDisplayFlag(boolean val){
        this.displayFlag = val;
    }

    public ProgramState oneStep(ProgramState state) throws MyException{
        MyIStack<IStatement> stack = state.getExeStack();
        if (stack.isEmpty()){
            throw new MyException("Program state stack is emptyyyyyyy");
        }
        else {
            IStatement currStatement = stack.pop();
            state.setExeStack(stack);
            return currStatement.execute(state);
        }
    }

    public void allSteps() throws MyException, IOException {
        ProgramState program = this.repo.getCurrentState();
        this.repo.logPrgStateExec();
        display();
        while (!program.getExeStack().isEmpty()){
            oneStep(program);
            this.repo.logPrgStateExec();

            Map<Integer, Value> heapContent= program.getHeap().getContent();
            List<Integer> symbolTableAddresses = this.getAddressesFromSymTable(program.getSymTable().values());
            List<Integer> allReferencedAddresses = this.addIndirectAddresses(symbolTableAddresses, heapContent);
            program.getHeap().setContent(new HashMap<>(this.garbageCollector(allReferencedAddresses, heapContent)));

            display();
        }
        System.out.println("Your result is: " + program.getOut().toString());
    }

    private void display(){
        if (displayFlag){
            System.out.println(this.repo.getCurrentState().toString());
        }
    }

    private List<Integer> getAddressesFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> addIndirectAddresses(List<Integer> addressesFromSymTable, Map<Integer, Value> heap){
        // we go through heapSet again and again and each time we add to the address list new indirection level
        // and new addresses which must be kept
        boolean change = true;
        List<Integer> newAddressList = new ArrayList<>(addressesFromSymTable);  //copy in which we add indirections
        while(change){
            List<Integer> appendingList;
            change= false;

            appendingList = heap.entrySet().stream()
            .filter(e-> e.getValue() instanceof  RefValue)    //check if it is refValue
                    .filter(e->newAddressList.contains(e.getKey()))  //check if our list contains reference to it
                    .map(e-> ((RefValue) e.getValue()).getAddress()) //map the reference to its address
                    .filter(e-> !newAddressList.contains(e))  //check if our list already has that reference
                    .collect(Collectors.toList());

            if (!appendingList.isEmpty()){
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }

    private Map<Integer, Value> garbageCollector(List<Integer> referencedAddresses, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->referencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }



}
