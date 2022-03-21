package Model.exp;

import Exceptions.ArtihmeticException;
import Exceptions.DiferentTypeException;
import Exceptions.InvalidIdException;
import Exceptions.LogicOperationException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;

public class LogicExp extends Exp{
    Exp e1;
    Exp e2;
    char op; // & or |

    public LogicExp(char opp, Exp exp1, Exp exp2)
    {
        op = opp;
        e1 = exp1;
        e2 = exp2;
    }

    @Override
    public IValue eval(IDict<String,IValue> symTable, IHeap<IValue> heapTable) throws Exception {
        IValue nr1 = e1.eval(symTable, heapTable);
        if(nr1.getType().equals(new BoolType()))
        {
            IValue nr2 = e2.eval(symTable, heapTable);
            if(nr2.getType().equals(new BoolType()))
            {
                String string1 = ((BoolType) nr1).toString();
                String string2 = ((BoolType) nr2).toString();
                boolean b1,b2;
                b1 = string1.equals("true");

                b2 = string2.equals("true");
                if(op == '&')
                    return new BoolValue(b1 && b2);
                else
                    if(op == '|')
                        return new BoolValue(b1 || b2);
                    else
                        throw new LogicOperationException("Invalid operator");
            }
        }
        else
            throw new LogicOperationException("Different types in logical expression");
        return null;

    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);

        if(type1.equals(new BoolType())){
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new DiferentTypeException("Second operand not boolean");
        }
        else
            throw new DiferentTypeException("First operand not boolean");
    }
}
