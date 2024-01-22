package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.types.Type;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;

public class SleepStatement implements IStatement{
    private final int value;

    public SleepStatement(int value) {
        this.value = value;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (value != 0) {
            MyStackInterface<IStatement> exeStack = state.getExeStack();
            exeStack.push(new SleepStatement(value - 1));

        }
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new SleepStatement(value);
    }

    @Override
    public String toString() {
        return String.format("sleep(%s)", value);
    }
}
