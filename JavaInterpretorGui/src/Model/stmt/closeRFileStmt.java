package Model.stmt;

import Exceptions.DiferentTypeException;
import Exceptions.InvalidFileException;
import Exceptions.InvalidIdException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class closeRFileStmt implements IStmt{
    Exp expression;

    public closeRFileStmt(Exp exp)
    {
        expression = exp;
    }

    public String toString(){return "close(" + expression.toString() + ")";}

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type = expression.typecheck(typeEnv);
        if(type.equals(new StringType()))
            return typeEnv;
        else
            throw new DiferentTypeException("File name is not string");
    }

    @Override
    public PrgState execute(PrgState state) throws Exception{

        IValue val = expression.eval(state.getSymTable(), state.getHeapTable());
        if(!val.getType().equals(new StringType()))
            throw new DiferentTypeException("The type can only be string");


        boolean ok = false;
        BufferedReader reader = null;

        StringValue k = new StringValue();
        for(StringValue key : state.getFileTable().getKeys())
        {
            if (key.equals(val)) {
                ok = true;
                k = key;
                reader = state.getFileTable().lookup(key);
                break;
            }

        }

        if(!ok)
            throw new InvalidIdException("File is not opened");

        reader.close();

        state.getFileTable().remove(k);

        return null;
    }
}
