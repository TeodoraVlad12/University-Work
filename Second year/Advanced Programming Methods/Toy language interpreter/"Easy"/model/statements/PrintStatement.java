package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.expressions.IExpression;
import model.types.Type;
import model.values.Value;

public class PrintStatement implements IStatement {

    IExpression expression;
    public PrintStatement(IExpression expression){
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + expression + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIList<Value> outputList = state.getOutputList();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        outputList.add(expression.evaluation(symTable, heap));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepcopy() {
        return new PrintStatement(expression.deepcopy());
    }
}
