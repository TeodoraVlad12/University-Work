package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStatement implements IStatement {

    IExpression condition;
    IStatement body;

    public WhileStatement(IExpression condition, IStatement body){
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "while (" + this.condition.toString() + ") { " + this.body.toString() + " }";
    }

    @Override
    public IStatement deepcopy() {
        return new WhileStatement(condition.deepcopy(), body.deepcopy());
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        MyIStack<IStatement> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value value = condition.evaluation(symTable, heap);

        if (!value.getType().equals(new BoolType())) {
            throw new MyException("The condition expression is not a boolean!");
        }

        BoolValue boolValue = (BoolValue) value;

        if(boolValue.getVal()){
            exeStack.push(this);
            exeStack.push(body);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = condition.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            body.typecheck(typeEnv.copy());
            return typeEnv;
        }
        throw new MyException("The condition of WHILE has not the type bool");
    }
}
