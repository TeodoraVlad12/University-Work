package model.utils;

import exceptions.MyException;

import java.util.ArrayList;

public interface MyIList<T> {
    void add(T newElem);
    ArrayList<T> getElems();
    T getElemAtIndex(int index) throws MyException;
    int size();
}
