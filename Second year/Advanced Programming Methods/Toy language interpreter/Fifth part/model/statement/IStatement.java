package model.statement;

import exceptions.MyException;
import model.programState.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IStatement deepCopy();
}
