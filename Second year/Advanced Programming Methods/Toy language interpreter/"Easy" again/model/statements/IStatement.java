package model.statements;

import exceptions.InterpreterException;
import exceptions.MyException;
import model.ADTs.MyDictionaryInterface;
import model.ProgramState;
import model.types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    // which is the execution method for a statement
    IStatement deepCopy();
    MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException;
}
