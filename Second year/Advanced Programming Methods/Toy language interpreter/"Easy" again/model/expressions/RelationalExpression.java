package model.expressions;

import exceptions.MyException;
import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements IExpression {
    private final IExpression IExpression1;
    private final IExpression IExpression2;
    private final String op;

    public RelationalExpression(String op, IExpression IExpression1, IExpression IExpression2) {
        this.IExpression1 = IExpression1;
        this.IExpression2 = IExpression2;
        this.op = op;
    }

    @Override
    public String toString() {
        return IExpression1.toString() + " " + this.op + " " + this.IExpression2;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws MyException {
        Value v1, v2;
        v1 = IExpression1.eval(SymbolTable, Heap);

        if (v1.getType().equals(new IntType())) {
            v2 = IExpression2.eval(SymbolTable, Heap);
            if (v2.getType().equals(new IntType())) {
                IntValue integerValue1 = (IntValue) v1; //casting -> dintr-un tip mai mare intr-un tip mai mic
                IntValue integerValue2 = (IntValue) v2; //casting -> dintr-un tip mai mare intr-un tip mai mic
                int n1, n2;
                n1 = integerValue1.getValue();
                n2 = integerValue2.getValue();

                switch (op) {
                    case "<" -> {return new BoolValue(n1 < n2);}
                    case "<=" -> {return new BoolValue(n1 <= n2);}
                    case "==" -> {return new BoolValue(n1 == n2);}
                    case "!=" -> {return new BoolValue(n1 != n2);}
                    case ">" -> {return new BoolValue(n1 > n2);}
                    case ">=" -> {return new BoolValue(n1 >= n2);}
                    default -> throw new MyException("Operation invalid!");
                }
            } else
                throw new MyException("The second operand is not an integer");
        } else
            throw new MyException("The first operand is not an integer");

    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(this.op, this.IExpression1, this.IExpression2);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type type1, type2;
        type1 = IExpression1.typecheck(table);
        type2 = IExpression2.typecheck(table);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new MyException("Second operand is not an integer");
            }
        } else {
            throw new MyException("First operand is not an integer");
        }
    }
}
