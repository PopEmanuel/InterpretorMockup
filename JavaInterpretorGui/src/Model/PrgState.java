package Model;
import Exceptions.EmptyMyStackException;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.EmptyStackException;

public class PrgState {

    static int next_id = 1;
    private int id;
    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<String> out;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap<IValue> HeapTable;
    IStmt originalProgram;

    public int getId()
    {
        return this.id;
    }

    public String toString()
    {
        //String string = "program state : Stack = ";


        return "\nProgram State number " + id +"\nExeStack\n" + exeStack.toString() + "\nSymTable\n" +
                symTable.toString() + "\nOutput\n" + out.toString() +
                "\nFileTable\n" + fileTable.toString() + "\nHeapTable\n" + HeapTable.toString();

    }

    public PrgState(IStack<IStmt> stk, Dict<String,IValue> symtbl, MyList<String> ot, IStmt prg)
    {
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        fileTable = new Dict<StringValue, BufferedReader>();
        HeapTable = new Heap<IValue>();
        id = next_id;
        next_id += 1;

        stk.push(prg);
    }

    public PrgState(IStack<IStmt> stk, Dict<String,IValue> symtbl, MyList<String> ot, IStmt prg, Dict<StringValue, BufferedReader> flTable,
            IHeap<IValue> HpTable)
    {
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        fileTable = flTable;
        HeapTable = HpTable;

        id = next_id;
        next_id += 1;

        stk.push(prg);
    }


    public IStack<IStmt> getStack()
    {
        return this.exeStack;
    }
    public IList<String> getOutput() {

        return this.out;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception{
        if(exeStack.isEmpty())
            throw new EmptyMyStackException("The stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }


    public IDict<StringValue, BufferedReader> getFileTable() {return this.fileTable;}

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public void setOutput(IList<String> output) {
        this.out = output;
    }

    public IHeap<IValue> getHeapTable() {return HeapTable;}
}