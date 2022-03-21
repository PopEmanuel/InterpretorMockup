package Model.adt;

import java.util.*;

public class MyList<T> implements IList<T> {
    Stack<T> list;

    public MyList() {list = new Stack<T>();}
    public MyList(Stack<T> lst)
    {
        list = lst;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("\n");
        for(T item : list)
        {
            result.append(item.toString()).append("\n");
        }

        return result.toString();
    }

    @Override
    public void add(T v) { list.add(v);}

    @Override
    public T pop() {return list.pop();}

    public T getFirstElement() {return this.list.peek();}

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int i) {
        return list.get(i);
    }

    @Override
    public IList<T> deepCopy() {

        Stack<T> listCopy = new Stack<T>();
        listCopy.addAll(list);
        return new MyList<T>(listCopy);
    }

    @Override
    public boolean contains(T key) {
        return list.contains(key);
    }

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

}
