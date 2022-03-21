package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int addr, IType lType) {
        address = addr;
        locationType = lType;
    }

    public int getAddress() {return address;}

    public void setAdress(int adress) { address = adress;}

    public IType getType() { return new RefType(locationType);}

    public IType getLocationType() {return locationType;}

    public String toString()
    {
        return "Ref(" + address + ", " + locationType.toString() + ")";
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }
}