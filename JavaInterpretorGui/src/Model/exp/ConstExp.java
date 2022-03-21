package Model.exp;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ConstExp extends Exp{
    int number;

    public ConstExp(int nr) {number = nr;}

    public IValue eval(IDict<String,IValue> symTable, IHeap<IValue> heapTable) {
        return new IntValue(number);
    }

    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        return new IntType();
    }
}
