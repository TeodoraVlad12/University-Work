package model.statements;

import exceptions.InterpreterException;
import exceptions.MyException;
import exceptions.MyExcpetion;
import exceptions.TypeException;
import model.ADTs.MyDictionaryInterface;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements IStatement {
    private final String id;
    private final IExpression IExpression;

    public AssignmentStatement(String id, IExpression IExpression) {
        this.id = id;
        this.IExpression = IExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(id)) {
            Value val = IExpression.eval(symbolTable, state.getHeap());
            Type typeID = (symbolTable.lookup(id).getType());
            if (val.getType().equals(typeID)) {
                symbolTable.update(id, val);
            } else {
                throw new MyException("declared type of variable" + id +
                        "and type of the evaluation( of the assigned IExpression) do not match");
            }
        }
        else {
            throw new MyException("The variable " + id + " was not declared before");
        }
        state.setSymbolTable(symbolTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(this.id, this.IExpression);
    }

    @Override
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String, Type> table) throws MyException {
        Type variableType = table.lookup(id);
        Type expressionType = IExpression.typecheck(table);
        if (variableType.equals(expressionType)) {
            return table;
        } else {
            throw new MyException("Not the same type on assignment");
        }
    }

    @Override
    public String toString(){
        return id + "=" + IExpression.toString();
    }
}
