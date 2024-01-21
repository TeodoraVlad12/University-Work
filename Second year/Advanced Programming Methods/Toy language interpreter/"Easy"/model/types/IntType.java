package model.types;

import model.values.Value;
import model.values.IntValue;

public class IntType implements Type {
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public Value getDefaultValue() {
        return new IntValue(0);
    }

    public String toString() {
        return "int";
    }

    @Override
    public Type deepcopy() {
        return new IntType();
    }
}