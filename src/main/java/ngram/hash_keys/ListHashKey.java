package ngram.hash_keys;

import core.LogProvider;
import ngram.models.HashKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static core.utils.HashingHelper.hashList1;

public class ListHashKey extends HashKey {

    List items;

    public ListHashKey(List items){
        this.items = items;
    }

    public ListHashKey(int... items){
        this.items = Arrays.asList(items);
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
