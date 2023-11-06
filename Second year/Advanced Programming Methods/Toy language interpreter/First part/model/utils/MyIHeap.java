package model.utils;

import exceptions.MyException;
import java.util.HashMap;
import java.util.Set;
import model.value.Value;

public interface MyIHeap {

    int add(Value value);
    void remove(Integer key) throws MyException;
    void update(Integer position, Value value) throws MyException;
    Value get(Integer position) throws MyException;
    void setContent(HashMap<Integer, Value> newMap);
    int getFreeValue();
    HashMap<Integer, Value> getContent();
    Set<Integer> keySet();
    boolean containsKey(Integer position);

}
