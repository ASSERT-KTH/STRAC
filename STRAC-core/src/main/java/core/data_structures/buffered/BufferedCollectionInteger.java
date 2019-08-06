package core.data_structures.buffered;

import java.nio.ByteBuffer;

public class BufferedCollectionInteger extends BufferedCollection<Integer> {


    @Override
    public int dataSize() {
        return 4;
    }

    public BufferedCollectionInteger(String fileName, long dataSize, int segmentSize) {
        super(fileName, dataSize, segmentSize);
    }


    @Override
    public void setData(Integer value, ByteBuffer buff) {
        buff.putInt(value);
    }

    @Override
    public Integer readFromFile(ByteBuffer buff) {
        return buff.getInt();
    }
}
