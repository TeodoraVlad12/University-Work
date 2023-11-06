package controller;

import exceptions.MyException;
import model.programState.ProgramState;
import model.statement.IStatement;
import model.utils.MyIStack;
import repository.IRepository;

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

    public void allSteps() throws MyException {
        ProgramState program = this.repo.getCurrentState();
        display();
        while (!program.getExeStack().isEmpty()){
            oneStep(program);
            display();
        }
    }

    private void display(){
        if (displayFlag){
            System.out.println(this.repo.getCurrentState().toString());
        }
    }



}
