package core;

import java.util.Stack;

/*
* LIFO Queue that holds maxSize elements
*/
public class CircularLIFOStack<T> extends Stack<T>{

    private int maxSize;

    public CircularLIFOStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public T push(T item) {
        T ret = super.push(item);
        while (this.size() > this.maxSize) {
            this.removeElementAt(this.size() - 1);
        }
        return ret;
    }
}
