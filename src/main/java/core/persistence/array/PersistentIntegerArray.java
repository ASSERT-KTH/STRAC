package core.persistence.array;

import core.data_structures.IArray;
import core.persistence.PersistentDataStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class PersistentIntegerArray extends PersistentDataStructure  implements IArray<Integer> {

    @Override
    public Integer read(int position) {
        try {
            if(_policy == CachePolicy.SEQUENTIAL){
                if(cachedElements != null && position >= cacheStartIn && position < cacheStartIn + cachedElements.length){
                    return cachedElements[position - cacheStartIn];
                }else{

                   this.saveCache();
                   // Unique load
                   this.loadCache(position, cacheSize);

                   return  this.read(position);
                }
            }else{
                _access.seek(position + HEADER_SIZE);
                return _access.readInt();

            }
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public long size() {
        return _size/4;
    }

    @Override
    public void write(int position, Integer value) {
        try {

            if (_policy == CachePolicy.SEQUENTIAL) {
                if (cachedElements != null && position >= cacheStartIn && position < cacheStartIn + cacheSize) {
                    cachedElements[position - cacheStartIn] = value;
                } else {

                    // Save cache and load again
                    this.saveCache();

                    this.loadCache(position,cacheSize);

                    this.write(position, value);
                }
            } else {

                _access.seek(position + HEADER_SIZE);
                _access.writeInt(value);
            }
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void add(Integer value) {

        try {
            _access.seek(HEADER_SIZE + this._size);
            _access.writeInt(value);

            this._size += 4;

            this.saveHeader();

        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void add(int index, Integer value) {

    }

    @Override
    public IArray<Integer> subArray() {
        return null;
    }

    public enum CachePolicy{
        SEQUENTIAL,
        NONE
    }

    private CachePolicy _policy;
    private int cacheStartIn;
    private int[] cachedElements;
    private int cacheSize;
    private int HEADER_SIZE = 8;

    // Header files
    private long _size;


    public PersistentIntegerArray(String storeIn, CachePolicy policy, int cachedElements) throws IOException {
        this(new File(storeIn), policy, cachedElements);
    }


    public PersistentIntegerArray(File storeIn, CachePolicy policy, int cachedElements) throws IOException {
        this(storeIn);

        this.cachedElements = null;
        cacheStartIn = -1;
        this.cacheSize = cachedElements;

        this._policy = policy;


        this.loadHeader();
    }

    private PersistentIntegerArray(File storeIn) throws FileNotFoundException {
        super(storeIn);
    }

    private void loadHeader() throws IOException {

        if(super._access.length() != 0){ // Not empty file
            this._access.seek(0);
            this._size = this._access.readLong();
        }
        else{
         this.saveHeader();
        }

    }

    private void saveHeader() throws IOException {

        this._access.seek(0);
        this._access.writeLong(this._size);

    }

    private void saveCache() throws IOException {

        if(cacheStartIn >= 0) {

            ByteBuffer buff = ByteBuffer.allocate(4 * cacheSize);

            buff.asIntBuffer().put(this.cachedElements);

            _access.seek(cacheStartIn + HEADER_SIZE);
            _access.write(buff.array());
        }

    }

    private void loadCache(int position, int size) throws IOException {

        // size is in Integer struct sizeof


        int realSize = (int)Math.min(this._size, 4*size);

        byte[] buffer = new byte[realSize];

        _access.seek(position + HEADER_SIZE);
        _access.readFully(buffer);

        IntBuffer buff
                = ByteBuffer.wrap(buffer)
                //.order(ByteOrder.BIG_ENDIAN)
                .asIntBuffer();

        this.cachedElements = new int[realSize/4];
        buff.get(this.cachedElements);

        this.cacheStartIn = position;
    }

    public void close(){
        try {
            this.saveHeader();
            this.saveCache();
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

}
