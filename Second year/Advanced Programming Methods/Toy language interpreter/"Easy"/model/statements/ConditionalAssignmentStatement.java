package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.expressions.VariableExpression;
import model.types.BoolType;
import model.types.Type;

public class ConditionalAssignmentStatement implements IStatement{
    private String variable;
    private IExpression expression1;
    private IExpression expression2;
    private IExpression expression3;

    public ConditionalAssignmentStatement(String var, IExpression exp1, IExpression exp2, IExpression exp3){
        this.variable = var;
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.expression3 = exp3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> exeStack = state.getExeStack();
        IStatement converted = new IfStatement(expression1, new AssignmentStatement(variable,expression2), new AssignmentStatement(variable, expression3));
        exeStack.push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type variableType = new VariableExpression(variable).typecheck(typeEnv);
        Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        Type type3 = expression3.typecheck(typeEnv);
        if(type1.equals(new BoolType()) && type2.equals(variableType) && type3.equals(variableType))
            return typeEnv;
        else
            throw new MyException("The conditional assignment is invalid:(");
    }

    @Override
    public IStatement deepcopy(){
        return new ConditionalAssignmentStatement(variable, expression1.deepcopy(), expression2.deepcopy(), expression3.deepcopy());
    }

    @Override
    public String toString(){
        return String.format("%s=(%s)? %s: %s", variable, expression1, expression2, expression3);
    }


}
