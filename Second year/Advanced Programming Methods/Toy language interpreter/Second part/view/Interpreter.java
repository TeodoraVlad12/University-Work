package view;

import controller.Controller;
import model.expression.ArithmeticExpression;
import model.expression.LogicExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.programState.ProgramState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.utils.MyDictionary;
import model.utils.MyList;
import model.utils.MyStack;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

public class Interpreter {

    public static void main(String[] args){

        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new NopStatement()));

        ProgramState prg1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller controller1 = new Controller(repo1);




        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new ArithmeticExpression('-', new VariableExpression("a"), new ArithmeticExpression('/', new ValueExpression(new IntValue(4)), new ValueExpression(new IntValue(2))) ), new ValueExpression(new IntValue(7)))),
                                        new PrintStatement(new VariableExpression("b"))))));

        ProgramState prg2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller controller2 = new Controller(repo2);



        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(false))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        ProgramState prg3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),  ex3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller controller3 = new Controller(repo3);

        IStatement ex4 =  new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement( new AssignmentStatement("a", new LogicExpression("or", new ValueExpression(new BoolValue(false)), new ValueExpression(new BoolValue(true)))), new PrintStatement(new VariableExpression("a"))));

        ProgramState prg4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller controller4 = new Controller(repo4);



        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))))))))));

        ProgramState prg5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex5);
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller controller5 = new Controller(repo5);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));
        menu.show();
    }
}
