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
import java.io.FileReader;

public class OpenReadFile implements IStatement {

    IExpression expression;

    public OpenReadFile(IExpression expression){
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "openReadFile(" + this.expression.toString() + ")";
    }

    @Override
    public IStatement deepcopy() {
        return new OpenReadFile(expression.deepcopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value value = expression.evaluation(symTable, heap);

        if(!value.getType().equals(new StringType()))
            throw new MyException("The expression isn't of string type!");

        StringValue fileName = (StringValue) value;

        if(fileTable.isDefined(fileName))
            throw new MyException("File already open!");

        BufferedReader bufferedReader;

        try{
            bufferedReader = new BufferedReader(new FileReader(fileName.getVal()));
        }catch (Exception e){
            throw new MyException(e.toString());
        }

        fileTable.update(fileName, bufferedReader);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typecheck(typeEnv);

        if (!(typeExp instanceof StringType)) {
            throw new MyException("OpenReadFile: The expression isn't of string type!");
        }

        return typeEnv;
    }
}
