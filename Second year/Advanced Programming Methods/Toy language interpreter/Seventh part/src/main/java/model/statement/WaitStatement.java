package model.statement;

import exceptions.MyException;
import model.expression.ValueExpression;
import model.programState.ProgramState;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIStack;
import model.value.IntValue;

public class WaitStatement implements IStatement{
    private final int value;

    public WaitStatement(int value) {
        this.value = value;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (value != 0) {
            MyIStack<IStatement> exeStack = state.getExeStack();
            exeStack.push(new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(value))),
                    new WaitStatement(value - 1)));

        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new WaitStatement(value);
    }

    @Override
    public String toString() {
        return String.format("wait(%s)", value);
    }
}
