package repository;

import model.programState.ProgramState;

import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void setProgramState(List<ProgramState> prStates);
    ProgramState getCurrentState();
    void addProgram(ProgramState program);


}
