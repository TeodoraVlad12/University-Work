package model.statements;

import exceptions.AdtException;
import exceptions.InterpreterException;
import exceptions.MyException;
import exceptions.MyExcpetion;
import model.ADTs.MyDictionaryInterface;
import model.ProgramState;
import model.types.Type;
import model.values.Value;


public class VariableDeclarationStatement implements IStatement {
    private final String name;
    private final Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        if(symbolTable.isDefined(name))
            throw new MyException("Variable is already declared");
        else{
            symbolTable.add(name, type.getDefault());
        }
        state.setSymbolTable(symbolTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(this.name, this.type);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        table.add(name, type);
        return table;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
