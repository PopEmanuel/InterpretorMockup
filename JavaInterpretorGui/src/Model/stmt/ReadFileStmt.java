package Model.stmt;

import Exceptions.DiferentTypeException;
import Exceptions.InvalidFileException;
import Exceptions.InvalidIdException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFileStmt implements IStmt{
    Exp expression;
    String varName;

    public ReadFileStmt(Exp exp, String var_name)
    {
        expression = exp;
        varName = var_name;
    }

    public String toString(){return varName + "= read(" + expression.toString() + ")";}

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

        if(!state.getSymTable().lookup(varName).getType().equals(new IntType()))
            throw new DiferentTypeException("Variable must pe int");
        IValue value = expression.eval(state.getSymTable(), state.getHeapTable());

        if(!value.getType().equals(new StringType()))
            throw new DiferentTypeException("Value must pe String");


        boolean ok = false;
        for(StringValue key : state.getFileTable().getKeys())
        {
            if (key.equals(value)) {
                ok = true;
                break;
            }

        }

        if(!ok)
            throw new InvalidFileException("No entry with this name");


        BufferedReader rd = state.getFileTable().lookup((StringValue)value);
        for(StringValue key : state.getFileTable().getKeys())
        {
            if (key.equals(value)) {
                rd = state.getFileTable().lookup(key);
            }

        }

        String line = rd.readLine();

        int new_val = 0;
        try{
            new_val = Integer.parseInt(line);
        }catch(NumberFormatException e)
        {
            throw new DiferentTypeException("Read string is not a valid int");
        }

        state.getSymTable().update(varName, new IntValue(new_val));

        return null;
    }
}
