package Model.adt;

public interface IList<T> {
    void add(T v);
    T pop();
    String toString();
    boolean empty();
    void clear();
    public T getFirstElement();
    int size();
    T get(int i);
    IList<T> deepCopy();
    boolean contains(T key);
}
