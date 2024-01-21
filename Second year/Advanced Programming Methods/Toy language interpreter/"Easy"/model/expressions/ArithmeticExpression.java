package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.Type;
import model.types.IntType;
import model.values.Value;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression {
    IExpression firstExpression, secondExpression;
    String op;

    public ArithmeticExpression(IExpression firstExpression, IExpression secondExpression, String op)
    {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.op = op;
    }


    @Override
    public String toString() {
        return firstExpression + " " + op +  " " + secondExpression;
    }
    @Override
    public Value evaluation(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        Value firstValue, secondValue;

        firstValue = firstExpression.evaluation(table, heap);

        if (firstValue.getType().equals(new IntType())) {
            secondValue = secondExpression.evaluation(table, heap);

            if (secondValue.getType().equals(new IntType())) {
                IntValue firstIntValue = (IntValue) firstValue;
                IntValue secondIntValue = (IntValue) secondValue;
                int firstNumber, secondNumber;
                firstNumber = firstIntValue.getVal();
                secondNumber = secondIntValue.getVal();

                return switch (op) {
                    case "+" -> new IntValue(firstNumber + secondNumber);
                    case "-" -> new IntValue(firstNumber - secondNumber);
                    case "*" -> new IntValue(firstNumber * secondNumber);
                    case "/" -> {
                        if (secondNumber == 0)
                            throw new MyException("Division by zero!");
                        yield new IntValue(firstNumber / secondNumber);
                    }
                    default -> throw new MyException("Arithmetic operation not exist!");
                };

            } else
                throw new MyException("Second operand isn't an integer!");
        } else
            throw new MyException("First operand isn't an integer!");
    }
    @Override
    public IExpression deepcopy() {
        return new ArithmeticExpression(this.firstExpression.deepcopy(), this.secondExpression.deepcopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = firstExpression.typecheck(typeEnv);
        type2 = secondExpression.typecheck(typeEnv);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
                return new IntType();
            else
                throw new MyException("Second operand isn't an integer!");
        }
        else
            throw new MyException("First operand isn't an integer!");


    }
}
