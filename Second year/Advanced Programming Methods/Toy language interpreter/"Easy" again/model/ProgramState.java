package model;

import exceptions.InterpreterException;
import exceptions.MyException;
import model.ADTs.*;
import model.statements.IStatement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class ProgramState {
    private MyStackInterface<IStatement> exeStack;
    private MyDictionaryInterface<String, Value> symbolTable;
    private MyListInterface<Value> out;
    private MyDictionaryInterface<StringValue, BufferedReader> fileTable;
    private MyHeapInterface<Value> heap;
    IStatement originalProgram; // optional field, but good to have
    private static int currentID = 1;
    private int id;

    public synchronized void setId() {
        currentID++;
        this.id = currentID;
    }

    public ProgramState(MyStackInterface<IStatement> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out, MyDictionaryInterface<StringValue, BufferedReader> fileTable,
                        MyHeapInterface<Value> heap, IStatement originalProgram) {
        // for main one
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = 1;

        if(this.originalProgram != null) {
            exeStack.push(this.originalProgram);
        }
    }

    public ProgramState(MyStackInterface<IStatement> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out, MyDictionaryInterface<StringValue, BufferedReader> fileTable,
                        MyHeapInterface<Value> heap) {
        // for fork
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;

    }

    public Integer getId_thread() {
        return this.id;
    }

    public Boolean isNotCompleted(){return !this.exeStack.isEmpty();}

    public ProgramState oneStep() throws MyException {
        if(this.exeStack.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStatement currentStatement = this.exeStack.pop();
        return currentStatement.execute(this);
    }

    public void typeCheck() throws MyException{
        originalProgram.typecheck(new MyDictionary<>());
    }

    public MyStackInterface<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyStackInterface<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public MyDictionaryInterface<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(MyDictionaryInterface<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public MyListInterface<Value> getOut() {
        return out;
    }

    public void setOut(MyListInterface<Value> out) {
        this.out = out;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyDictionaryInterface<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyDictionaryInterface<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyHeapInterface<Value> getHeap() {
        return this.heap;
    }

    public void setHeap(MyHeapInterface<Value> heap) {
        this.heap = heap;
    }

    @Override
    public String toString() {
        //", originalProgram=" + originalProgram +

        return ">>> ProgramState: " + "\n----------\n" +
                "* ID: \n" + Integer.toString(this.id) + "\n----------\n" +
                "* exeStack: \n" + exeStack.toString() + "\n----------\n" +
                "* symbolTable: \n" + symbolTable + "\n----------\n" +
                "* out: " + out + "\n----------\n" +
                "* fileTable=" + fileTable.toString() + "\n----------\n" +
                "* heap: " + heap.toString() + "\n----------\n\n";
    }

    public ProgramState(MyStackInterface<IStatement> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out) {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
    }
}
