package View;

import Controller.Controller;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.MyList;
import Model.adt.MyStack;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.RefType;
import Model.types.StringType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.IRepo;
import Repo.Repo;

class Interpreter {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        try{
            ex1.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString() + " 1");
        }

        PrgState prg1 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex1);
        IRepo repo1 = new Repo(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                        new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));
        try{
            ex2.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 2");
        }
        PrgState prg2 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(),ex2);
        IRepo repo2 = new Repo(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));
        try{
            ex3.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 3");
        }
        PrgState prg3 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex3);
        IRepo repo3 = new Repo(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new openRFileStmt(new ValueExp(new StringValue("varf"))), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("varf")), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("varf")), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),  new closeRFileStmt(new ValueExp(new StringValue("varf"))) ))))))));
        try{
            ex4.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 4");
        }
        PrgState prg4 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex4);
        IRepo repo4 = new Repo(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);


        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                                new newStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new VarExp("a"))
                                , new PrintStmt(new VarExp("v"))))
                )
                ));
        try{
            ex5.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 5");
        }
        PrgState prg5 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex5);
        IRepo repo5 = new Repo(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new newStmt("v",
                new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new rHeap(new VarExp("v"))),
                new CompStmt(new wHeap("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp('+',
                        new rHeap(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        try{
            ex6.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 6");
        }
        PrgState prg6 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex6);
        IRepo repo6 = new Repo(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        //Ref int v;Ref Ref int u;new(v,20); new(u,v); new(v,30);print(rH(rH(u)))
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new VarDeclStmt(
                "u", new RefType(new RefType(new IntType()))), new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new newStmt("u", new VarExp("v") ), new CompStmt(new newStmt("v", new ValueExp(new IntValue(30))),
                        new PrintStmt(new rHeap(new rHeap(new VarExp("u")))))
        ))));
        try{
            ex7.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 7");
        }
        PrgState prg7 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex7);
        IRepo repo7 = new Repo(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        //int a; a = 3; While(a > 0) a--;print(a)
        IStmt ex8 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(3))),
                new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("a"), new ValueExp(new IntValue(0))), new AssignStmt("a",
                        new ArithExp('-', new VarExp("a"), new ValueExp(new IntValue(1))))),
                        new PrintStmt(new VarExp("a")))));
        try{
            ex8.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 8");
        }
        PrgState prg8 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex8);
        IRepo repo8 = new Repo(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);

        //int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a)));
        //   print(v);print(rH(a))
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new newStmt("a", new ValueExp(
                        new IntValue(22)
                )), new CompStmt(new forkStmt(new CompStmt(new wHeap("a", new ValueExp(new IntValue(30))), new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExp("v")),
                        new PrintStmt(new rHeap(new VarExp("a"))))))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(
                                new rHeap(new VarExp("a"))
                )))))));
        try{
            ex9.typecheck(new Dict<>());
        }
        catch(Exception e)
        {
            System.out.print(e.toString()+ " 9");
        }
        PrgState prg9 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex9);
        IRepo repo9 = new Repo(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", "First example", ctr1));
        menu.addCommand(new RunExample("2", "Second example", ctr2));
        menu.addCommand(new RunExample("3", "Third example", ctr3));
        menu.addCommand(new RunExample("4", "Fourth example", ctr4));
        menu.addCommand(new RunExample("5", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)", ctr5));
        menu.addCommand(new RunExample("6", "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);", ctr6));
        menu.addCommand(new RunExample("7", "Ref int v;Ref Ref int u;new(v,20); new(u,v); new(v,30);print(rH(rH(u)))", ctr7));
        menu.addCommand(new RunExample("8", "int a; a = 3; While(a > 0) a--; print(a)", ctr8));
        menu.addCommand(new RunExample("9", "int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))", ctr9));

        menu.show();
    }
}