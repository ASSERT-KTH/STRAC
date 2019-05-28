package ngram.hash_keys;

import core.data_structures.IArray;
import core.data_structures.memory.InMemoryArray;
import ngram.models.HashKey;

import java.util.List;

import static core.utils.HashingHelper.hashList1;

public class ListHashKey extends HashKey {

    IArray<Integer> items;

    public ListHashKey(IArray<Integer> items){
        this.items = items;
    }

    public ListHashKey(List<Integer> items){
        this.items = new InMemoryArray(items);
    }

    @Override
    public int hashCode() {
        //LogProvider.info(this.items.stream().map(Object::hashCode).map(String::valueOf).collect(Collectors.toList()).toArray(new String[0]));
        return hashList1(this.items);
    }

    @Override
    public boolean equals(Object obj) {

        ListHashKey cast = (ListHashKey) obj;

        if(cast.items.size() != items.size())
            return false;

        return this.hashCode() == cast.hashCode();
    }
}
