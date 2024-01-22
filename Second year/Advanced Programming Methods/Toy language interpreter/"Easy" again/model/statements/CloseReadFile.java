package model.statements;

import exceptions.*;
import model.ADTs.MyDictionaryInterface;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStatement {
    private final IExpression expFileName;

    public CloseReadFile(IExpression IExpression) {
        this.expFileName = IExpression;
    }

    @Override
    public String toString(){
        return "closeFile(" + this.expFileName.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value val = expFileName.eval(symbolTable, state.getHeap());
        if (val.getType().equals(new StringType())){
            StringValue stringFileName = (StringValue) val; // down casting
            if(!fileTable.isDefined(stringFileName))
                throw new MyException("File does not exist");
            else {
                try {
                    BufferedReader reader = fileTable.lookup(stringFileName);
                    reader.close();
                    fileTable.remove(stringFileName);
                } catch (IOException exception) {
                    throw new MyException("File cannot be closed: " + exception.getMessage());
                }
            }
        } else
            throw new MyException("CloseReadFile expression is not a string");

        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(this.expFileName);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type type = expFileName.typecheck(table);
        if (type.equals(new StringType())) {
            return table;
        } else {
            throw new MyException("IExpression not of type string");
        }
    }
}
