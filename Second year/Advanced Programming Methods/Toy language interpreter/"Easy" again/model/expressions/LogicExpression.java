package model.expressions;

import exceptions.MyException;
import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements IExpression {
    private final IExpression IExpression1;
    private final IExpression IExpression2;
    private final int op; // 1->&, 2->|

    public LogicExpression(IExpression IExpression1, IExpression IExpression2, int op) {
        this.IExpression1 = IExpression1;
        this.IExpression2 = IExpression2;
        this.op = op;
    }

    @Override
    public String toString() {
        return switch (op) {
            case 1 -> IExpression1.toString() + "&" + IExpression2.toString();
            case 2 -> IExpression1.toString() + "|" + IExpression2.toString();
            default -> "";
        };
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws MyException {
        Value v1, v2;
        v1 = IExpression1.eval(SymbolTable, Heap);

        if (v1.getType().equals(new BoolType())) {
            v2 = IExpression2.eval(SymbolTable, Heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue booleanValue1 = (BoolValue) v1; //casting -> dintr-un tip mai mare intr-un tip mai mic
                BoolValue booleanValue2 = (BoolValue) v2; //casting -> dintr-un tip mai mare intr-un tip mai mic
                boolean b1, b2;
                b1 = booleanValue1.getValue();
                b2 = booleanValue2.getValue();

                switch (op) {
                    case 1 -> {return new BoolValue(b1 & b2);}
                    case 2 -> {return new BoolValue(b1 | b2);}
                    default -> throw new MyException("Operation invalid!");
                }
            } else
                throw new MyException("The second operand is not a boolean");
        } else
            throw new MyException("The first operand is not a boolean");

    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(this.IExpression1, this.IExpression2, this.op);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type type1, type2;
        type1 = IExpression1.typecheck(table);
        type2 = IExpression2.typecheck(table);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new MyException("Second operand is not an integer");
            }
        } else {
            throw new MyException("First operand is not an integer");
        }
    }
}
