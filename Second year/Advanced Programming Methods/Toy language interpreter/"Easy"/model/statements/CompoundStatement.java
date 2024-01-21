package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.types.Type;

public class CompoundStatement implements IStatement {
    IStatement firstStmt, secondStmt;

    public CompoundStatement(IStatement firstStmt, IStatement secondStmt){
        this.firstStmt = firstStmt;
        this.secondStmt = secondStmt;
    }
    public String toString() {
        return "("+firstStmt.toString() + ";" + secondStmt.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack=state.getExeStack();
        stack.push(secondStmt);
        stack.push(firstStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return secondStmt.typecheck(firstStmt.typecheck(typeEnv));
    }

    @Override
    public IStatement deepcopy() {
        return new CompoundStatement(firstStmt.deepcopy(), secondStmt.deepcopy());
    }


}
