package model.utils;
import exceptions.MyException;

import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1,T2> implements MyIDictionary<T1,T2> {

    HashMap<T1, T2> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public boolean isDefined(T1 key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public T2 lookUp(T1 key) throws MyException {
        if (!isDefined(key))
            throw new MyException("This key is not defined:((");
        return this.dictionary.get(key);
    }

    @Override
    public void put(T1 key, T2 value) {
        this.dictionary.put(key, value);
    }

    @Override
    public void update(T1 key, T2 value) throws MyException{
        if (!isDefined(key))
            throw new MyException("This key is not defined:((");
        this.dictionary.put(key, value);
    }

    @Override
    public T2 remove(T1 key) throws MyException {
        if (!isDefined(key))
            throw new MyException("This key is not defined:((");
        return this.dictionary.remove(key);
    }

    @Override
    public Collection<T2> values() {
        return this.dictionary.values();
    }

    @Override
    public Set<T1> keySet(){
        return dictionary.keySet();
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }



    @Override
    public Map<T1, T2> getContent() {
        return this.dictionary;
    }

    @Override
    public void setContent(Map<T1, T2> newContent) {
        this.dictionary.clear();
        this.dictionary.putAll(newContent);
    }

    @Override
    public MyIDictionary<T1, T2> deepCopy() throws MyException {
        MyIDictionary<T1, T2> toReturn = new MyDictionary<>();
        for (T1 key: keySet())
            toReturn.put(key, lookUp(key));
        return toReturn;
    }


}
