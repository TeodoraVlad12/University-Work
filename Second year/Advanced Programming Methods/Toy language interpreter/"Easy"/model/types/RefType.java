package model.types;

import model.values.Value;
import model.values.RefValue;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner){
        this.inner = inner;
    }

    public Type getInner(){
        return inner;
    }

    public boolean equals(Object obj) {
        if(obj instanceof RefType)
            return inner.equals(((RefType) obj).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString()+ ")";
    }

    @Override
    public Type deepcopy() {
        return new RefType(inner.deepcopy());
    }

    @Override
    public Value getDefaultValue() {
        return new RefValue(0, inner);
    }
}
