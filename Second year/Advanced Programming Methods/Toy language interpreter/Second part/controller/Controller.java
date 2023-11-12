package controller;

import exceptions.MyException;
import model.programState.ProgramState;
import model.statement.IStatement;
import model.utils.MyIStack;
import repository.IRepository;

import java.io.IOException;

public class Controller {
    IRepository repo;
    boolean displayFlag = false;

    public Controller(IRepository repository){
        this.repo = repository;
    }

    public void setDisplayFlag(boolean val){
        this.displayFlag = val;
    }

    public ProgramState oneStep(ProgramState state) throws MyException{
        MyIStack<IStatement> stack = state.getExeStack();
        if (stack.isEmpty()){
            throw new MyException("Program state stack is emptyyyyyyy");
        }
        else {
            IStatement currStatement = stack.pop();
            state.setExeStack(stack);
            return currStatement.execute(state);
        }
    }

    public void allSteps() throws MyException, IOException {
        ProgramState program = this.repo.getCurrentState();
        this.repo.logPrgStateExec();
        display();
        while (!program.getExeStack().isEmpty()){
            oneStep(program);
            this.repo.logPrgStateExec();
            display();
        }
        System.out.println("Your result is: " + program.getOut().toString());
    }

    private void display(){
        if (displayFlag){
            System.out.println(this.repo.getCurrentState().toString());
        }
    }



}
