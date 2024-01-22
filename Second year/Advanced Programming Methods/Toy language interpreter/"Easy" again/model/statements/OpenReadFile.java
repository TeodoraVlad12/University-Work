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
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFile implements IStatement {
    private final IExpression nameOfNewFile;

    public OpenReadFile(IExpression IExpression) {
        this.nameOfNewFile = IExpression;
    }

    @Override
    public String toString(){
        return "openFile(" + this.nameOfNewFile.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value val = this.nameOfNewFile.eval(symbolTable, state.getHeap());
        if (val.getType().equals(new StringType())){
            StringValue stringVal = (StringValue) val; // down casting
            if(fileTable.isDefined(stringVal))
                throw new MyException("File is already opened");
            else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(stringVal.getValue()));
                    fileTable.add(stringVal, reader);
                } catch (IOException exception) {
                    throw new MyException("File cannot be opened " + exception.getMessage());
                }
            }
        } else
            throw new MyException("OpenReadFile expression is not a string");

        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(this.nameOfNewFile);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type type = nameOfNewFile.typecheck(table);
        if (type.equals(new StringType())) {
            return table;
        } else {
            throw new MyException("IExpression not of type string");
        }
    }

}
