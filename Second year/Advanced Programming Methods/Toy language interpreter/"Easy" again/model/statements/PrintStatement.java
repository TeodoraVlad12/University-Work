package model.statements;

import exceptions.InterpreterException;
import exceptions.MyException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyListInterface;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.Type;
import model.values.Value;

public class PrintStatement implements IStatement {
    private final IExpression IExpression;

    public PrintStatement(IExpression IExpression) {
        this.IExpression = IExpression;
    }

    @Override
    public String toString(){
        return "print(" + this.IExpression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyListInterface<Value> out1 = state.getOut();
        out1.add(IExpression.eval(state.getSymbolTable(), state.getHeap()));
        state.setOut(out1);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.IExpression);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        IExpression.typecheck(table);
        return table;
    }
}
