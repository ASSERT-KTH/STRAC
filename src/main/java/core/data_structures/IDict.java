package core.data_structures;

public interface IDict<TKey, TValue> {

    TValue get(TKey key);

    void set(TKey key, TValue value);

    boolean contains(TKey key);

    int size();

    ISet<TKey> keySet();
}
