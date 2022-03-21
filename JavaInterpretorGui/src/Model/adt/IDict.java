package Model.adt;

import Exceptions.InvalidIdException;

import java.util.Collection;
import java.util.Map;

public interface IDict<T1,T2>{

    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    T2 lookup(T1 id) throws InvalidIdException;
    boolean isDefined(T1 id);
    String toString();
    Collection<T2> getValues();
    IDict<T1, T2> deepCopy();
    Collection<T1> getKeys();
    T2 remove(T1 key);
    Map<T1, T2> getContent();
}
