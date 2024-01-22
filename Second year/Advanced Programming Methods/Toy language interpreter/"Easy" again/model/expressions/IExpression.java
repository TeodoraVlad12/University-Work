package model.expressions;

import exceptions.InterpreterException;
import exceptions.MyException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.types.Type;
import model.values.Value;

public interface IExpression {
    Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws MyException;
    public IExpression deepCopy();
    Type typecheck(MyDictionaryInterface<String, Type> table) throws MyException;
}
