package view;

import controller.Controller;
import exceptions.MyException;
import model.expression.ArithmeticExpression;
import model.expression.LogicExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.programState.ProgramState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.utils.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.util.Scanner;

public class View {

    public View(){};

    public static void printMenu() {
        System.out.println("\n1. First example");
        System.out.println("2. Second example");
        System.out.println("3. Third example");
        System.out.println("4. Fourth example");
        System.out.println("0. Exit\n");
        System.out.println("Please choose one >> ");
    }

    public void start(){
        while(true){
            try {
                printMenu();
                Scanner readOption = new Scanner(System.in);
                int option = readOption.nextInt();
                if (option == 1) {
                    example1();
                } else if (option == 2) {
                    example2();
                } else if (option == 3) {
                    example3();
                } else if (option == 4) {
                    example4();
                }else if (option == 0) {
                    return;
                } else {
                    System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void example1() throws MyException {
        IStatement ex = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new NopStatement()));
        runProgram(ex);
        System.out.println("\nThe example was:\nint v;\nv=2;\nPrint(v);");
    }

    public void example2() throws MyException{
        IStatement ex = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new ArithmeticExpression('-', new VariableExpression("a"), new ArithmeticExpression('/', new ValueExpression(new IntValue(4)), new ValueExpression(new IntValue(0))) ), new ValueExpression(new IntValue(7)))),
                                        new PrintStatement(new VariableExpression("b"))))));
        runProgram(ex);
        System.out.println("\nThe example was:\nint a;\nint b;\na=2+3*5;\nb=a-4/2+7;\nPrint(b);");
    }

    public void example3() throws MyException {
        IStatement ex = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(false))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        runProgram(ex);
        System.out.println("\nThe example was:\nbool a;\na=false;\nint v;\nIf a Then v=2 Else v=3;\nPrint(v);");
    }

    //a = false or true,print(a)
    public void example4() throws MyException{
        IStatement ex = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement( new AssignmentStatement("a", new LogicExpression("or", new ValueExpression(new BoolValue(false)), new ValueExpression(new BoolValue(true)))), new PrintStatement(new VariableExpression("a"))));
        runProgram(ex);
        System.out.println("\nThe example was:\nbool a;\na=false or true;\nPrint(a);");
    }

    public void runProgram(IStatement stmt) throws MyException{
        MyIStack<IStatement> exeStack = new MyStack<>();
        MyIDictionary<String, Value> symTable = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();

        ProgramState state = new ProgramState(exeStack, symTable, out, stmt);

        IRepository repo = new Repository(state);
        Controller controller = new Controller(repo);

        System.out.println("Do you want the steps displayed? Enter y or n: ");
        Scanner readInput = new Scanner(System.in);
        String option = readInput.next();
        controller.setDisplayFlag(option.equals("y"));
        //controller.oneStep(state);
        controller.allSteps();

        System.out.println("Your result is: " + state.getOut().toString());
    }
}
