package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.type.StringType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.value.StringValue;
import model.value.Value;
import model.programState.ProgramState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFile implements IStatement{
    private final IExpression expression;

    public OpenReadFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (expression.typecheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("OpenReadFile requires a string expression.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) value;
            MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            if (!state.getFileTable().isDefined(fileName)) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getValue()));
                } catch (FileNotFoundException e) {
                    throw new MyException(String.format("%s could not be opened:/", fileName.getValue()));
                }
                fileTable.put(fileName, br);
                //state.setFileTable(fileTable);
            } else {
                throw new MyException(String.format("%s is already opened:(", fileName.getValue()));
            }
        } else {
            throw new MyException(String.format("%s does not evaluate to StringTypeeeeeeeeeee", expression.toString()));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expression.toString());
    }

}
