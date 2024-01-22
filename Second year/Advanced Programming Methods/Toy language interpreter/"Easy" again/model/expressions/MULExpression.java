package model.expressions;

import exceptions.MyException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.types.IntType;
import model.types.Type;
import model.values.Value;

public class MULExpression implements IExpression{
    private final IExpression expression1;
    private final IExpression expression2;

    public MULExpression(IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        if (type1.equals(new IntType()) && type2.equals(new IntType()))
            return new IntType();
        else
            throw new MyException("Expressions in the MUL should be int!");
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> table, MyHeapInterface<Value> heap) throws MyException {
        IExpression converted = new ArithmeticExpression('-',
                new ArithmeticExpression( '*',expression1, expression2),
                new ArithmeticExpression('+', expression1, expression2));
        return converted.eval(table, heap);
    }

    @Override
    public IExpression deepCopy() {
        return new MULExpression(expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("MUL(%s, %s)", expression1, expression2);
    }
}
