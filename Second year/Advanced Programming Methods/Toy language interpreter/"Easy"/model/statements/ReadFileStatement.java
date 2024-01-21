package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.types.Type;
import model.types.IntType;
import model.types.StringType;
import model.values.Value;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;


public class ReadFileStatement implements IStatement {

    IExpression expression;
    String varName;

    public ReadFileStatement(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression.toString() + ", " + this.varName + ");";
    }

    @Override
    public IStatement deepcopy() {
        return new ReadFileStatement(expression.deepcopy(), varName);
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new MyException("Variable " + varName + " isn't defined!");

        Value value = symTable.lookup(varName);

        if (!value.getType().equals(new IntType()))
            throw new MyException("Variable type isn't int!");

        Value expressionValue = expression.evaluation(symTable, heap);

        if (!expressionValue.getType().equals(new StringType()))
            throw new MyException("The expression isn't string type!");

        StringValue fileName = (StringValue) expressionValue;

        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        BufferedReader bufferedReader = fileTable.lookup(fileName);

        if (bufferedReader == null) {
            throw new MyException("File with given name doesn't exist!");
        }

        String readValue;
        IntValue intValue;

        try {
            readValue = bufferedReader.readLine();
        } catch (Exception e) {
            throw new MyException(e.toString());
        }

        if (readValue == null)
            intValue = new IntValue(0);
        else
            intValue = new IntValue(Integer.parseInt(readValue));

        symTable.update(varName, intValue);


        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typecheck(typeEnv);

        if (!(expressionType instanceof StringType))
            throw new MyException("ReadFileStatement: The expression isn't string type!");

        Type variableType = typeEnv.lookup(varName);
        if (!(variableType instanceof IntType))
            throw new MyException("ReadFileStatement: The given variable is not of type IntType.");


        return typeEnv;
    }
}
