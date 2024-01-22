package view;

import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

public class Examples {

    IStatement example1 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                    new NopStatement()
            )
    );
    IStatement example2 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(
                            new AssignmentStatement(
                                    "a",
                                    new ArithmeticExpression(
                                            '+',
                                            new ValueExpression(new IntValue(2)),
                                            new ArithmeticExpression('*',
                                                    new ValueExpression(new IntValue(3)),
                                                    new ValueExpression(new IntValue(5))
                                            )
                                    )
                            ),
                            new CompoundStatement(
                                    new AssignmentStatement(
                                            "b",
                                            new ArithmeticExpression('+',
                                                    new VariableExpression("a"),
                                                    new ValueExpression(new IntValue(1))
                                            )
                                    ),
                                    new PrintStatement(new VariableExpression("b"))
                            )
                    )
            )
    );
    IStatement example3 = new CompoundStatement(
            new VariableDeclarationStatement("a", new BoolType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(
                                    new IfStatement(
                                            new VariableExpression("a"),
                                            new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                            new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            )
    );

    IStatement example4 = new CompoundStatement(
            new VariableDeclarationStatement("varf", new StringType()),
            new CompoundStatement(
                    new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new CompoundStatement(
                            new OpenReadFile(new VariableExpression("varf")),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("varc", new IntType()),
                                    new CompoundStatement(
                                            new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("varc")),
                                                    new CompoundStatement(
                                                           new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                           new CompoundStatement(
                                                                   new PrintStatement(new VariableExpression("varc")),
                                                                   new CloseReadFile(new VariableExpression("varf"))
                                                           )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    IStatement example5 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new NewStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(new VariableExpression("a"))
                                    )
                            )
                    )
            )
    );

    IStatement example6 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new NewStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                            new PrintStatement(
                                                    new ArithmeticExpression(
                                                            '+',
                                                                new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                                new ValueExpression(new IntValue(5))
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    IStatement example7 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                            new CompoundStatement(
                                    new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new ArithmeticExpression(
                                            '+',
                                                new ReadHeapExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5))
                                    ))
                            )
                    )
            )
    );

    IStatement example8 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new NewStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new NewStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(
                                                    new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))
                                            )
                                    )
                            )
                    )
            )
    );

    IStatement example9 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                    new CompoundStatement(
                            new WhileStatement(
                                    new RelationalExpression(
                                            ">",
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntValue(0))),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new AssignmentStatement(
                                                    "v",
                                                    new ArithmeticExpression(
                                                            '-',
                                                            new VariableExpression("v"),
                                                            new ValueExpression(new IntValue(1)))
                                            )
                                    )
                            ),
                            new PrintStatement(new VariableExpression("v"))
                    )
            )
    );


    IStatement example10 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("a", new RefType(new IntType())),
                    new CompoundStatement(
                            new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                            new CompoundStatement(
                                    new NewStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompoundStatement(
                                            new ForkStatement(
                                                    new CompoundStatement(
                                                            new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                            new CompoundStatement(
                                                                    new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new VariableExpression("v")),
                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                            )
                                    )
                            )
                    )
            )
    );


    IStatement example11 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))))));


    IStatement example12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                    new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                            new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

    IStatement example13 = new CompoundStatement(new VariableDeclarationStatement("counter", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                    new WhileStatement(new RelationalExpression("<", new VariableExpression("counter"), new ValueExpression(new IntValue(10))),
                            new CompoundStatement(new ForkStatement(new ForkStatement(new CompoundStatement(new NewStatement("a", new VariableExpression("counter")),
                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))),
                                    new AssignmentStatement("counter", new ArithmeticExpression('+', new VariableExpression("counter"), new ValueExpression(new IntValue(1))))))));

    IStatement example14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new AssignmentStatement("v", new ValueExpression(new BoolValue(true))));

    IStatement example15 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(10))),
                    new ForStatement("i", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(5)), new ArithmeticExpression('+', new VariableExpression("i"), new ValueExpression(new IntValue(1))), new PrintStatement(new VariableExpression("i")))));

    /*IStatement example15 = new CompoundStatement(new VariableDeclarationStatement("i", new IntType()),
            new CompoundStatement(new AssignmentStatement("i", new ValueExpression(new IntValue(10))),
                    new model.statement.ForStatement("i", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(5)), new ArithmeticExpression('+', new VariableExpression("i"), new ValueExpression(new IntValue(1))), new PrintStatement(new VariableExpression("i")))));
*/

    IStatement example16 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                    new CompoundStatement(new DoWhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(5))),
                            new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                            new PrintStatement(new VariableExpression("v")))));

    IStatement example17 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                    new CompoundStatement(new RepeatUntilStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))), new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(5)))),
                            new PrintStatement(new VariableExpression("v")))));

    IStatement example18 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
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

    IStatement example19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                    new CompoundStatement(new WhileStatement(new RelationalExpression("<", new VariableExpression("v"), new ValueExpression(new IntValue(3))),
                            new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                    new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                    new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                            new CompoundStatement(new SleepStatement(5), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))))));

    IStatement example20 =  new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new WaitStatement(10),
                            new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

    IStatement example21 = new CompoundStatement(new VariableDeclarationStatement("b", new BoolType()),
            new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                    new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(new ConditionalAssignmentStatement("c", new VariableExpression("b"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                    new CompoundStatement(new PrintStatement(new VariableExpression("c")),
                                            new CompoundStatement(new ConditionalAssignmentStatement("c", new ValueExpression(new BoolValue(false)), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                    new PrintStatement(new VariableExpression("c"))))))));

    //for
    //IStatement example22 =new CompoundStatement(new VariableDeclarationStatement("v",new IntType()) ,new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())), new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(20))), new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)), new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)),"+"), new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a")),"*"))))), new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))));

    IStatement example22 =new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())), new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(20))), new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)), new ArithmeticExpression('+',new VariableExpression("v"), new ValueExpression(new IntValue(1))), new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('*',new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a"))))))), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));

    //repeat until
    IStatement example23 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                    new CompoundStatement(new RepeatUntilStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-',new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new AssignmentStatement("v", new ArithmeticExpression('+',new VariableExpression("v"), new ValueExpression(new IntValue(1))))) , new RelationalExpression("==", new VariableExpression("v"), new ValueExpression(new IntValue(3)))), new PrintStatement(new ArithmeticExpression('*',new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

    //sleep
    IStatement example24 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))), new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignmentStatement("v", new ArithmeticExpression('-',new VariableExpression("v"), new ValueExpression(new IntValue(1)))), new CompoundStatement(new AssignmentStatement("v", new ArithmeticExpression('-',new VariableExpression("v"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("v"))))), new CompoundStatement(new SleepStatement(10), new PrintStatement(new ArithmeticExpression('*',new VariableExpression("v"), new ValueExpression(new IntValue(10))))))));

    //MUL
    IStatement example25 = new CompoundStatement(new VariableDeclarationStatement("v1", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("v2", new IntType()),
                    new CompoundStatement(new AssignmentStatement("v1", new ValueExpression(new IntValue(2))),
                            new CompoundStatement(new AssignmentStatement("v2", new ValueExpression(new IntValue(3))),
                                    new IfStatement(new ValueExpression(new BoolValue(true)), new PrintStatement(new MULExpression(new VariableExpression("v1"), new VariableExpression("v2"))), new PrintStatement(new VariableExpression("v2")))))));

    public IStatement[] exampleList(){
        return new IStatement[]{example1, example2, example3, example4, example5, example6, example7, example8,
        example9, example10,example11,example12,example13,example14,example15,example16
        ,example17,example18,example19,example20,example21,example22,example23,example24,example25};
    }
}
