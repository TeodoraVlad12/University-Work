package model.expressions;

import exceptions.InterpreterException;
import exceptions.MyException;
import exceptions.MyExcpetion;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExpression implements IExpression {
    private final IExpression expVarName;

    public ReadHeapExpression(IExpression IExpression) {
        this.expVarName = IExpression;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws MyException {
        Value val = this.expVarName.eval(SymbolTable, Heap);
        if (!(val instanceof RefValue))
            throw new MyException("IExpression not of reference type!");

        RefValue refVal = (RefValue) val;
        int address = refVal.getAddress();
        if (!Heap.exists(address))
            throw new MyException("Not allocated on heap");

        return Heap.get(address);
    }

    @Override
    public String toString() {
        return "readHeap(" + this.expVarName.toString() + ")";
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(this.expVarName);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type type = this.expVarName.typecheck(table);
        if (type instanceof RefType) {
            RefType referenceType = (RefType) type;
            return referenceType.getInner();
        } else {
            throw new MyException("IExpression not of reference type");
        }
    }
}
