package repository;

import model.programState.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> programStates;
    private int currentPosition;

    public Repository(ProgramState programState){
        this.programStates = new ArrayList<>();
        this.currentPosition = 0;
        this.addProgram(programState);
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }

    public void setCurrentPosition(int currPos){
        this.currentPosition = currPos;
    }

    @Override
    public List<ProgramState> getProgramList(){
        return this.programStates;
    }

    @Override
    public void setProgramState(List<ProgramState> prStates){
        this.programStates = prStates;
    }

    @Override
    public void addProgram(ProgramState pr){
        this.programStates.add(pr);
    }

    @Override
    public ProgramState getCurrentState(){
        return this.programStates.get(this.currentPosition);
    }
}
