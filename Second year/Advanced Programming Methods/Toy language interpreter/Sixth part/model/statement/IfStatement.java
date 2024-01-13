package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.type.BoolType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIStack;
import model.value.BoolValue;
import model.value.Value;
import model.programState.ProgramState;

public class IfStatement implements IStatement{
    IExpression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typeexp = expression.typecheck(typeEnv);
        if (typeexp.equals(new BoolType())){
            thenStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new MyException("The condition of if does not have bool type.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value result = this.expression.eval(state.getSymTable(), state.getHeap());
        if (result instanceof BoolValue)  {
            BoolValue boolResult = (BoolValue)result;

            IStatement statement;
            if (boolResult.getValue()) {
                statement = thenStatement;
            } else {
                statement = elseStatement;
            }

            MyIStack<IStatement> stack = state.getExeStack();
            stack.push(statement);
            //state.setExeStack(stack);
            return null;
        } else {
            throw new MyException("Please provide a boolean model expression in an if statement.");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("if(%s){%s}else{%s}", expression.toString(), thenStatement.toString(), elseStatement.toString());
    }
}
