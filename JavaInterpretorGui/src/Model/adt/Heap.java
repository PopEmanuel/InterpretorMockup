package Model.adt;

import Exceptions.InvalidIdException;
import Model.value.IValue;
import Model.value.RefValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Heap<T1> implements IHeap<T1>{

    Map<Integer, T1> dictionary;
    Integer free_location = 1;

    public Heap() {
        dictionary = new HashMap<Integer,T1>();
    }
    public Heap(Map<Integer, T1> map){
        dictionary = map;
    }




    @Override
    public void add(T1 value) {
        dictionary.put(free_location, value);
        free_location += 1;
    }

    @Override
    public void remove(int key) {
        dictionary.remove(key);
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("\n");

        for (Map.Entry<Integer, T1> entry : dictionary.entrySet()) {
            Integer key = entry.getKey();
            T1 val = entry.getValue();
            result.append(key.toString()).append(" : ").append(val.toString()).append("\n");
        }


        return result.toString();
    }

    @Override
    public void update(int key, T1 value) {
        dictionary.replace(key, value);
    }

    @Override
    public T1 lookup(int key) {
        return dictionary.get(key);
    }

    @Override
    public int get_free_location() {
        return free_location;
    }

    @Override
    public void setContent(Map<Integer, T1> map) {
        dictionary = map;
    }

    @Override
    public Map<Integer, T1> getContent() { return dictionary; }

    @Override
    public boolean isDefined(int key) {
        return dictionary.containsKey(key);
    }
}
