package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
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
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        if (type1.equals(new IntType()) && type2.equals(new IntType()))
            return new IntType();
        else
            throw new MyException("Expressions in the MUL should be int!");
    }

    @Override
    public Value evaluation(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        IExpression converted = new ArithmeticExpression(
                new ArithmeticExpression( expression1, expression2,"*"),
                new ArithmeticExpression( expression1, expression2,"+"),"-");
        return converted.evaluation(table, heap);
    }

    @Override
    public IExpression deepcopy() {
        return new MULExpression(expression1.deepcopy(), expression2.deepcopy());
    }

    @Override
    public String toString() {
        return String.format("MUL(%s, %s)", expression1, expression2);
    }
}
