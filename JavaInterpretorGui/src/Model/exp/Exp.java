package Model.exp;
import Exceptions.ArtihmeticException;
import Exceptions.InvalidIdException;
import Exceptions.LogicOperationException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public abstract class Exp {

    public abstract IValue eval(IDict<String,IValue> symTable, IHeap<IValue> heapTable) throws ArtihmeticException, LogicOperationException, InvalidIdException, Exception;
    public abstract String toString();
    public abstract IType typecheck(IDict<String,IType> typeEnv) throws Exception;

    //protected abstract IValue eval(IDict<String, IValue> symTable);
}
