package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements IStatement {

    IExpression expression;
    IStatement thenS, elseS;

    public IfStatement(IExpression expression, IStatement thenS, IStatement elseS) {
        this.expression = expression;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    public String toString() {
        return "(if(" + expression.toString() + ") then(" + thenS.toString() + ")else(" + elseS.toString() + "))";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();


        if (!expression.evaluation(symTable, heap).getType().equals(new BoolType())) {
            throw new MyException("Expression is not a boolean!");
        } else if (((BoolValue) expression.evaluation(symTable, heap)).getVal()) {
            stack.push(thenS);
        } else stack.push(elseS);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.copy());
            elseS.typecheck(typeEnv.copy());
            return typeEnv;
        }
        throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public IStatement deepcopy() {
        return new IfStatement(expression.deepcopy(), thenS.deepcopy(), elseS.deepcopy());
    }
}
