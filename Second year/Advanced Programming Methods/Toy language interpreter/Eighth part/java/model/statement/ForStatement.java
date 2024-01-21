package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.expression.RelationalExpression;
import model.expression.VariableExpression;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIStack;

public class ForStatement implements IStatement{
    private final String variable;
    private final IExpression expression1;
    private final IExpression expression2;
    private final IExpression expression3;
    private final IStatement statement;

    public ForStatement(String variable, IExpression expression1, IExpression expression2, IExpression expression3, IStatement statement){
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        MyIStack<IStatement> exeStack = state.getExeStack();
        IStatement converted = new CompoundStatement(new AssignmentStatement(variable, expression1),
                new WhileStatement(new RelationalExpression("<",new VariableExpression(variable), expression2),
                        new CompoundStatement(statement, new AssignmentStatement(variable, expression3))));
        exeStack.push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        Type type3 = expression3.typecheck(typeEnv);

        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("The for statement is invalid!");
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(variable, expression1.deepCopy(), expression2.deepCopy(), expression3.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) {%s}", variable, expression1, variable, expression2, variable, expression3, statement);
    }
}

