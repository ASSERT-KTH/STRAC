package core.data_structures.buffered;

import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

public class BufferedCollectionLong  extends BufferedCollection<Long> {


    public BufferedCollectionLong(String fileName, long dataSize, int segmentSize) {
        super(fileName, dataSize, segmentSize);
    }

    @Override
    int dataSize() {
        return 8;
    }

    @Override
    void setData(Long value, ByteBuffer buff) {
        buff.putLong(value);
    }

    @Override
    public Long readFromFile(ByteBuffer buff) {
        return buff.getLong();
    }
}
