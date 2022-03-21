package Model.stmt;

import Exceptions.DiferentTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStmt implements IStmt{
    IStmt statement;
    Exp expression;

    public WhileStmt(Exp expre, IStmt stmt)
    {
        this.statement = stmt;
        this.expression = expre;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IStack<IStmt> stack = state.getStack();
        IValue val = expression.eval(state.getSymTable() , state.getHeapTable());

        if(!(val instanceof BoolValue))
            throw new DiferentTypeException("Not a BoolValue");
        if(((BoolValue) val).getValue())
        {
            stack.push(new WhileStmt(expression, statement));
            stack.push(statement);
        }

        return null;
    }

    public  String toString() {
        return "while(" + expression.toString() + ") do { " + statement.toString() + " }";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type = expression.typecheck(typeEnv);
        if(type.equals(new BoolType()))
            statement.typecheck(typeEnv);
        else
            throw new DiferentTypeException("Expression is not boolean");
        return typeEnv;
    }
}
