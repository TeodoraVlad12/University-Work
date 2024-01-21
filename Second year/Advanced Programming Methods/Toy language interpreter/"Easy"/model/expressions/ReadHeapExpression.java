package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.Type;
import model.types.RefType;
import model.values.Value;
import model.values.RefValue;

public class ReadHeapExpression implements IExpression {

    IExpression expression;

    public ReadHeapExpression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IExpression deepcopy() {
        return new ReadHeapExpression(expression.deepcopy());
    }


    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }

    @Override
    public Value evaluation(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        Value expressionValue = expression.evaluation(table, heap);

        if (!(expressionValue.getType() instanceof RefType)) {
            throw new MyException("Expression not of type Reference");
        }

        RefValue referenceValue = (RefValue) expressionValue;

        if(!heap.isDefined(referenceValue.getAddress())) {
            throw new MyException("Address not in heap");
        }

        return heap.lookup(referenceValue.getAddress());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof RefType refType) {
            return refType.getInner();
        }
        throw new MyException("the rH argument is not a Ref Type");
    }
}
