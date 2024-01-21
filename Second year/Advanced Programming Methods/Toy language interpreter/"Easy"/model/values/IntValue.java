package model.values;

import model.types.Type;
import model.types.IntType;

public class IntValue implements Value {
    int val;

    public IntValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    public String toString() {
        return String.valueOf(val);
    }

    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepcopy() {
        return new IntValue(val);
    }
}