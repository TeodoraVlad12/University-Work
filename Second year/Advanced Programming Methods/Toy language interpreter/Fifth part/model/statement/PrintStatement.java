package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.utils.MyIList;
import model.value.Value;
import model.programState.ProgramState;

public class PrintStatement implements IStatement{
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIList<Value> out = state.getOutput();
        out.add(expression.eval(state.getSymTable(), state.getHeap()));
        //state.setOut(out);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Print(%s)", expression.toString());
    }
}
