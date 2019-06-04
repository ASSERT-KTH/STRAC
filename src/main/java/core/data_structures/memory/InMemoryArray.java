package core.data_structures.memory;

import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InMemoryArray<T> implements IArray<T> {

    List<T> items;

    public InMemoryArray(List<T> items){
        this.items = items;
    }

    public InMemoryArray(){
        this.items = new ArrayList<>();
    }


    public InMemoryArray(int size){
        this.items = new ArrayList<>(size);
    }

    public InMemoryArray(T... array){
        items = Arrays.asList(array);
    }

    @Override
    public T read(int position) {
        return items.get(position);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void reset() {

    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<T> adaptor) throws IOException {
        this.reset();

        for(T item: this)
            wr.write(adaptor.getValue(item));

        wr.close();
    }

    @Override
    public void add(int position, T value) {
        items.set(position, value);
    }

    @Override
    public void add(T value) {
        items.add(value);
    }

    @Override
    public void close() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public IArray<T> subArray(int index, int size) {
        return new InMemoryArray<T>(items.subList(index, size));
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.items.iterator();
    }
}
