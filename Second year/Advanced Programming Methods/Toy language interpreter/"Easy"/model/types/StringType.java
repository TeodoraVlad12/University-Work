package model.types;

import model.values.Value;
import model.values.StringValue;

public class StringType implements Type {

    @Override
    public boolean equals(Object obj) {
        return  obj instanceof StringType;
    }

    @Override
    public Type deepcopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }
}
