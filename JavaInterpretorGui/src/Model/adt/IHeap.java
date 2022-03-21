package Model.adt;

import java.util.Map;

public interface IHeap<T1> {
    void add(T1 value);

    void remove(int key);

    void update(int key, T1 value);

    T1 lookup(int key);

    int get_free_location();

    void setContent(Map<Integer, T1> map);

    Map<Integer, T1> getContent();

    boolean isDefined(int key);
}
