package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.Type;
import model.types.IntType;
import model.values.BoolValue;
import model.values.Value;
import model.values.IntValue;

public class RelationalExpression implements IExpression {

    IExpression firstExpression, secondExpression;
    String relationalOperation;

    @Override
    public String toString() {
        return firstExpression.toString() + relationalOperation + secondExpression.toString() ;
    }

    public RelationalExpression(IExpression firstExpression, IExpression secondExpression, String relationalOperation){
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.relationalOperation = relationalOperation;
    }

    @Override
    public Value evaluation(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        Value firstValue, secondValue;
        firstValue = firstExpression.evaluation(table, heap);
        if(firstValue.getType().equals(new IntType()))
        {
            secondValue = secondExpression.evaluation(table, heap);
            if (secondValue.getType().equals(new IntType()))
            {
                int firstNumber = ((IntValue)firstValue).getVal();
                int secondNumber = ((IntValue)secondValue).getVal();

                return switch (relationalOperation){
                    case "<" -> new BoolValue(firstNumber < secondNumber);
                    case "<=" -> new BoolValue(firstNumber <= secondNumber);
                    case ">" -> new BoolValue(firstNumber > secondNumber);
                    case ">=" -> new BoolValue(firstNumber >= secondNumber);
                    case "==" -> new BoolValue(firstNumber == secondNumber);
                    case "!=" -> new BoolValue(firstNumber != secondNumber);
                    default-> throw new MyException("Invalid relational operation!");

                };
            }
            else
                throw new MyException("Second operand isn't an integer!");
        }
        else
            throw new MyException("First operand isn't an integer!");
    }

    @Override
    public IExpression deepcopy() {
        return new RelationalExpression(firstExpression.deepcopy(), secondExpression.deepcopy(), relationalOperation);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = firstExpression.typecheck(typeEnv);
        type2 = secondExpression.typecheck(typeEnv);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
                return new BoolType();
            else
                throw new MyException("Second operand isn't an integer!");
        }
        else
            throw new MyException("First operand isn't an integer!");


    }
}
