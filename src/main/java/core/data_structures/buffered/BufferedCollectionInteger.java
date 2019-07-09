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

public class BufferedCollectionInteger implements IArray<Integer> {

    RandomAccessFile aFile;

    MappedByteBuffer[] _buffers;

    String filename;
    FileChannel inChannel;

    long _size;

    int segment_size;

    public BufferedCollectionInteger(String fileName, long dataSize, int segmentSize) {
        aFile = null;
        this.filename = fileName;

        _size = dataSize*4;

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
    public void set(long position, Integer value) {
        position = position*4;

        ByteBuffer buf = buffer(position);

        buf.putInt(value);
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
    public Integer read(long position) {
        position = position*4;

        ByteBuffer buf = buffer(position);

        return buf.getInt();
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
    public Integer[] getPlain() {
        return null;
    }

    @Override
    public void writeTo(Writer wr, IMapAdaptor<Integer> adaptor) throws IOException {
        this.reset();

        for(MappedByteBuffer buff: _buffers)
            buff.force();
        int position = 0;

        for(Integer item: this){
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
    public Iterator<Integer> iterator() {
        return new BufferedIterator();
    }


    public class BufferedIterator implements Iterator<Integer>{

        int index = 0;


        public BufferedIterator(){

        }

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Integer next() {
            return read(index++);
        }
    }
}
