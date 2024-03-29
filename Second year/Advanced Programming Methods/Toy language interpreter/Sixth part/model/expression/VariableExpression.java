package model.expression;

import exceptions.MyException;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.value.Value;

public class VariableExpression implements IExpression{
    String key;

    public VariableExpression(String key){
        this.key = key;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookUp(key);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws MyException {
        return symTable.lookUp(key);
    }

    @Override
    public IExpression deepCopy(){
        return new VariableExpression((key));
    }

    @Override
    public String toString(){
        return key;
    }
}
