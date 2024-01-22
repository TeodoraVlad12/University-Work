package model.expressions;
import exceptions.MyException;
import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements IExpression {
    private final Value expression;

    public ValueExpression(Value expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> symbolTable, MyHeapInterface<Value> Heap) throws MyException {
        return expression;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(this.expression);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        return this.expression.getType();
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
