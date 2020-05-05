package strac.core.data_structures.buffered;

import org.apache.log4j.Level;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Iterator;

public abstract class BufferedCollection<T> implements IArray<T> {

    RandomAccessFile aFile;

    MappedByteBuffer[] _buffers;

    String filename;
    FileChannel inChannel;

    long _size;

    long segment_size;

    public abstract int dataSize();

    public BufferedCollection(String fileName, long dataSize, long segmentSize) {
        aFile = null;
        this.filename = fileName;

        _size = dataSize*dataSize();

        this.segment_size = segmentSize;

        try {
            aFile = new RandomAccessFile
                    (fileName, "rw");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

        inChannel = aFile.getChannel();

        int buffSize = (int)(_size/segmentSize);

        if(_size%segmentSize != 0)
            buffSize++;

        _buffers = new MappedByteBuffer[buffSize];

        strac.core.LogProvider.info("Buffered collection ", dataSize, " Segment size", segmentSize, " Buffer count ", _buffers.length );

        System.out.print("Buffered collection. Data size ");
        System.out.print("" + dataSize);
        System.out.print(" Segment size " + segmentSize);
        System.out.println(" Number of buffers " + _buffers.length);


        try {

            int bufIdx = 0;

            for (long offset = 0 ; offset < _size ; offset += segmentSize)
            {
                long remainingFileSize = _size - offset;
                long thisSegmentSize = Math.min(segmentSize, remainingFileSize);
                _buffers[bufIdx++] = inChannel.map(FileChannel.MapMode.READ_WRITE, offset, thisSegmentSize);
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public abstract void setData(T value, ByteBuffer buff);

    @Override
    public void set(long position, T value) {
        position = position*dataSize();

        ByteBuffer buf = buffer(position);

        if(value != null)
            setData(value, buf);
    }

    private ByteBuffer buffer(long index){



        try {
            ByteBuffer buf =  _buffers[(int)(index/segment_size)];

            buf.position((int) (index % segment_size));
            return buf;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public abstract T readFromFile(ByteBuffer buff);

    @Override
    public T read(long position) {
        position = position*dataSize();

        ByteBuffer buf = buffer(position);

        return readFromFile(buf);
    }

    @Override
    public void close() {
        //buffer.clear(); // do something with the data and clear/compact it.
        try {
            inChannel.close();
            aFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void dispose() {
        new File(this.filename).delete();
    }


    @Override
    public long size() {
        return _size/dataSize();
    }

    @Override
    public void reset() {
        // buffer.position(0);
    }

    @Override
    public T[] getPlain() {
        return null;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<T> adaptor) throws IOException {
        this.reset();

        for(MappedByteBuffer buff: _buffers)
            buff.force();
        int position = 0;

        for(T item: this){
            String value = adaptor.getValue(item);

            //System.out.println(String.format("\\node at (%s,%s) {%s};", (position++)*0.5 - 0.75, 2.75, value.substring(0, 1)));

            if(value != null)
                wr.write(value);
        }

        wr.close();
    }

    @Override
    public String getUniqueId() {
        return filename;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BufferedCollection.BufferedIterator();
    }


    public class BufferedIterator implements Iterator<T>{

        int index = 0;


        public BufferedIterator(){

        }

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public T next() {
            return read(index++);
        }
    }
}
