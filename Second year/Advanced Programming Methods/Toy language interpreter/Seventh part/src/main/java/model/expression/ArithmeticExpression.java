package model.expression;

import exceptions.MyException;
import model.type.IntType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.value.IntValue;
import model.value.Value;

public class ArithmeticExpression implements IExpression{
    private IExpression expression1;
    private IExpression expression2;
    char operation;

    public ArithmeticExpression( char op, IExpression e1, IExpression e2){
        this.expression1 = e1;
        this.expression2 = e2;
        this.operation = op;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);

        if (type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new IntType();
            }else
                throw new MyException("Second operand is not an integer.");
        } else
            throw new MyException("First operand is not an integer.");

    }

    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws MyException {
        Value value1, value2;
        value1 = this.expression1.eval(symTable, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = this.expression2.eval(symTable, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int n1, n2;
                n1 = int1.getValue();
                n2 = int2.getValue();
                if (this.operation == '+')
                    return new IntValue(n1 + n2);
                else if (this.operation == '-')
                    return new IntValue(n1 - n2);
                else if (this.operation == '*')
                    return new IntValue(n1 * n2);
                else if (this.operation == '/')
                    if (n2 == 0)
                        throw new MyException("Division by zero.");
                    else
                        return new IntValue(n1 / n2);
            } else
                throw new MyException("Second operand is not an integer.");
        } else
            throw new MyException("First operand is not an integer.");
        return null;

    }

    @Override
    public IExpression deepCopy(){
        return new ArithmeticExpression(operation, expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
