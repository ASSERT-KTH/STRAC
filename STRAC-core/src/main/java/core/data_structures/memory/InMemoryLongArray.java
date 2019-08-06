package core.data_structures.memory;

import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;

public class InMemoryLongArray implements IArray<Long> {

    Long[] items;
    int position;

    public InMemoryLongArray(String id, int size){


        if(size > 1 << 30){
            throw new RuntimeException("Too large array " + (size));
        }

        this.items = new Long[size];
        this.position = 0;

        this.id = id;
    }

    @Override
    public Long read(long position) {
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
    public Long[] getPlain() {

        return (Long[])items;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<Long> adaptor) throws IOException {
        this.reset();

        for(Long item: this){

            String value = adaptor.getValue(item);

            if(value != null)
                wr.write(value);
        }

        wr.close();
    }

    String id;

    @Override
    public String getUniqueId() {
        return id;
    }

    @Override
    public void set(long position, Long value) {

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
    public InMemoryLongArray.Iterator iterator() {

        return new InMemoryLongArray.Iterator();
    }

    class Iterator implements java.util.Iterator<Long> {

        int _position = 0;

        @Override
        public boolean hasNext() {
            return _position < items.length;
        }

        @Override
        public Long next() {
            return items[_position++];
        }
    }
}
