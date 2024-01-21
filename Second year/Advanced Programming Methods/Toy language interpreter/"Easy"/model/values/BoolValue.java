package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    public String toString() {
        return String.valueOf(val);
    }

    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepcopy() {
        return new BoolValue(val);
    }
}
