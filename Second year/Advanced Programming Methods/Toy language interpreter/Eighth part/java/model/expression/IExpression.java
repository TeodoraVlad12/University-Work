package model.expression;

import exceptions.MyException;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.value.Value;

public interface IExpression {
    Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    IExpression deepCopy();
}
