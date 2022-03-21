package Model.stmt;

import Model.PrgState;
import Model.adt.*;
import Model.types.IType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class forkStmt implements IStmt{
    IStmt statement;

    public forkStmt(IStmt stmt) { statement = stmt;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyStack<IStmt> stk = new MyStack<>();
        Dict<String, IValue> symTable = (Dict<String, IValue>) state.getSymTable().deepCopy();
        MyList<String> out = (MyList<String>) state.getOutput();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<IValue> HeapTable = state.getHeapTable();


        return new PrgState(stk, symTable, out, statement, (Dict<StringValue, BufferedReader>) fileTable, HeapTable);
    }

    public String toString()
    {
        return "fork()";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        statement.typecheck(typeEnv);
        return typeEnv;
    }
}
