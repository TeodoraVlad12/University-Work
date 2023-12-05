package repo;

import exceptions.MyException;
import model.programState.ProgramState;

import java.util.List;

public interface IRepository {
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> newProgramStates);
    void addProgramState(ProgramState newProgramState);
    void logPrgStateExec(ProgramState programState) throws MyException;

}
