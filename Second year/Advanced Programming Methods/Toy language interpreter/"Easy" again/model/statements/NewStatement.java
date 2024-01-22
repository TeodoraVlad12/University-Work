package model.statements;

import exceptions.InterpreterException;
import exceptions.MyException;
import exceptions.MyExcpetion;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class NewStatement implements IStatement {
    String varName;
    IExpression expReplacement;

    public NewStatement(String varName, IExpression IExpression) {
        this.varName = varName;
        this.expReplacement = IExpression;
    }

    @Override
    public String toString() {
        return "new(" + this.varName + ',' + this.expReplacement.toString() + ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Value> heap = state.getHeap();

        if (!symbolTable.isDefined(this.varName))
            throw new MyException("Variable " + this.varName + " is not defined in symbolTable!");
        Value refVal = symbolTable.lookup(this.varName);
        if (!(refVal.getType() instanceof RefType))
            throw new MyException("The value from SymbolTable doesn't have the type RefType!");

        Value newValue = this.expReplacement.eval(symbolTable, heap);
        if(!(newValue.getType().equals(((RefType) refVal.getType()).getInner())))
            throw new MyException("New expression's value type is not the same as the variable");

        int addr = heap.allocate(newValue);
        symbolTable.update(this.varName, new RefValue(addr, newValue.getType()));

        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(this.varName, this.expReplacement);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type variableType = table.lookup(this.varName);
        Type expressionType = this.expReplacement.typecheck(table);
        if (variableType.equals(new RefType(expressionType))) {
            return table;
        } else {
            throw new MyException("Different types on heap allocation");
        }
    }
}
