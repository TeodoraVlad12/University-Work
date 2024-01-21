package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.ValueExpression;
import model.types.Type;
import model.values.IntValue;

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
    public IStatement deepcopy() {
        return new WaitStatement(value);
    }

    @Override
    public String toString() {
        return String.format("wait(%s)", value);
    }
}
