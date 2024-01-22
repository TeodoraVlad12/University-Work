package model.statements;

import exceptions.InterpreterException;
import exceptions.MyException;
import model.ADTs.MyDictionaryInterface;
import model.ProgramState;
import model.types.Type;

public class NopStatement implements IStatement {
    public NopStatement() {}

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        return table;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
