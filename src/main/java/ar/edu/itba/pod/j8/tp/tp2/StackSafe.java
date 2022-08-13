package ar.edu.itba.pod.j8.tp.tp2;

public class StackSafe implements Stack {
    private static final int MAX_SIZE = 200000;
    private int top = 0;
    private final int[] values = new int[MAX_SIZE];

    @Override
    public synchronized void push(final int n) {
        if (top == MAX_SIZE) {
            throw new IllegalStateException("stack full");
        }
        values[top++] = n;
    }

    @Override
    public synchronized int pop() {
        if (top == 0) {
            throw new IllegalStateException("stack empty");
        }
        return values[--top];
    }

    @Override
    public int getTop() {
        return top;
    }
}