package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class VariableDeclarationStatement implements IStatement {
    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + name ;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();

        if(symTable.isDefined(name)){
            throw new MyException("Variable already declared");
        }
        else{
            symTable.update(name, type.getDefaultValue());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.update(name, type);
        return typeEnv;
    }

    @Override
    public IStatement deepcopy() {
        return new VariableDeclarationStatement(name, type.deepcopy());
    }
}
