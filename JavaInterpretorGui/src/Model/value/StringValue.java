package Model.value;

import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;

import java.util.Objects;

public class StringValue implements IValue{
    String value;

    public StringValue()
    {
        this.value = "";
    }

    public StringValue(String str)
    {
        this.value = str;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        StringValue o_value = (StringValue) o;
        return Objects.equals(o_value.value, this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    public String getValue(){
            return this.value;
        }
}
