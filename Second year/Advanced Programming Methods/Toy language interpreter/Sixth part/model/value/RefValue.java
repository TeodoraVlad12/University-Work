package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value{
    private  int address;
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public Type getType(){
        return new RefType(locationType);
    }

    public int getAddress(){
        return this.address;
    }

    public Type getLocationType(){
        return this.locationType;
    }

    @Override
    public Value deepCopy(){
        return new RefValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", address, locationType);
    }
}
