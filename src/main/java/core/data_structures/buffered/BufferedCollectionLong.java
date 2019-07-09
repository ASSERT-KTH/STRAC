package core.data_structures.buffered;

import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

public class BufferedCollectionLong  implements IArray<Long> {

    RandomAccessFile aFile;

    MappedByteBuffer[] _buffers;

    String filename;
    FileChannel inChannel;

    long _size;

    int segment_size;

    public BufferedCollectionLong(String fileName, long dataSize, int segmentSize) {
        aFile = null;
        this.filename = fileName;

        _size = dataSize*8;

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

    @Override
    public void set(long position, Long value) {
        position = position*8;

        ByteBuffer buf = buffer(position);

        buf.putLong(value);
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

    @Override
    public Long read(long position) {
        position = position*8;

        ByteBuffer buf = buffer(position);

        return buf.getLong();
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
        return _size/4;
    }

    @Override
    public void reset() {
        // buffer.position(0);
    }

    @Override
    public Long[] getPlain() {
        return null;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<Long> adaptor) throws IOException {
        this.reset();

        for(MappedByteBuffer buff: _buffers)
            buff.force();
        int position = 0;

        for(Long item: this){
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
    public Iterator<Long> iterator() {
        return new BufferedCollectionLong.BufferedIterator();
    }


    public class BufferedIterator implements Iterator<Long>{

        int index = 0;


        public BufferedIterator(){

        }

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Long next() {
            return read(index++);
        }
    }
}
