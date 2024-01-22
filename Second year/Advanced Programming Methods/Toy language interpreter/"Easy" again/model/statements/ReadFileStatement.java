package model.statements;

import exceptions.*;
import model.ADTs.MyDictionaryInterface;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private final IExpression expFileName; //varf
    private final String varLine; // varc

    public ReadFileStatement(IExpression IExpression, String varName) {
        this.expFileName = IExpression;
        this.varLine = varName;
    }

    @Override
    public String toString(){
        return "readFile(" + this.expFileName.toString() + ", " + this.varLine + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(!symbolTable.isDefined(this.varLine))
            throw new MyException("The variable where you save the content of the line, " + this.varLine + " is not defined in the symbol table");
        Value valOfLine = symbolTable.lookup(this.varLine);
        if (!valOfLine.getType().equals(new IntType()))
            throw new MyException("You can't save an integer value in the variable " + this.varLine);

        Value valFileName = expFileName.eval(symbolTable, state.getHeap());
        if (!valFileName.getType().equals(new StringType()))
            throw new MyException("You can't have in the variable " + this.expFileName + " a file name because its value type is not a string");

        StringValue valFileNameString = (StringValue) valFileName;
        if (fileTable.isDefined(valFileNameString)) {
            try {
                BufferedReader reader = fileTable.lookup(valFileNameString);
                String line = reader.readLine();
                IntValue intVal;
                if (line == null)
                    intVal = new IntValue(0);
                else
                    intVal = new IntValue(Integer.parseInt(line));
                symbolTable.update(this.varLine, intVal);
                state.setSymbolTable(symbolTable);
            } catch (IOException exception) {
                throw new MyException("Cannot read from file");
            }
        } else{
            throw new MyException("The string " + valFileNameString + " is not defined in file table");
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(this.expFileName, this.varLine);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type expressionType = expFileName.typecheck(table);
        Type variableType = table.lookup(varLine);
        if (expressionType.equals(new StringType())) {
            if (variableType.equals(new IntType())) {
                return table;
            } else {
                throw new MyException("Variable not of type int");
            }
        } else {
            throw new MyException("IExpression not of type string");
        }
    }
}
