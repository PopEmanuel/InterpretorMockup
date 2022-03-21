package Model.stmt;

import Exceptions.DiferentTypeException;
import Exceptions.InvalidIdException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;



public class newStmt implements IStmt{
    String var_name;
    Exp expression;

    public newStmt(String var, Exp expr)
    {
        var_name = var;
        expression = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if(!state.getSymTable().isDefined(var_name))
           throw new InvalidIdException("this Var_name doesn't exist");
        if(!(state.getSymTable().lookup(var_name).getType() instanceof RefType))
            throw new DiferentTypeException("Not a reftype");

        IValue val = expression.eval(state.getSymTable(), state.getHeapTable());

        RefValue tp = (RefValue) (state.getSymTable().lookup(var_name));
        if(!val.getType().equals(tp.getLocationType()))
            throw new DiferentTypeException("Different types");

        IValue value1 = val.deepCopy();
        int free = state.getHeapTable().get_free_location();
        state.getHeapTable().add(value1);

        ((RefValue) state.getSymTable().lookup(var_name)).setAdress(free);

        return null;

    }

    public String toString()
    {
        return "new(" + var_name + ", " + expression.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(var_name);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new DiferentTypeException("NEW stmt: right hand side and left hand side have different types ");
    }
}
