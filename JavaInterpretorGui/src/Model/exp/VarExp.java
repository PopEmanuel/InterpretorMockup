package Model.exp;
import Exceptions.InvalidIdException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class VarExp extends Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String,IValue> symTable, IHeap<IValue> heapTable) throws InvalidIdException {
        return symTable.lookup(id);
    }

    public String toString() {return id;}

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv.lookup(id);
    }

}
