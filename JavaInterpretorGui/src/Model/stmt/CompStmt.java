package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt snd;

    public CompStmt(IStmt frst, IStmt snd)
    {
        this.first = frst;

        this.snd = snd;

    }
    @Override
    public PrgState execute(PrgState state) {

        IStack<IStmt> stack = state.getStack();
        stack.push(snd);
        stack.push(first);
        return null;
    }

    public  String toString() {
        return first.toString()+ "\n" + snd.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        return snd.typecheck(first.typecheck(typeEnv));
    }
}
