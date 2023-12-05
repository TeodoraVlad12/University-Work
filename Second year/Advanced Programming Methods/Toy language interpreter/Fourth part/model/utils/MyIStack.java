package model.utils;

import exceptions.MyException;
import java.util.List;

public interface MyIStack<T> {
    T pop() throws MyException;
    void push(T elem);
    boolean isEmpty();
    T peek();

    List<T> getReversed();
}
