package model.ADTs;

import exceptions.AdtException;
import exceptions.MyException;

import java.util.List;
import java.util.function.Consumer;

public interface MyListInterface<T> extends Iterable<T>{
    void add(T val);
    T remove(int poz) throws MyException;
    boolean isEmpty();
    int size();
    T get(int poz) throws MyException;

    @Override
    void forEach(Consumer<? super T> action);

    List<T> getList();

}
