package model.statement;

import exceptions.MyException;
import model.programState.ProgramState;
import model.utils.MyDictionary;
import model.utils.MyStack;
import model.value.Value;

import java.util.Map;
import java.util.stream.Collectors;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement stmt) {
        this.statement = stmt;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws MyException {
        MyStack<IStatement> forkedExecutionStack = new MyStack<>();     // need to create a new execution stack

        Map<String, Value> symbolTableContent = currentState.getSymTable().getContent();    // need to create a deep copy of the symbol table
        MyDictionary<String, Value> forkedSymbolTable = new MyDictionary<>();
        forkedSymbolTable.setContent(symbolTableContent.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().deepCopy())));
        return new ProgramState(forkedExecutionStack, forkedSymbolTable, currentState.getOutput(), currentState.getFileTable(), currentState.getHeap(), this.statement);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
