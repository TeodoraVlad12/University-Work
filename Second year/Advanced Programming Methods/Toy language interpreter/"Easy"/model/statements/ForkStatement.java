package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.*;
import model.types.Type;
import model.values.Value;
import model.values.StringValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement {
    IStatement statement;

    public ForkStatement(IStatement statement){
        this.statement = statement;
    }

    @Override
    public String toString()
    {
        return "fork{" + this.statement.toString() + "}";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, Value> symTable = state.getSymTable().copy();
        MyIList<Value> outputList = state.getOutputList();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        return new ProgramState(new MyStack<>(), symTable, outputList, fileTable, heap, this.statement);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
         statement.typecheck(typeEnv.copy());
         return typeEnv;
    }

    @Override
    public IStatement deepcopy() {
        return new ForkStatement(statement.deepcopy());
    }
}
