package model.utils;

import exceptions.MyException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public interface MyIDictionary<T1,T2> {
    void put(T1 key, T2 value);
    void update(T1 key, T2 value) throws MyException;
    T2 remove(T1 key) throws MyException;

    Collection<T2> values();
    Set<T1> keySet();
    T2 lookUp(T1 key) throws MyException;
    public boolean isDefined(T1 key);

    Map<T1, T2> getContent();
    void setContent(Map<T1, T2> newContent);
}
