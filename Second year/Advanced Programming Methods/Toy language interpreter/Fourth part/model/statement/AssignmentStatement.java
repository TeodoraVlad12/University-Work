package model.statement;

import exceptions.MyException;
import model.expression.IExpression;
import model.programState.ProgramState;
import model.type.Type;
import model.utils.MyIDictionary;
import model.value.Value;

public class AssignmentStatement implements IStatement{
    private String key;
    private IExpression expression;

    public AssignmentStatement(String key, IExpression expression){
        this.key = key;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(key)){
            Value value = expression.eval(symTable, state.getHeap());
            Type typeId = (symTable.lookUp((key))).getType();
            if (value.getType().equals(typeId)){
                symTable.update(key, value);
            }
            else{
                throw new MyException("Declared type of variable " + key + "and the type of the assigned expression don't match....");
            }

        }
        else {
            throw new MyException("This variable has not been declared before:(((((");
        }
        state.setSymTable(symTable);
        return state;
    }

    @Override
    public IStatement deepCopy(){
        return new AssignmentStatement(key, expression.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("%s = %s", key, expression.toString());
    }

}
