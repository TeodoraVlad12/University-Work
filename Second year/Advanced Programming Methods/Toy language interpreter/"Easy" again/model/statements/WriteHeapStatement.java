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

public class WriteHeapStatement implements IStatement {
    private final String varName;
    private final IExpression IExpression;

    public WriteHeapStatement(String varName, IExpression IExpression) {
        this.varName = varName;
        this.IExpression = IExpression;
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

        int refAddress = ((RefValue)refVal).getAddress();
        if (!heap.exists(refAddress))
            throw new MyException("Value does not exist on heap");

        Value valReplacement = this.IExpression.eval(symbolTable, heap);
        if(!valReplacement.getType().equals(heap.get(refAddress).getType()))
            throw new MyException("IExpression not of variable type");

        heap.put(refAddress, valReplacement);
        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.varName + ", " + this.IExpression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(this.varName, this.IExpression);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type expressionType = IExpression.typecheck(table);
        Type variableType = table.lookup(this.varName);
        if (variableType instanceof RefType) {
            RefType referenceType = (RefType) variableType;
            if (expressionType.equals(referenceType.getInner())) {
                return table;
            } else {
                throw new MyException("Not the same type on heap modification");
            }
        } else {
            throw new MyException("Variable not of reference type");
        }
    }
}
