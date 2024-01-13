package model.expression;

import exceptions.MyException;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.value.Value;

public class ValueExpression implements IExpression{
    Value value;

    public ValueExpression(Value value){
        this.value = value;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        return value.getType();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws MyException {
        return this.value;
    }

    @Override
    public IExpression deepCopy(){
        return new ValueExpression(value);
    }

    @Override
    public String toString(){
        return this.value.toString();
    }
}
