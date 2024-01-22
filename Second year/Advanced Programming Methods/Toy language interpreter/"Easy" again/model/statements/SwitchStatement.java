package model.statements;

import exceptions.MyException;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.ProgramState;
import model.types.Type;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;

public class SwitchStatement implements IStatement{
    private final IExpression mainExpression;
    private final IExpression expression1;
    private final IStatement statement1;
    private final IExpression expression2;
    private final IStatement statement2;
    private final IStatement defaultStatement;

    public SwitchStatement(IExpression mainExpression, IExpression expression1, IStatement statement1, IExpression expression2, IStatement statement2, IStatement defaultStatement) {
        this.mainExpression = mainExpression;
        this.expression1 = expression1;
        this.statement1 = statement1;
        this.expression2 = expression2;
        this.statement2 = statement2;
        this.defaultStatement = defaultStatement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyStackInterface<IStatement> exeStack = state.getExeStack();
        IStatement converted = new IfStatement(new RelationalExpression("==", mainExpression, expression1),
                statement1, new IfStatement(new RelationalExpression("==", mainExpression, expression2), statement2, defaultStatement));
        exeStack.push(converted);

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type mainType = mainExpression.typecheck(typeEnv);
        Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        if (mainType.equals(type1) && mainType.equals(type2)) {
            statement1.typecheck(typeEnv.deepCopy());
            statement2.typecheck(typeEnv.deepCopy());
            defaultStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The expression types don't match in the switch statement!");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(mainExpression.deepCopy(), expression1.deepCopy(), statement1.deepCopy(), expression2.deepCopy(), statement2.deepCopy(), defaultStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("switch(%s){(case(%s): %s)(case(%s): %s)(default: %s)}", mainExpression, expression1, statement1, expression2, statement2, defaultStatement);
    }
}
