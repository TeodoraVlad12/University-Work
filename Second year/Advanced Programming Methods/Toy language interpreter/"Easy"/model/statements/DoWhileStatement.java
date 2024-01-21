package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.Type;

public class DoWhileStatement implements IStatement{
    private final IStatement statement;
    private final IExpression expression;

    public DoWhileStatement(IExpression expression, IStatement statement) {
        this.statement = statement;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement converted = new CompoundStatement(statement, new WhileStatement(expression, statement));
        state.getExeStack().push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeExpression.equals(new BoolType())) {
            statement.typecheck(typeEnv.copy());
            return typeEnv;
        } else
            throw new MyException("Condition in the do while statement must be bool!");
    }

    @Override
    public IStatement deepcopy() {
        return new DoWhileStatement(expression.deepcopy(), statement.deepcopy());
    }

    @Override
    public String toString() {
        return String.format("do {%s} while (%s)", statement, expression);
    }
}
