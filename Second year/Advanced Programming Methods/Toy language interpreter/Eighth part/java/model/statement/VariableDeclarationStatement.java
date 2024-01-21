package model.statement;

import exceptions.MyException;
import model.type.Type;
import model.utils.MyIDictionary;
import model.value.Value;
import model.programState.ProgramState;

public class VariableDeclarationStatement implements IStatement{
    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new MyException("Variable " + name + " has already been declared before.");
        } else {

            symTable.put(name, type.defaultValue());
            //state.setSymTable(symTable);
            return null;
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
