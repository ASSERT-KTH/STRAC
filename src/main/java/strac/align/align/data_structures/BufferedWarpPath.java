package strac.align.align.data_structures;

import strac.align.align.Cell;
import strac.core.data_structures.buffered.BufferedCollection;

import java.nio.ByteBuffer;

public class BufferedWarpPath extends BufferedCollection<Cell> {

    public BufferedWarpPath(String fileName, long dataSize, int segmentSize) {
        super(fileName, dataSize, segmentSize);
    }

    @Override
    public int dataSize() {
        return Cell.CELL_ADAPTOR.size();
    }


    @Override
    public void setData(Cell value, ByteBuffer buff) {

        byte[] chunk = Cell.CELL_ADAPTOR.toBytes(value);

        buff.put(chunk, 0, chunk.length);
    }


    @Override
    public Cell readFromFile(ByteBuffer buff) {
        byte[] chunk = new byte[Cell.CELL_ADAPTOR.size()];

        buff.get(chunk, 0, chunk.length);

        return Cell.CELL_ADAPTOR.fromBytes(chunk);
    }

}
