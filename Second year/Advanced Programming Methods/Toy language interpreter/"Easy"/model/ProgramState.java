package model;

import exceptions.MyException;
import model.adt.*;
import model.statements.IStatement;
import model.values.Value;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.Vector;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> outputList;

    MyIDictionary<StringValue, BufferedReader> fileTable;

    MyIHeap<Integer, Value> heap;

    IStatement originalProgram;

    private final int id;
    static final Vector<Integer> usedIds = new Vector<>();

    public ProgramState(IStatement originalProgram) {
        this.exeStack = new MyStack<>();
        this.symTable = new MyDictionary<>();
        this.outputList = new MyList<>();
        this.fileTable = new FileTable<>();
        this.heap = new MyHeap<>();
        this.originalProgram = originalProgram.deepcopy();
        exeStack.push(originalProgram);

        id = generateNewId();
        usedIds.add(id);
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> outputList, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Integer, Value> heap, IStatement program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = program.deepcopy();
        exeStack.push(program);

        id = generateNewId();
        usedIds.add(id);
    }

    @Override
    public String toString() {
        return  "Id --> " + id + "\n" +
                "ExeStack:\n" + exeStack.toString() +
                "SymbolTable:\n" + symTable.toString() +
                "Heap:\n" + heap.toString() +
                "Out:\n" + outputList.toString() +
                "FileTable:\n" + fileTable.toString() +
                "-------------------------------------------------------------\n\n";
    }


    private int generateNewId(){
        int newId = 1;
        synchronized (usedIds) {
            while (usedIds.contains(newId))
                newId++;
        }
        return newId;
    }
    public ProgramState oneStep() throws MyException {
        if(exeStack.isEmpty()) {
            throw new MyException("Program state stack is empty!");
        }
        IStatement createdStatement = exeStack.pop();
        return createdStatement.execute(this);
    }

    public Boolean isNotCompleted(){ return !exeStack.isEmpty();}
    public MyIList<Value> getOutputList() {
        return outputList;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStatement> newStack) {
        this.exeStack = newStack;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, Value> getHeap(){
        return heap;
    }

    public int getId() {
        return id;
    }
}
