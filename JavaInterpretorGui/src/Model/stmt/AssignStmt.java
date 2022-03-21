package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;


public class AssignStmt implements IStmt{

    String id;
    Exp expression;

    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.expression = exp;
    }

    @Override
    public String toString(){
        return this.id + "=" + this.expression.toString() + ";";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(id);
        IType typeexo = expression.typecheck(typeEnv);
        if(typevar.equals(typeexo))
            return typeEnv;
        else
            throw new DiferentTypeException("Assignment : different types");
    }

    public PrgState execute(PrgState state) throws Exception {

        IStack<IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();

        if (symTbl.isDefined(id)) {
            IValue val = expression.eval(symTbl, state.getHeapTable());

            IType typId = symTbl.lookup(id).getType();

            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new AssignOperationException("Different type assignment");
        }
        else
            throw new AssignOperationException("Variable is not defined");
        return null;
    }

}

