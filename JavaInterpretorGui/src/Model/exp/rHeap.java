package Model.exp;

import Exceptions.ArtihmeticException;
import Exceptions.DiferentTypeException;
import Exceptions.InvalidIdException;
import Exceptions.LogicOperationException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;



public class rHeap extends Exp {
    Exp expression;

    public rHeap(Exp expr)
    {
        expression = expr;

    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws Exception {

        if(!(expression.eval(symTable, heapTable) instanceof RefValue))
            throw new DiferentTypeException("Not instanceof refvalue");

        RefValue value = (RefValue) expression.eval(symTable, heapTable);

        int key = value.getAddress();
        if(!heapTable.isDefined(key))
            throw new InvalidIdException("No entry with this key in heapTable");

        return heapTable.lookup(key);
    }

    @Override
    public String toString() {
        return expression.toString();
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type = expression.typecheck(typeEnv);
        if(type instanceof RefType reft)
        {
            return reft.getInner();
        }else
            throw new DiferentTypeException("Argument is not reftype");
    }
}
