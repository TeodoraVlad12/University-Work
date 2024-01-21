package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements IStatement {
    String id;
    IExpression expression;

    public AssignmentStatement(String id, IExpression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return id + "=" + expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if(symTable.isDefined(id))
        {
            Value value = expression.evaluation(symTable, heap);
            Type typeId = (symTable.lookup(id)).getType();

            if(value.getType().equals(typeId))
                symTable.update(id, value);
            else throw new MyException("declared type of variable " +id+" and type of the assigned expression do not match");
        }
        else throw new MyException("the used variable " +id + " was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = expression.typecheck(typeEnv);
        if(typeVar.equals(typeExp))
            return typeEnv;
        throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public IStatement deepcopy() {
        return new AssignmentStatement(id, expression.deepcopy());
    }
}
