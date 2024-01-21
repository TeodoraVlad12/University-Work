package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIStack;
import model.types.Type;
import model.adt.MyIDictionary;

public class SleepStatement implements IStatement{
    private final int value;

    public SleepStatement(int value) {
        this.value = value;
    }



    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (value != 0) {
            MyIStack<IStatement> exeStack = state.getExeStack();
            exeStack.push(new SleepStatement(value - 1));

        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStatement deepcopy() {
        return new SleepStatement(value);
    }

    @Override
    public String toString() {
        return String.format("sleep(%s)", value);
    }
}
