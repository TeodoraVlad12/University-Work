package model.values;

import model.types.Type;
import model.types.StringType;

public class StringValue implements Value {

    String value;

    public StringValue(String value){
        this.value = value;
    }

    public String getVal(){
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Value deepcopy() {
        return new StringValue(value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }
}
