package model.utils;

import exceptions.MyException;

import java.util.Stack;
import java.util.ListIterator;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> elems;

    public MyStack() {
        this.elems = new Stack<T>();
    }

    @Override
    public void push(T newElem) {
        this.elems.push(newElem);
    }

    @Override
    public T pop() throws MyException {
        if (this.elems.empty()) {
            throw new MyException("Failed to pop element: Full stack.");
        }
        return elems.pop();
    }

    @Override
    public boolean isEmpty() {
        return this.elems.empty();
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        ListIterator<T> stackIterator = this.elems.listIterator(this.elems.size());
        while (stackIterator.hasPrevious()) {
            st.append(stackIterator.previous().toString()).append("\n");
        }
        if (st.length() > 0) {
            return st.deleteCharAt(st.length() - 1).toString();
        }
        else {
            return "";
        }
    }
}
