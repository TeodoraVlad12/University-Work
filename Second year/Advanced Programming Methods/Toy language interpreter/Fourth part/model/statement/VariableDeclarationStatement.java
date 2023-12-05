package model.statement;

import exceptions.MyException;
import model.expression.VariableExpression;
import model.programState.ProgramState;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIList;
import model.value.Value;

public class VariableDeclarationStatement implements IStatement{
    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new MyException("Variable " + name + " has already been declared before.");
        } else {

            symTable.put(name, type.defaultValue());
            state.setSymTable(symTable);
            return state;
        }
    }

    @Override
    public IStatement deepCopy(){
        return new VariableDeclarationStatement(name, type);
    }

    @Override
    public String toString(){
        return String.format("%s %s", type.toString(), name);
    }
}
