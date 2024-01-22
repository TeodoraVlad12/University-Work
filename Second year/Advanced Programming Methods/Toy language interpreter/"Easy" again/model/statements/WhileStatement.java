package model.statements;

import exceptions.InterpreterException;
import exceptions.MyException;
import exceptions.MyExcpetion;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements IStatement {
    private final IExpression IExpression;
    private final IStatement statement;

    public WhileStatement(IExpression IExpression, IStatement statement) {
        this.IExpression = IExpression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyStackInterface<IStatement> stack = state.getExeStack();

        Value valExpr = this.IExpression.eval(state.getSymbolTable(), state.getHeap());
        if (!valExpr.getType().equals(new BoolType()))
            throw new MyException("IExpression not of type bool");

        if (valExpr.equals(new BoolValue(true))) {
            // daca e true dai push la while mai intai, si dupa la statement ca el sa fie primul ca sa il executi.
            stack.push(new WhileStatement(this.IExpression, this.statement));
            stack.push(this.statement);
        }

        state.setExeStack(stack);
        return null;
    }

    @Override
    public String toString() {
        return "while(" + this.IExpression.toString() + ") { " + this.statement.toString() + " }";
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.IExpression, this.statement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type expressionType = IExpression.typecheck(table);
        if (expressionType.equals(new BoolType())) {
            statement.typecheck(table.deepCopy());
            return table;
        } else {
            throw new MyException("Condition not of type bool");
        }
    }
}
