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

public class IfStatement implements IStatement {
    private final IExpression IExpression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(IExpression IExpression, IStatement thenStatement, IStatement elseStatement) {
        this.IExpression = IExpression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyStackInterface<IStatement> stack = state.getExeStack();
        Value condition = IExpression.eval(symbolTable, state.getHeap());
        if (!condition.getType().equals(new BoolType()))
            throw new MyException("Conditional IExpression is not a boolean");
        else{
            if(condition.equals(new BoolValue(true)))
                stack.push(thenStatement);
            else
                stack.push(elseStatement);
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.IExpression, this.thenStatement, this.elseStatement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type expressionType = IExpression.typecheck(table);
        if (expressionType.equals(new BoolType())) {
            thenStatement.typecheck(table.deepCopy());
            elseStatement.typecheck(table.deepCopy());
            return table;
        } else {
            throw new MyException("Condition not of type bool");
        }
    }

    @Override
    public String toString(){
        return "(if(" + IExpression.toString() + ") then(" + thenStatement.toString() + ") else(" + elseStatement.toString() + "))";
    }
}
