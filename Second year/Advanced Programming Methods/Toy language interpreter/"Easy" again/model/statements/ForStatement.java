package model.statements;

import exceptions.MyException;
import model.ADTs.MyStack;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.ProgramState;
import model.statements.*;
import model.types.IntType;
import model.types.Type;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;

public class ForStatement implements IStatement {
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
        MyStackInterface<IStatement> exeStack = state.getExeStack();

        IStatement converted = new CompoundStatement(new VariableDeclarationStatement(variable, new IntType()),
                new CompoundStatement(new AssignmentStatement(variable, expression1),
                        new WhileStatement(new RelationalExpression("<",new VariableExpression(variable), expression2 ),
                                new CompoundStatement(statement, new AssignmentStatement(variable, expression3)))));

        /*IStatement converted = new CompoundStatement(new AssignmentStatement(variable, expression1),
                new WhileStatement(new RelationalExpression("<",new VariableExpression(variable), expression2),
                        new CompoundStatement(statement, new AssignmentStatement(variable, expression3))));
*/
        exeStack.push(converted);
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        MyDictionaryInterface<String, Type> table1 = new VariableDeclarationStatement(variable, new IntType()).typecheck(typeEnv.deepCopy());
        Type vType = table1.lookup(variable);
        Type type1 = expression1.typecheck(table1);
        Type type2 = expression2.typecheck(table1);
        Type type3 = expression3.typecheck(table1);

        if ( vType.equals(new IntType()) && type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("The for statement is invalid!");


        /*Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        Type type3 = expression3.typecheck(typeEnv);

        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("The for statement is invalid!");*/
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

