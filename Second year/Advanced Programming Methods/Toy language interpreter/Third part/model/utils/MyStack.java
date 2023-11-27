package model.utils;
import exceptions.MyException;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class MyStack<T> implements MyIStack<T> {

    java.util.Stack<T> stack;

    public MyStack(){
        this.stack = new java.util.Stack<>();
    }

    @Override
    public void push(T elem) {
        this.stack.push(elem);
    }

    @Override
    public T pop() throws MyException{
        if (stack.isEmpty()){
            throw new MyException("The stack is empty:((((");
        }
        return this.stack.pop();
    }

    @Override
    public boolean isEmpty(){
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

    @Override
    public List<T> getReversed() {
        List<T> list = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public T peek() {
        return this.stack.peek();
    }




}
