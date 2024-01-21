package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.expressions.NotExpression;
import model.types.BoolType;
import model.types.Type;

public class RepeatUntilStatement implements IStatement{
    private final IStatement statement;
    private final IExpression expression;

    public RepeatUntilStatement(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> exeStack = state.getExeStack();
        IStatement converted = new CompoundStatement(statement, new WhileStatement(new NotExpression(expression), statement));
        exeStack.push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type.equals(new BoolType())) {
            statement.typecheck(typeEnv.copy());
            return typeEnv;
        } else {
            throw new MyException("Expression in the repeat statement must be of Bool type!");
        }
    }

    @Override
    public IStatement deepcopy() {
        return new RepeatUntilStatement(statement.deepcopy(), expression.deepcopy());
    }

    @Override
    public String toString() {
        return String.format("repeat(%s) until (%s)", statement, expression);
    }
}
