package model.utils;

import java.util.ArrayList;

import exceptions.MyException;

public class MyList<T> implements MyIList<T> {
    java.util.List<T> list;

    public MyList(){
        this.list = new ArrayList<T>();   //dynamic array, you don't need to mention the size

    }

    @Override
    public void add(T elem){
        this.list.add(elem);
    }

    @Override
    public T pop() throws MyException{
        if (list.isEmpty()){
            throw new MyException("The list is empty:(((");
        }
        return this.list.remove(0);

    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public java.util.List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
