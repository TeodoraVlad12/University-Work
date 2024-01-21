package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.types.Type;
import model.types.StringType;
import model.values.Value;
import model.values.StringValue;

import java.io.BufferedReader;

public class CloseReadFile implements IStatement {
    IExpression expression;

    public CloseReadFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "closeReadFile(" + this.expression.toString() + ")";
    }

    @Override
    public IStatement deepcopy() {
        return new CloseReadFile(expression.deepcopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value value = expression.evaluation(symTable, heap);

        if (!value.getType().equals(new StringType()))
            throw new MyException("The expression isn't of string type!");

        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        BufferedReader bufferedReader = fileTable.lookup(fileName);

        if (bufferedReader == null) {
            throw new MyException("File with given name not found!");
        }

        try {
            bufferedReader.close();
        } catch (Exception e) {
            throw new MyException(e.toString());
        }

        fileTable.remove(fileName);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typecheck(typeEnv);

        if (!(typeExp instanceof StringType)) {
            throw new MyException("CloseReadFile: The expression isn't of string type!");
        }

        return typeEnv;
    }
}
