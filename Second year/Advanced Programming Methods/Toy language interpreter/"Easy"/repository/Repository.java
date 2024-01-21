package repository;

import model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    List<ProgramState> programStates = new ArrayList<>();
    String filePath;

    public Repository(ProgramState ps, String filePath) {
        programStates.add(ps);
        this.filePath = filePath;
    }

    @Override
    public void addProgramState(ProgramState ps) {
        programStates.add(ps);
    }

    @Override
    public List<ProgramState> getCurrentProgramStatesList() {
        return programStates;
    }

    @Override
    public void setCurrentProgramStatesList(List<ProgramState> newProgramStatesList) {
        this.programStates = newProgramStatesList;
    }

    @Override
    public void logProgramStateExecution(ProgramState ps){
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, true)))) {
            logFile.write(ps.toString());
            logFile.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

