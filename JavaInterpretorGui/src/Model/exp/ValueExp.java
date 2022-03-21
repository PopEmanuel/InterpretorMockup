package Model.exp;

import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class ValueExp extends Exp{
    IValue value;

    public ValueExp(IValue val) {value = val;}

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) {
        return value;
    }

    @Override
    public String toString() {
        return "(Value " + value.toString() + ")";
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        return value.getType();
    }
}
