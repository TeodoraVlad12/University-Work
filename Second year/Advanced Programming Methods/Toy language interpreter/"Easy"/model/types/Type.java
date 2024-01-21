package model.types;

import model.values.Value;

public interface Type {
    Value getDefaultValue();

    Type deepcopy();
}
