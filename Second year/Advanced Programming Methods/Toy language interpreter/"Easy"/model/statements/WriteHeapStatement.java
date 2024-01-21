package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.types.Type;
import model.types.RefType;
import model.values.Value;
import model.values.RefValue;

public class WriteHeapStatement implements IStatement {

    String variableName;
    IExpression expression;

    public WriteHeapStatement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public IStatement deepcopy() {
        return new NewStatement(variableName, expression.deepcopy());
    }

    @Override
    public String toString() {
        return "wH(" + variableName + ", " + expression + ");";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIDictionary<String, Value> symTable = state.getSymTable();

        if(!symTable.isDefined(variableName))
            throw new MyException("Variable isn't defined!");

        Value varValue = symTable.lookup(variableName);

        if(!(varValue.getType() instanceof RefType))
            throw new MyException("Variable is not of type reference!");

        MyIHeap<Integer, Value> heap = state.getHeap();

        if(!(heap.isDefined(((RefValue)varValue).getAddress())))
            throw new MyException("Address not in heap!");

        Value expressionValue = expression.evaluation(symTable, heap);

        if (!expressionValue.getType().equals(((RefType) varValue.getType()).getInner())) {
            throw new MyException("Types not equal!");
        }

        heap.update(((RefValue)varValue).getAddress(), expressionValue);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(variableName);
        Type typeExp = expression.typecheck(typeEnv);

        if (typeExp.equals(((RefType)typeVar).getInner()))
            return typeEnv;
        throw new MyException("WriteHeap: right hand side and left hand side have different types");
    }

}
