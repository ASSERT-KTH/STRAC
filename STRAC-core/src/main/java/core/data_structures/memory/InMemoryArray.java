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

public class InMemoryArray implements IArray<Integer> {

    Integer[] items;
    int position;

    public InMemoryArray(String id, int size){


        if(size > 1 << 30){
            throw new RuntimeException("Too large array " + (size));
        }

        this.items = new Integer[size];
        this.position = 0;

        this.id = id;
    }

    @Override
    public Integer read(long position) {
        return items[(int)position];
    }

    @Override
    public long size() {
        return items.length;
    }

    @Override
    public void reset() {

    }

    @Override
    public Integer[] getPlain() {

        return (Integer[])items;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<Integer> adaptor) throws IOException {
        this.reset();
        try {

            for (int item : items) {

                    String value = adaptor.getValue(item);

                    if (value != null)
                        wr.write(value);

            }

            wr.close();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    String id;

    @Override
    public String getUniqueId() {
        return id;
    }

    @Override
    public void set(long position, Integer value) {

        items[(int)position] = value;
    }


    @Override
    public void close() {

    }

    @Override
    public void dispose() {

    }

    @NotNull
    @Override
    public Iterator iterator() {

        return new Iterator();
    }

    class Iterator implements java.util.Iterator<Integer> {

        int _position = 0;

        @Override
        public boolean hasNext() {
            return _position < items.length;
        }

        @Override
        public Integer next() {
            return items[_position++];
        }
    }
}
