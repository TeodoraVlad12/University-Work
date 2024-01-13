package model.utils;

import exceptions.MyException;

public interface MyIStack<T>{
    void push(T newElem);
    T pop() throws MyException;
    boolean isEmpty();
}
