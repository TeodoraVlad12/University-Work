package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements IExpression {
    String id;

    public VariableExpression(String id){
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public Value evaluation(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        if(table.isDefined(id))
            return table.lookup(id);
        throw new MyException("Variable " + id + " isn't defined!");
    }

    @Override
    public IExpression deepcopy() {
        return new VariableExpression(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
}
