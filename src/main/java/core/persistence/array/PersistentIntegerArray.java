package core.persistence.array;

import core.ServiceRegister;
import core.data_structures.IArray;
import core.persistence.PersistentDataStructure;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Iterator;

public class PersistentIntegerArray extends PersistentDataStructure implements IArray<Integer>, Iterator<Integer> {

    @Override
    public Integer read(int position) {
        try {
            if(_policy == CachePolicy.SEQUENTIAL){
                if(cachedElements != null && position >= cacheStartIn && position < cacheStartIn + cachedElements.length){
                    return cachedElements[position - cacheStartIn];
                }
                else if(this.isInTail(position)){ // If the value is in tail cache
                    return readFromTail(position);
                }
                else{

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
    public int size() {
        return (int)_size/4;
    }

    private boolean isInTail(int position){
        return this.size()  - tailPosition <= position && position < size();
    }

    private int readFromTail(int position){
        return tailCache[(int)(position - (size() - tailPosition))];
    }

    private void writeToTail(int position, int value){

        tailCache[(int)(position - (size() - tailPosition))] = value;
    }

    @Override
    public void write(int position, Integer value) {
        try {

            if (_policy == CachePolicy.SEQUENTIAL) {
                if (cachedElements != null && position >= cacheStartIn && position < cacheStartIn + cachedElements.length) {
                    cachedElements[position - cacheStartIn] = value;
                }
                else if(this.isInTail(position)){ // If the value is in tail cache
                   this.writeToTail(position, value);
                }
                else {

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

        try{

            if(tailPosition == tailCache.length){
                this.saveTail();

                this.add(value);
            }else{
                tailCache[tailPosition] = value;
                tailPosition++;
                _size += 4;
            }

        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public IArray<Integer> subArray(int index, int size) {

        return this.moveTo(index, size);
    }

    private IArray<Integer> moveTo(int index, int size){

        IArray<Integer> sub = ServiceRegister.getProvider().allocateNewArray();

        for(int i  = index; i < size; i++){
            sub.add(this.read(i));
        }

        return sub;
    }

    private int iteratorPosition = 0;

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return iteratorPosition < this.size();
    }

    @Override
    public Integer next() {
        return this.readFromTail(iteratorPosition++);
    }

    public enum CachePolicy{
        SEQUENTIAL,
        NONE
    }

    private CachePolicy _policy;
    private int cacheStartIn;

    // double size for adding
    private int[] cachedElements;


    private int[] tailCache;
    private int tailPosition;

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


        this.tailCache = new int[cachedElements];
        this.tailPosition = 0;

        this._policy = policy;


        this.loadHeader();

    }

    private PersistentIntegerArray(File storeIn) throws FileNotFoundException {
        super(storeIn);
    }

    @Override
    public void validate() {

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

        if(cacheStartIn >= 0) // Check is there was a previous operation
        {

            ByteBuffer buff = ByteBuffer.allocate(4 * cachedElements.length);

            buff.asIntBuffer().put(this.cachedElements);

            _access.seek(cacheStartIn + HEADER_SIZE);
            _access.write(buff.array());
        }

    }



    private void saveTail() {

        try {

            if(tailPosition >= 0) {
                _access.seek(HEADER_SIZE + this._size - (tailPosition) * 4);

                ByteBuffer buff = ByteBuffer.allocate(4 * (tailPosition));

                buff.asIntBuffer().put(this.tailCache, 0, tailPosition);

                _access.write(buff.array());

                this.tailCache = new int[cacheSize];
                this.tailPosition = 0;
            }
        }
        catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

    }


    private void loadCache(int position, int size) throws IOException {

        // size is in Integer struct sizeof


        int realSize = (int)Math.min(this._size, 4*size);

        byte[] buffer = new byte[realSize];

        _access.seek(position*4 + HEADER_SIZE);
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
            this.saveTail();

        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void dispose() {
        super._storage.delete();
    }

}
