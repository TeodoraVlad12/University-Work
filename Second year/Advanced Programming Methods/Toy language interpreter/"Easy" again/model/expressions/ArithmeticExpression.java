package model.expressions;
import exceptions.MyException;
import exceptions.InterpreterException;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements IExpression {
    private final IExpression IExpression1;
    private final IExpression IExpression2;
    private int op;  // 1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExpression(char op, IExpression first, IExpression second) {
        this.IExpression1 = first;
        this.IExpression2 = second;
        switch (op){
            case '+' -> this.op = 1;
            case '-' -> this.op = 2;
            case '*' -> this.op = 3;
            case '/' -> this.op = 4;
        }
    }

    @Override
    public String toString() {
        return switch (op) {
            case 1 -> IExpression1.toString() + "+" + IExpression2.toString();
            case 2 -> IExpression1.toString() + "-" + IExpression2.toString();
            case 3 -> IExpression1.toString() + "*" + IExpression2.toString();
            case 4 -> IExpression1.toString() + '/' + IExpression2.toString();
            default -> "";
        };
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws MyException {
        Value v1, v2;
        v1 = IExpression1.eval(SymbolTable, Heap);

        if (v1.getType().equals(new IntType())) {
            v2 = IExpression2.eval(SymbolTable, Heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1; //casting -> dintr-un tip mai mare intr-un tip mai mic
                IntValue i2 = (IntValue) v2; //casting -> dintr-un tip mai mare intr-un tip mai mic
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                switch (op) {
                    case 1 -> {return new IntValue(n1 + n2);}
                    case 2 -> {return new IntValue(n1 - n2);}
                    case 3 -> {return new IntValue(n1 * n2);}
                    case 4 -> {
                        if (n2 == 0)
                            throw new MyException("Division by 0!"); //DivisionBy0Exception
                        else
                            return new IntValue(n1 / n2);
                    }
                    default -> throw new MyException("Operation invalid!");
                }
            } else
                throw new MyException("The second operand is not an integer");
        } else
            throw new MyException("The first operand is not an integer");

    }

    @Override
    public IExpression deepCopy() {
        char newOp;
        switch (this.op){
            case 1 -> newOp = '+';
            case 2 -> newOp = '-';
            case 3 -> newOp = '*';
            case 4 -> newOp = '/';
            default -> newOp = ' ';
        }
        return new ArithmeticExpression(newOp, this.IExpression1, this.IExpression2);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type type1, type2;
        type1 = IExpression1.typecheck(table);
        type2 = IExpression2.typecheck(table);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new MyException("Second operand is not an integer");
            }
        } else {
            throw new MyException("First operand is not an integer");
        }
    }

}
