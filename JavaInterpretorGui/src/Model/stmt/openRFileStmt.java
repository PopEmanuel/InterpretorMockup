package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class openRFileStmt implements IStmt{
    Exp expression;

    public openRFileStmt(Exp exp)
    {
        expression = exp;
    }

    public String toString(){return "open(" + expression.toString() + ")";}

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
        for(StringValue key : state.getFileTable().getKeys())
        {
            if(key.equals(val))
                throw new InvalidFileException("File is already opened");

        }

        BufferedReader reader = new BufferedReader(new FileReader(state.getSymTable().lookup(val.toString()).toString()));
        state.getFileTable().add((StringValue)val, reader);

        return null;
    }
}
