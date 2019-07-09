package core.data_structures.memory;

import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;

public class InMemoryDoubleArray implements IArray<Double> {

    Double[] items;
    int position;

    public InMemoryDoubleArray(String id, int size){


        if(size > 1 << 30){
            throw new RuntimeException("Too large array " + (size));
        }

        this.items = new Double[size];
        this.position = 0;

        this.id = id;
    }

    @Override
    public Double read(long position) {
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
    public Double[] getPlain() {

        return (Double[])items;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<Double> adaptor) throws IOException {
        this.reset();

        for(Double item: this){

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
    public void set(long position, Double value) {

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
    public InMemoryDoubleArray.Iterator iterator() {

        return new InMemoryDoubleArray.Iterator();
    }

    class Iterator implements java.util.Iterator<Double> {

        int _position = 0;

        @Override
        public boolean hasNext() {
            return _position < items.length;
        }

        @Override
        public Double next() {
            return items[_position++];
        }
    }
}
