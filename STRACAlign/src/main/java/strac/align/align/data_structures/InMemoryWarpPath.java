package strac.align.align.data_structures;

import strac.align.align.Cell;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;

public class InMemoryWarpPath implements IArray<Cell> {

    Cell[] items;
    int position;

    public InMemoryWarpPath(String id, int size){


        if(size > 1 << 30){
            throw new RuntimeException("Too large array " + (size));
        }

        this.items = new Cell[size];
        this.position = 0;

        this.id = id;
    }

    @Override
    public Cell read(long position) {
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
    public Cell[] getPlain() {

        return items;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<Cell> adaptor) throws IOException {
        this.reset();

        for(Cell item: this){

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
    public void set(long position, Cell value) {

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
    public InMemoryWarpPath.Iterator iterator() {

        return new InMemoryWarpPath.Iterator();
    }

    class Iterator implements java.util.Iterator<Cell> {

        int _position = 0;

        @Override
        public boolean hasNext() {
            return _position < items.length;
        }

        @Override
        public Cell next() {
            return items[_position++];
        }
    }
}
