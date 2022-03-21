package Model.stmt;


import Exceptions.ArtihmeticException;
import Exceptions.DiferentTypeException;
import Exceptions.InvalidIdException;
import Exceptions.LogicOperationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class wHeap implements IStmt {
    String var_name;
    Exp expression;

    public wHeap(String var, Exp expr)
    {
        var_name = var;
        expression = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if(!state.getSymTable().isDefined(var_name))
            throw new InvalidIdException("No entry in symtable");

        if(!(state.getSymTable().lookup(var_name).getType() instanceof RefType))
            throw new DiferentTypeException("Not a reftype");
        //RefValue val = (RefValue) symTable.lookup(var_name);
        if(!state.getHeapTable().isDefined(((RefValue) state.getSymTable().lookup(var_name)).getAddress()))
            throw new InvalidIdException("no entry in symtable");


        IValue val = expression.eval(state.getSymTable(), state.getHeapTable());
        RefValue var = (RefValue) ((RefValue) state.getSymTable().lookup(var_name));
        if(!val.getType().equals( var.getLocationType()))
            throw new DiferentTypeException("differetn types");

        state.getHeapTable().update(var.getAddress(), val);

        return null;
    }

    @Override
    public String toString() {
        return "Replace " + var_name + " with " + expression.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }
}
