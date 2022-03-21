package Model.exp;

import Exceptions.*;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class RelationalExp extends Exp{
    Exp e1;
    Exp e2;
    String op;

    public RelationalExp(String opp, Exp e11, Exp e22)
    {
        e1 = e11;
        e2 = e22;
        op = opp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws ArtihmeticException, LogicOperationException, InvalidIdException, Exception {

        IValue v1 = e1.eval(symTable, heapTable);
        IValue v2 = e2.eval(symTable, heapTable);

        if(!(v1 instanceof IntValue s1))
            throw new DiferentTypeException("Not an IntValue");
        if(!(v2 instanceof IntValue s2))
            throw new DiferentTypeException("Not an IntValue");

        if(Objects.equals(op, "<"))
        {
            return new BoolValue(s1.getValue() < s2.getValue());
        }

        if(Objects.equals(op, "<="))
        {
            return new BoolValue(s1.getValue() <= s2.getValue());
        }

        if(Objects.equals(op, ">="))
        {
            return new BoolValue(s1.getValue() >= s2.getValue());
        }

        if(Objects.equals(op, ">"))
        {
            return new BoolValue(s1.getValue() > s2.getValue());
        }

        if(Objects.equals(op, "=="))
        {
            return new BoolValue(s1.getValue() == s2.getValue());
        }

        if(Objects.equals(op, "!="))
        {
            return new BoolValue(s1.getValue() <= s2.getValue());
        }


        throw new RelationalExpException("inexistent operator");

    }

    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);

        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
                return new BoolType();
            else
                throw new DiferentTypeException("Second operand not integer");

        }
        else
            throw new DiferentTypeException("First operand not integer");
    }
}
