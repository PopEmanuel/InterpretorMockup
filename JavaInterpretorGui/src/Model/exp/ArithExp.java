package Model.exp;
import Exceptions.ArtihmeticException;
import Exceptions.InvalidIdException;
import Exceptions.LogicOperationException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ArithExp extends Exp{
    char op;
    Exp e1, e2;

    public ArithExp(char operator, Exp exp1, Exp exp2)
    {
        op = operator;
        e1 = exp1;
        e2 = exp2;
    }

    public char getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws Exception {
        IValue v1, v2;
        v1 = e1.eval(symTable, heapTable);
        if(v1.getType().equals(new IntType()))
        {
            v2 = e2.eval(symTable, heapTable);
            if(v2.getType().equals(new IntType()))
            {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                if(op == '+')
                    return new IntValue(n1+n2);
                else if(op == '-')
                    return new IntValue(n1-n2);
                else if(op == '/') {
                    if (n2 != 0)
                        return new IntValue(n1 / n2);
                    else {

                        throw new ArtihmeticException("Can't divide by zero");
                    }
                }
                else if(op == '*')
                    return new IntValue(n1*n2);
            }
        }else
            throw new ArtihmeticException("arithmetic exception");
        return null;
    }

    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);

        if(type1.equals(new IntType())){
            if(type2.equals(new IntType()))
                return new IntType();
            else
                throw new ArtihmeticException("Second operand not integer");

        }
        else
            throw new ArithmeticException("First operand not integer");
    }
}
