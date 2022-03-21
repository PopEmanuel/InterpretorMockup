package Model.adt;
import Exceptions.InvalidIdException;

import java.util.*;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<T1,T2>();
    }
    public Dict(Map<T1, T2> map){
        dictionary = map;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("\n");

        for (Map.Entry<T1, T2> entry : dictionary.entrySet()) {
            T1 key = entry.getKey();
            T2 val = entry.getValue();
            result.append(key.toString()).append(" : ").append(val.toString()).append("\n");
        }


        return result.toString();
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dictionary.replace(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) throws InvalidIdException {
        try{

            return dictionary.get(id);
        }catch(Exception e)
        {
//            throw new InvalidIdException("Error id not in table");
            return null;
        }
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public Collection<T2> getValues() {
        return dictionary.values();
    }

    @Override
    public IDict<T1, T2> deepCopy() {
        Map<T1, T2> dictionaryCopy = new HashMap<T1,T2>();
        for(T1 key : dictionary.keySet())
        {
            dictionaryCopy.put(key, dictionary.get(key));
        }
        return new Dict<T1, T2>(dictionaryCopy);
    }

    @Override
    public Collection<T1> getKeys() {
        return this.dictionary.keySet();
    }

    @Override
    public T2 remove(T1 key) {
        return dictionary.remove(key);
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

}
