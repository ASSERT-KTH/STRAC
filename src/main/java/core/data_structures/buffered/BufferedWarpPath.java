package core.data_structures.buffered;

import align.Cell;
import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import core.utils.HashingHelper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

public class BufferedWarpPath extends  BufferedCollection<Cell> {

    public BufferedWarpPath(String fileName, long dataSize, int segmentSize) {
        super(fileName, dataSize, segmentSize);
    }

    @Override
    int dataSize() {
        return Cell.CELL_ADAPTOR.size();
    }


    @Override
    void setData(Cell value, ByteBuffer buff) {

        byte[] chunk = Cell.CELL_ADAPTOR.toBytes(value);

        buff.put(chunk, 0, chunk.length);
    }

    @Override
    public void set(long position, Cell value) {



    }

    @Override
    public Cell readFromFile(ByteBuffer buff) {
        byte[] chunk = new byte[Cell.CELL_ADAPTOR.size()];

        buff.get(chunk, 0, chunk.length);

        return Cell.CELL_ADAPTOR.fromBytes(chunk);
    }

}
