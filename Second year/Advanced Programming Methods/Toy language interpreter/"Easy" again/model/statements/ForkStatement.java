package model.statements;


import exceptions.InterpreterException;
import exceptions.MyException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStack;
import model.ADTs.MyStackInterface;
import model.ProgramState;
import model.types.Type;
import model.values.Value;

public class ForkStatement implements IStatement {
    private final IStatement forkStatement;

    public ForkStatement(IStatement forkStatement){
        this.forkStatement = forkStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyStackInterface<IStatement> newExeStack = new MyStack<>();
        newExeStack.push(forkStatement);
        MyDictionaryInterface<String, Value> newSymTable;
        newSymTable = state.getSymbolTable().deepCopy();

        ProgramState newProgramState = new ProgramState(newExeStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap());
        newProgramState.setId();
        return newProgramState;
    }


    @Override
    public IStatement deepCopy() {
        return new ForkStatement(this.forkStatement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        forkStatement.typecheck(table.deepCopy());
        return table;
    }

    @Override
    public String toString() {
        return "fork(" + this.forkStatement.toString() + ")";
    }

}
