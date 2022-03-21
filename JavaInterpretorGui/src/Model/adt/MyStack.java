package Model.adt;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    Stack<T> stack;

    public MyStack() {stack = new Stack<T>();}

    public MyStack(Stack<T> stk){
        stack = stk;
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("");
        for(T item : stack)
            result.append(item.toString()).append("\n");

        return result.toString();
    }

    @Override
    public IStack<T> deepCopy() {
        Stack<T> listCopy = new Stack<T>();
        listCopy.addAll(stack);
        return new MyStack<T>(listCopy);
    }
}
