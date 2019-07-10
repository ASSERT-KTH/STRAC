package core.data_structures.buffered;

import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import core.utils.HashingHelper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import static core.utils.HashingHelper.getRandomName;

public class BufferedCollectionInteger extends BufferedCollection<Integer> {


    public BufferedCollectionInteger(String fileName, long dataSize, int segmentSize) {
        super(fileName, dataSize, segmentSize);
    }


    @Override
    void setData(Integer value, ByteBuffer buff) {
        buff.putInt(value);
    }

    @Override
    public Integer readFromFile(ByteBuffer buff) {
        return buff.getInt();
    }
}
