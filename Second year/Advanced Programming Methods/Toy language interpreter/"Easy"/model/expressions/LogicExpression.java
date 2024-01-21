package model.expressions;

import exceptions.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements IExpression {
    IExpression e1, e2;
    String op;

    public LogicExpression(IExpression e1, IExpression e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return e1 + " " + op + " " + e2;
    }

    @Override
    public Value evaluation(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        Value firstEvaluation = e1.evaluation(table, heap);

        if (firstEvaluation.getType().equals(new BoolType())) {
            Value secondEvaluation = e2.evaluation(table, heap);

            if (secondEvaluation.getType().equals(new BoolType())) {
                return switch (op) {
                    case "||" ->
                            new BoolValue(((BoolValue) firstEvaluation).getVal() || ((BoolValue) secondEvaluation).getVal());
                    case "&&" ->
                            new BoolValue(((BoolValue) firstEvaluation).getVal() && ((BoolValue) secondEvaluation).getVal());
                    default -> throw new MyException("Logic operation not exist!");
                };

            } else throw new MyException("Operand2 isn't a boolean!");
        } else throw new MyException("Operand1 isn't a boolean!");
    }

    @Override
    public IExpression deepcopy() {
        return new LogicExpression(this.e1.deepcopy(), this.e2.deepcopy(), op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);

        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new MyException("Operand2 isn't a boolean!");
        }
        else
            throw new MyException("Operand1 isn't a boolean!");


    }
}
