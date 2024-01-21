package model.statement;

import exceptions.MyException;
import model.programState.ProgramState;
import model.type.Type;
import model.utils.MyIDictionary;

public class NopStatement implements IStatement{

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
