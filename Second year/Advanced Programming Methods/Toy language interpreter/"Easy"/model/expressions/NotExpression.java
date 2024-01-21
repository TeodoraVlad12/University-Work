package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class NotExpression implements IExpression{
    private final IExpression expression;

    public NotExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return expression.typecheck(typeEnv);
    }

    @Override
    public Value evaluation(MyIDictionary<String, Value> symTable, MyIHeap<Integer, Value> heap) throws MyException {
        BoolValue value = (BoolValue) expression.evaluation(symTable, heap);
        if (!value.getVal())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public IExpression deepcopy() {
        return new NotExpression(expression.deepcopy());
    }

    @Override
    public String toString() {
        return String.format("!(%s)", expression);
    }
}
