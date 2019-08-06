package core.data_structures.buffered;

import java.nio.ByteBuffer;

public class BufferedCollectionLong  extends BufferedCollection<Long> {


    public BufferedCollectionLong(String fileName, long dataSize, int segmentSize) {
        super(fileName, dataSize, segmentSize);
    }

    @Override
    public int dataSize() {
        return 8;
    }

    @Override
    public void setData(Long value, ByteBuffer buff) {
        buff.putLong(value);
    }

    @Override
    public Long readFromFile(ByteBuffer buff) {
        return buff.getLong();
    }
}
