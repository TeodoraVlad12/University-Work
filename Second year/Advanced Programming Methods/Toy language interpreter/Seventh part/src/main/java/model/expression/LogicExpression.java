package model.expression;

import exceptions.MyException;
import model.type.BoolType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.value.BoolValue;
import model.value.Value;

import java.util.Objects;

public class LogicExpression implements IExpression{
    IExpression expression1;
    IExpression expression2;
    String operation;

    public LogicExpression(String op, IExpression exp1, IExpression exp2){
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operation = op;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);

        if (type1.equals(new BoolType())){
            if (type2.equals(new BoolType())){
                return new BoolType();
            } else
                throw new MyException("Second operand is not a boolean");
        } else
            throw new MyException("First operand is not a boolean.");
    }

    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws MyException {
        Value value1, value2;
        value1 = this.expression1.eval(symTable, heap);
        if (value1.getType().equals(new BoolType())){
            value2 = this.expression2.eval(symTable, heap);
            if (value2.getType().equals(new BoolType())){
                BoolValue bool1 = (BoolValue) value1;
                BoolValue bool2 = (BoolValue) value2;
                boolean b1, b2;
                b1 = bool1.getValue();
                b2 = bool2.getValue();
                if (Objects.equals(this.operation, "and")) {
                    return new BoolValue(b1 && b2);}
                else
                if (Objects.equals(this.operation, "or")){
                    return new BoolValue(b1 || b2);
                }
                else throw new MyException("Not a valid operand");
            }
            else
                throw new MyException("Second operand is not a bool.");
        }
        else throw new MyException("First operand is not a bool.");

    }

    @Override
    public IExpression deepCopy(){
        return new LogicExpression(operation, expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
