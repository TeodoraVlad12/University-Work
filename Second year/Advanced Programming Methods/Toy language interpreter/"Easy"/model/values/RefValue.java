package model.values;

import model.types.Type;
import model.types.RefType;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return address;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepcopy() {
        return new RefValue(address, locationType.deepcopy());
    }

    @Override
    public String toString() {
        return address + ", " + locationType;
    }
}
