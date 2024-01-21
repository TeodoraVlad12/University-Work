package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.Type;
import model.values.Value;

public interface IExpression {
    Value evaluation(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException;

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;

    IExpression deepcopy();
}
