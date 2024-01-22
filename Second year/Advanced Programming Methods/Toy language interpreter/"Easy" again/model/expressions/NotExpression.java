package model.expressions;

import exceptions.MyException;
import model.types.Type;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.values.BoolValue;
import model.values.Value;

public class NotExpression implements IExpression{
    private final IExpression expression;

    public NotExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        return expression.typecheck(typeEnv);
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> symTable, MyHeapInterface<Value> heap) throws MyException {
        BoolValue value = (BoolValue) expression.eval(symTable, heap);
        if (!value.getValue())
            return new BoolValue(true);
        else
            return new BoolValue(false);
    }

    @Override
    public IExpression deepCopy() {
        return new NotExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("!(%s)", expression);
    }
}
