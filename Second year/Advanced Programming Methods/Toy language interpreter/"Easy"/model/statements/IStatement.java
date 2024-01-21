package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;

    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    IStatement deepcopy();
}
