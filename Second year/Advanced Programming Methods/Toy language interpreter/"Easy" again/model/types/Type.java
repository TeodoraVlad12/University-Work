package model.types;
import model.values.Value;

public interface Type {
    Value getDefault();
    Type deepCopy();
}
