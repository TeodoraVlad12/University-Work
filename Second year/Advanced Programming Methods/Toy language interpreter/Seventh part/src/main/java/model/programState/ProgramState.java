package model.programState;

import exceptions.MyException;
import model.statement.IStatement;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.utils.MyIList;
import model.utils.MyIStack;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStatement originalProgram;
    private MyIHeap<Value> heap;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    private int id;
    private static int baseId;

    public ProgramState(MyIStack<IStatement> stack, MyIDictionary<String,Value> symTable, MyIList<Value> out,  MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Value> heap, IStatement program) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program.deepCopy();
        this.heap = heap;
        this.exeStack.push(this.originalProgram);
        this.fileTable = fileTable;

        this.incrementBaseId();
        this.id = this.getBaseId();

    }

    private synchronized int getBaseId() {
        return baseId;
    }

    private synchronized void incrementBaseId() {
        baseId = baseId + 1;
    }

    public int getId(){
        return this.id;
    }


    public MyIStack<IStatement> getExeStack() {
        return this.exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return this.symTable;
    }

    public MyIList<Value> getOutput() {
        return this.out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() { return this.fileTable; }

    public MyIHeap<Value> getHeap() { return this.heap; }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException {
        MyIStack<IStatement> executionStack = this.exeStack;
        if (exeStack.isEmpty()) {
            throw new MyException("Program state's execution stack is empty.");
        }
        IStatement topStatement = executionStack.pop();
        return topStatement.execute(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return "ID: " + this.id + "\n" +
                dtf.format(now) + "\n" +
                "Execution Stack:\n" +
                this.exeStack.toString() + "\n" +
                "Heap:\n" +
                this.heap.toString() + "\n" +
                "Symbol Table:\n" +
                this.symTable.toString() + "\n" +
                "Output:\n" +
                this.out.toString() + "\n" +
                "-".repeat(50);
    }


}
