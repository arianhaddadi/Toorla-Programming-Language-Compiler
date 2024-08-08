package toorla.utils.stack;

import java.util.ArrayList;

public class Stack<E> {
    private final ArrayList<E> elements;
    private int top;

    public Stack() {
        top = -1;
        elements = new ArrayList<>();
    }

    public void push(E pushValue) {
        elements.add(pushValue);
        ++top;
    }

    public E pop() {
        if (top == -1) {
            return null;
        }
        --top;
        E e = elements.get(top + 1);
        elements.remove(top + 1);
        return e;
    }
}
