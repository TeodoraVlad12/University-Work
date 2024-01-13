package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import model.programState.ProgramState;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    private final IExpression expression;
    private final String varName;

    public ReadFile(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }
    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (expression.typecheck(typeEnv).equals(new StringType()))
            if (typeEnv.lookUp(varName).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("ReadFile requires an int as its variable parameter.");
        else
            throw new MyException("ReadFile requires a string as es expression parameter.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(varName)) {
            Value value = symTable.lookUp(varName);
            if (value.getType().equals(new IntType())) {
                value = expression.eval(symTable, state.getHeap());
                if (value.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) value;
                    if (fileTable.isDefined(castValue)) {
                        BufferedReader br = fileTable.lookUp(castValue);
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.put(varName, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new MyException(String.format("Could not read from file %s...", castValue));
                        }
                    } else {
                        throw new MyException(String.format("The file table does not contain %s:(", castValue));
                    }
                } else {
                    throw new MyException(String.format("%s does not evaluate to StringTypeeee", value));
                }
            } else {
                throw new MyException(String.format("%s is not of type IntTypeeee", value));
            }
        } else {
            throw new MyException(String.format("%s is not present in the symTable.", varName));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(expression.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", expression.toString(), varName);
    }

}
