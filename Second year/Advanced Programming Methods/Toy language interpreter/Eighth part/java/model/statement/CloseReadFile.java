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
import java.io.IOException;

public class CloseReadFile implements IStatement{
    private final IExpression expression;

    public CloseReadFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (expression.typecheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("CloseReadFile requires a string expression.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new MyException(String.format("%s does not evaluate to StringValueeee", expression));
        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!state.getFileTable().isDefined(fileName))
            throw new MyException(String.format("%s is not present in the FileTable...", value));
        BufferedReader br = state.getFileTable().lookUp(fileName);
        try {
            br.close();
            fileTable.remove(fileName);
        } catch (IOException e) {
            throw new MyException(String.format("Unexpected error in closing %s:o", value));
        }

        //state.setFileTable(fileTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expression.toString());
    }
}
