package Model.types;

import Model.value.IValue;
import Model.value.RefValue;

import java.lang.reflect.Type;

public class RefType implements IType
{
    IType inner;

    public RefType(IType inner) {this.inner=inner;}


    public boolean equals(Object object){
        if(!(object instanceof RefType referenceType))
            return false;
        return inner.equals(referenceType.getInner());
    }

    public IType getInner() {return inner;}

    public String toString() { return "Ref(" +inner.toString()+")";}

    public IValue defaultValue() { return new RefValue(0,inner);}

    @Override
    public IType deepCopy() {
        return null;
    }

}