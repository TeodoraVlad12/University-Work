package model.types;

import model.values.BoolValue;
import model.values.Value;

public class BoolType implements Type {

    public boolean equals(Object another) {

        return another instanceof BoolType;
    }

    @Override
    public Value getDefaultValue() {
        return new BoolValue(false);
    }

    public String toString() {

        return "bool";
    }

    @Override
    public Type deepcopy() {
        return new BoolType();
    }
}

