package core.data_structures.memory;

import core.data_structures.IArray;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InMemoryArray implements IArray<Integer> {

    List<Integer> items;

    public InMemoryArray(List<Integer> items){
        this.items = items;
    }

    public InMemoryArray(){
        this.items = new ArrayList<>();
    }


    public InMemoryArray(int size){
        this.items = new ArrayList<>(size);
    }

    public InMemoryArray(Integer... array){
        items = Arrays.asList(array);
    }

    @Override
    public Integer read(int position) {
        return items.get(position);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void write(int position, Integer value) {
        items.set(position, value);
    }

    @Override
    public void add(Integer value) {
        items.add(value);
    }

    @Override
    public void close() {

    }

    @Override
    public IArray<Integer> subArray(int index, int size) {
        return new InMemoryArray(items.subList(index, size));
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return this.items.iterator();
    }
}
