package repository;

import exceptions.MyException;
import model.programState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void setProgramState(List<ProgramState> prStates);
    ProgramState getCurrentState();
    void addProgram(ProgramState program);

    void logPrgStateExec() throws IOException, MyException;



}
