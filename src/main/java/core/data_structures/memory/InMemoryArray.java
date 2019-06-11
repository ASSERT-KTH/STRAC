package core.data_structures.memory;

import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InMemoryArray<T> implements IArray<T> {

    Object[] items;
    int position;

    public InMemoryArray(int size){


        if(size > 1 << 30){
            throw new RuntimeException("Too large array " + (size));
        }

        this.items = new Object[size];
        this.position = 0;
    }

    @Override
    public T read(long position) {
        return (T)items[(int)position];
    }

    @Override
    public long size() {
        return position;
    }

    @Override
    public void reset() {

    }

    @Override
    public T[] getPlain() {

        return (T[])items;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<T> adaptor) throws IOException {
        this.reset();

        for(T item: this)
            wr.write(adaptor.getValue(item));

        wr.close();
    }

    @Override
    public void add(long position, T value) {

        items[(int)position] = value;
    }

    @Override
    public void add(T value) {

        items[position++]=value;
    }

    @Override
    public void close() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public IArray<T> subArray(long index, long size) {
        return null;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {

        return new Iterator<>();
    }

    class Iterator<T> implements java.util.Iterator<T> {

        int _position = 0;

        @Override
        public boolean hasNext() {
            return _position < position;
        }

        @Override
        public T next() {
            return (T)items[_position++];
        }
    }
}
