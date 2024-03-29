package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.type.RefType;
import model.type.Type;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.value.RefValue;
import model.value.Value;
import model.programState.ProgramState;

public class NewStatement implements IStatement{

    private final String varName;
    private final IExpression expression;

    public NewStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typevar = typeEnv.lookUp(varName);
        Type typeexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typeexp))){
            return typeEnv;
        } else
            throw new MyException("New statement: right hand side and left hand side have different types,");
    }



    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            Value varValue = symTable.lookUp(varName);
            if ((varValue.getType() instanceof RefType)) {
                Value evaluated = expression.eval(symTable, heap);
                Type locationType = ((RefValue) varValue).getLocationType();
                if (locationType.equals(evaluated.getType())) {
                    int newPosition = heap.addNewHeapEntry(evaluated);
                    symTable.put(varName, new RefValue(newPosition, locationType));
                    //state.setSymTable(symTable);
                    //state.setHeap(heap);
                } else
                    throw new MyException(String.format("%s not of %s", varName, evaluated.getType()));
            } else {
                throw new MyException(String.format("%s in not of RefType", varName));
            }
        } else {
            throw new MyException(String.format("%s not in symTable", varName));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }

}
