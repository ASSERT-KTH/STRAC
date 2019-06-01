package core.data_structures.memory;

import core.ServiceRegister;
import core.data_structures.IDict;
import core.data_structures.ISet;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDict<TKey, TValue> implements IDict<TKey, TValue> {

    Map<TKey, TValue> map;
    ISet<TKey> keySet;

    public InMemoryDict(){
        this.map = new HashMap<>();
        this.keySet = ServiceRegister.getProvider().allocateNewSet();
    }


    @Override
    public TValue get(TKey tKey) {
        return this.map.get(tKey);
    }

    @Override
    public void set(TKey tKey, TValue tValue) {
        this.map.put(tKey, tValue);
        this.keySet.add(tKey);
    }

    @Override
    public boolean contains(TKey tKey) {
        return this.keySet.contains(tKey);
    }

    @Override
    public int size() {
        return this.keySet.size();
    }

    @Override
    public ISet<TKey> keySet() {
        return keySet;
    }
}
