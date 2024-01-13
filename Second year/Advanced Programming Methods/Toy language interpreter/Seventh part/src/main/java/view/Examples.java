package view;

import model.expression.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

public class Examples {

    IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                    new NopStatement()));

    IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                            new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new ArithmeticExpression('-', new VariableExpression("a"), new ArithmeticExpression('/', new ValueExpression(new IntValue(4)), new ValueExpression(new IntValue(2))) ), new ValueExpression(new IntValue(7)))),
                                    new PrintStatement(new VariableExpression("b"))))));

    IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
            new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(false))),
                            new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

    IStatement ex4 =  new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement( new AssignmentStatement("a", new LogicExpression("or", new ValueExpression(new BoolValue(false)), new ValueExpression(new BoolValue(true)))), new PrintStatement(new VariableExpression("a"))));

    IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
            new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new CompoundStatement(new OpenReadFile(new VariableExpression("varf")),
                            new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                    new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                    new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                            new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                    new CloseReadFile(new VariableExpression("varf"))))))))));

    IStatement ex6 =  new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new CompoundStatement( new AssignmentStatement("a", new RelationalExpression(">=", new ValueExpression(new IntValue(2)), new ValueExpression(new IntValue(12)))), new PrintStatement(new VariableExpression("a"))));

    IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(new AssignmentStatement("a",new ValueExpression( new IntValue(5))),
                    new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("a"), new ValueExpression(new IntValue(1))),
                            new CompoundStatement(new PrintStatement(new VariableExpression("a")), new AssignmentStatement("a", new ArithmeticExpression('-', new VariableExpression("a"), new ValueExpression(new IntValue(1)))))),
                            new PrintStatement(new VariableExpression("a")))));


    IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));


    IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                            new PrintStatement(new ArithmeticExpression('+',new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));

    IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement( new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                            new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

    IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))))));


    IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                    new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                            new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

    IStatement ex13 = new CompoundStatement(new VariableDeclarationStatement("counter", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                    new WhileStatement(new RelationalExpression("<", new VariableExpression("counter"), new ValueExpression(new IntValue(10))),
                            new CompoundStatement(new ForkStatement(new ForkStatement(new CompoundStatement(new NewStatement("a", new VariableExpression("counter")),
                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))),
                                    new AssignmentStatement("counter", new ArithmeticExpression('+', new VariableExpression("counter"), new ValueExpression(new IntValue(1))))))));

    IStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new AssignmentStatement("v", new ValueExpression(new BoolValue(true))));

    IStatement ex15 = new CompoundStatement(new VariableDeclarationStatement("i", new IntType()),
            new CompoundStatement(new AssignmentStatement("i", new ValueExpression(new IntValue(10))),
                    new ForStatement("i", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(5)), new ArithmeticExpression('+', new VariableExpression("i"), new ValueExpression(new IntValue(1))), new PrintStatement(new VariableExpression("i")))));

    IStatement ex16 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                    new CompoundStatement(new DoWhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(5))),
                            new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                            new PrintStatement(new VariableExpression("v")))));

    IStatement ex17 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                    new CompoundStatement(new RepeatUntilStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))), new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(5)))),
                            new PrintStatement(new VariableExpression("v")))));

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

    IStatement ex19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                    new CompoundStatement(new WhileStatement(new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(3))),
                            new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                    new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                    new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                            new CompoundStatement(new SleepStatement(5), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))))));

    IStatement ex20 =  new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new WaitStatement(10),
                            new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

    public IStatement[] exampleList(){
        return new IStatement[]{ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12, ex13, ex14, ex15, ex16, ex17, ex18, ex19, ex20};
    }
}
