package Model.stmt;

import Exceptions.ArtihmeticException;
import Exceptions.DiferentTypeException;
import Exceptions.InvalidIdException;
import Exceptions.LogicOperationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements IStmt{
    Exp expression;
    IStmt thenStatement;
    IStmt elseStatement;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        expression = e;
        thenStatement = t;
        elseStatement = el;
    }

    public String toString(){
        return "if(" + expression.toString() + ") do "
                + thenStatement.toString() + " else do "
                + elseStatement.toString();
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new DiferentTypeException("The condition of IF has not the type bool");
}

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IStack<IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();

        IValue condition = expression.eval(symTbl, state.getHeapTable());

        if(!condition.getType().equals(new BoolType()))
            throw new DiferentTypeException("Types don't match");
        else
        if(condition.toString().equals("true"))
            stack.push(thenStatement);
        else
            stack.push(elseStatement);


        return null;
    }
}
