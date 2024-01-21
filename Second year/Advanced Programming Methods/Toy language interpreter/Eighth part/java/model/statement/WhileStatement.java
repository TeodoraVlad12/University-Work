package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.type.BoolType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIStack;
import model.value.BoolValue;
import model.value.Value;
import model.programState.ProgramState;

public class WhileStatement implements IStatement{
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typeexp = expression.typecheck(typeEnv);
        if (typeexp.equals(new BoolType())){
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new MyException("The condition of while does not have bool type.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStatement> stack = state.getExeStack();
        if (!value.getType().equals(new BoolType()))
            throw new MyException(String.format("%s is not of BoolTypeeeee", value));
        if (!(value instanceof BoolValue))
            throw new MyException(String.format("%s is not a BoolValue:(((((((((", value));
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            stack.push(this.deepCopy());
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }
}
