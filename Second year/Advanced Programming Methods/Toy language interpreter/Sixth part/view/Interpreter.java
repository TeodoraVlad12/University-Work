package view;

import controller.Controller;
import exceptions.MyException;
import model.expression.*;
import model.programState.ProgramState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.utils.MyDictionary;
import model.utils.MyHeap;
import model.utils.MyList;
import model.utils.MyStack;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repo.IRepository;
import repo.Repository;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args){

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new NopStatement()));

        try {
            ex1.typecheck(new MyDictionary<>());
            ProgramState prg1 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new MyDictionary<>(), new MyHeap<Value>(), ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller controller1 = new Controller(repo1);
            menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }




        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new ArithmeticExpression('-', new VariableExpression("a"), new ArithmeticExpression('/', new ValueExpression(new IntValue(4)), new ValueExpression(new IntValue(2))) ), new ValueExpression(new IntValue(7)))),
                                        new PrintStatement(new VariableExpression("b"))))));

        try {
        ex2.typecheck(new MyDictionary<>());
        ProgramState prg2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller controller2 = new Controller(repo2);
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }



        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(false))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        try {
        ex3.typecheck(new MyDictionary<>());
        ProgramState prg3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap(),  ex3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller controller3 = new Controller(repo3);
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        IStatement ex4 =  new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement( new AssignmentStatement("a", new LogicExpression("or", new ValueExpression(new BoolValue(false)), new ValueExpression(new BoolValue(true)))), new PrintStatement(new VariableExpression("a"))));

        try{
        ex4.typecheck(new MyDictionary<>());
        ProgramState prg4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller controller4 = new Controller(repo4);
            menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }




        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))))))))));

        try{
        ex5.typecheck(new MyDictionary<>());
        ProgramState prg5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex5);
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller controller5 = new Controller(repo5);
            menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        //int a;
        //a=2;
        //int b;
        //b=12;
        //a<=b
        IStatement ex6 =  new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement( new AssignmentStatement("a", new RelationalExpression(">=", new ValueExpression(new IntValue(2)), new ValueExpression(new IntValue(12)))), new PrintStatement(new VariableExpression("a"))));

        try{
        ex6.typecheck(new MyDictionary<>());
        ProgramState prg6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex6);
        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller controller6 = new Controller(repo6);
            menu.addCommand(new RunExampleCommand("6", ex6.toString(), controller6));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new AssignmentStatement("a",new ValueExpression( new IntValue(5))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("a"), new ValueExpression(new IntValue(1))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new AssignmentStatement("a", new ArithmeticExpression('-', new VariableExpression("a"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("a")))));

        try{
        ex7.typecheck(new MyDictionary<>());
        ProgramState prg7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex7);
        IRepository repo7 = new Repository(prg7, "log7.txt");
        Controller controller7 = new Controller(repo7);
            menu.addCommand(new RunExampleCommand("7", ex7.toString(), controller7));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

        try{
        ex8.typecheck(new MyDictionary<>());
        ProgramState prg8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex8);
        IRepository repo8 = new Repository(prg8, "log8.txt");
        Controller controller8 = new Controller(repo8);
            menu.addCommand(new RunExampleCommand("8", ex8.toString(), controller8));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+',new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));

        try{
        ex9.typecheck(new MyDictionary<>());
        ProgramState prg9 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex9);
        IRepository repo9 = new Repository(prg9, "log9.txt");
        Controller controller9 = new Controller(repo9);
            menu.addCommand(new RunExampleCommand("9", ex9.toString(), controller9));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement( new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

        try{
        ex10.typecheck(new MyDictionary<>());
        ProgramState prg10 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex10);
        IRepository repo10 = new Repository(prg10, "log10.txt");
        Controller controller10 = new Controller(repo10);
            menu.addCommand(new RunExampleCommand("10", ex10.toString(), controller10));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }




        IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))))));

        try{
        ex11.typecheck(new MyDictionary<>());
        ProgramState prg11 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
        IRepository repo11 = new Repository(prg11, "log11.txt");
        Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunExampleCommand("11", ex11.toString(), controller11));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }




        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

        try{
        ex12.typecheck(new MyDictionary<>());
        ProgramState prg12 = new ProgramState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex12);
        IRepository repo12 = new Repository(prg12, "log12.txt");
        Controller controller12 = new Controller(repo12);
            menu.addCommand(new RunExampleCommand("12", ex12.toString(), controller12));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }



        IStatement ex13 = new CompoundStatement(new VariableDeclarationStatement("counter", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new WhileStatement(new RelationalExpression("<", new VariableExpression("counter"), new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new ForkStatement(new ForkStatement(new CompoundStatement(new NewStatement("a", new VariableExpression("counter")),
                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))),
                                        new AssignmentStatement("counter", new ArithmeticExpression('+', new VariableExpression("counter"), new ValueExpression(new IntValue(1))))))));

        try{
        ex13.typecheck(new MyDictionary<>());
        ProgramState prg13 = new ProgramState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex13);
        IRepository repo13 = new Repository(prg13, "log13.txt");
        Controller controller13 = new Controller(repo13);
            menu.addCommand(new RunExampleCommand("13", ex13.toString(), controller13));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new BoolValue(true))));

        //IStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
             //   new AssignmentStatement("v", new ValueExpression(new IntValue(10))));


        try{
            ex14.typecheck(new MyDictionary<>());
            ProgramState prg14 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex14);
            IRepository repo14 = new Repository(prg14, "log14.txt");
            Controller controller14 = new Controller(repo14);
            menu.addCommand(new RunExampleCommand("14", ex14.toString(), controller14));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex15 = new CompoundStatement(new VariableDeclarationStatement("i", new IntType()),
                new CompoundStatement(new AssignmentStatement("i", new ValueExpression(new IntValue(10))),
                        new ForStatement("i", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(5)), new ArithmeticExpression('+', new VariableExpression("i"), new ValueExpression(new IntValue(1))), new PrintStatement(new VariableExpression("i")))));


        try{
            ex15.typecheck(new MyDictionary<>());
            ProgramState prg15 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex15);
            IRepository repo15 = new Repository(prg15, "log15.txt");
            Controller controller15 = new Controller(repo15);
            menu.addCommand(new RunExampleCommand("15", ex15.toString(), controller15));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex16 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                        new CompoundStatement(new DoWhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(5))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        try{
            ex16.typecheck(new MyDictionary<>());
            ProgramState prg16 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex16);
            IRepository repo16 = new Repository(prg16, "log16.txt");
            Controller controller16 = new Controller(repo16);
            menu.addCommand(new RunExampleCommand("16", ex16.toString(), controller16));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex17 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                        new CompoundStatement(new RepeatUntilStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))), new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(5)))),
                                new PrintStatement(new VariableExpression("v")))));

        try{
            ex17.typecheck(new MyDictionary<>());
            ProgramState prg17 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex17);
            IRepository repo17 = new Repository(prg17, "log17.txt");
            Controller controller17 = new Controller(repo17);
            menu.addCommand(new RunExampleCommand("17", ex17.toString(), controller17));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex18 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(new SwitchStatement(
                                                                new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
                                                                new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                                                new ValueExpression(new IntValue(10)),
                                                                new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))
                                                        ), new PrintStatement(new ValueExpression(new IntValue(300))))))))));

        try{
            ex18.typecheck(new MyDictionary<>());
            ProgramState prg18 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex18);
            IRepository repo18 = new Repository(prg18, "log18.txt");
            Controller controller18 = new Controller(repo18);
            menu.addCommand(new RunExampleCommand("18", ex18.toString(), controller18));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }


        IStatement ex19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(3))),
                                new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new CompoundStatement(new SleepStatement(5), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))))));
        try{
            ex19.typecheck(new MyDictionary<>());
            ProgramState prg19 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex19);
            IRepository repo19 = new Repository(prg19, "log19.txt");
            Controller controller19 = new Controller(repo19);
            menu.addCommand(new RunExampleCommand("19", ex19.toString(), controller19));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        IStatement ex20 =  new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new WaitStatement(10),
                                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

        try {
            ex2.typecheck(new MyDictionary<>());
            ProgramState prg20 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex20);
            IRepository repo20 = new Repository(prg20, "log20.txt");
            Controller controller20 = new Controller(repo20);
            menu.addCommand(new RunExampleCommand("20", ex20.toString(), controller20));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }



        menu.show();





        //TextMenu menu = new TextMenu();
        //menu.addCommand(new ExitCommand("0", "exit"));
        //menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        //menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        //menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        //menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        //menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));
        //menu.addCommand(new RunExampleCommand("6", ex6.toString(), controller6));
        //menu.addCommand(new RunExampleCommand("7", ex7.toString(), controller7));
        //menu.addCommand(new RunExampleCommand("8", ex8.toString(), controller8));
        //menu.addCommand(new RunExampleCommand("9", ex9.toString(), controller9));
        //menu.addCommand(new RunExampleCommand("10", ex10.toString(), controller10));
        //menu.addCommand(new RunExampleCommand("11", ex11.toString(), controller11));
        //menu.addCommand(new RunExampleCommand("12", ex12.toString(), controller12));
        //menu.addCommand(new RunExampleCommand("13", ex13.toString(), controller13));
        //menu.show();



    }
}
