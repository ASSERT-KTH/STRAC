package core.data_structures.postgreSQL;

import core.LogProvider;
import core.data_structures.IArray;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static core.utils.HashingHelper.getRandomName;

public class PostgreArray implements IArray<Integer> {

    String _traceName;
    PostgreInterface _interface;
    long size;

    public PostgreArray(String name){
        this._traceName = name;
        _interface = PostgreInterface.getInstance();

        size = _interface.executeScalarQuery(String.format("SELECT COUNT(*) as count FROM TRACE WHERE name = '%s'", name), 0l);

    }

    int addCacheSize = 10000;
    int elements = 0;

    String cacheString = "";

    @Override
    public void add(Integer value) {

        if(elements == addCacheSize ){
           clearAddCache();
        }
        else{
            cacheString += String.format("('%s', %s, %s),", _traceName, size++, value);
            elements++;
        }

    }

    private void clearAddCache(){
        if(cacheString.length() > 0){
            String toInser = cacheString.substring(0, cacheString.length() - 1);

            //LogProvider.info(toInser);
            _interface.executeQuery(String.format("INSERT INTO TRACE(name, index, value) VALUES %s",
                    toInser));
            elements = 0;
            cacheString  = "";
        }
    }

    @Override
    public void write(int position, Integer value) {

    }

    int readCacheSize = 40000;
    List<Integer> cache = null;
    int cacheStart = 0;



    @Override
    public Integer read(int position) {

        if(cache != null && position >= cacheStart && position <= cacheStart + readCacheSize){
            return cache.get(position - cacheStart);
        }else{

            cache = _interface.executeCollection(String.format("SELECT value FROM TRACE WHERE index >= %s AND index <= %s", position, position + readCacheSize));
            cacheStart = position;

            return read(position);
        }

    }

    @Override
    public void close() {
        clearAddCache();
    }

    @Override
    public void dispose() {
        _interface.executeQuery(String.format("DELETE FROM TRACE WHERE name='%s'", _traceName));
    }

    @Override
    public IArray<Integer> subArray(int index, int size) {

        String name = getRandomName();

        _interface.executeQuery(String.format("INSERT INTO TRACE(name, value, index, map)" +
                " (SELECT '%s', value, index - %s, map FROM TRACE " +
                "WHERE name='%s' AND index >= %s AND index <= %s)", name, index, _traceName, index, index + size - 1));

        return new PostgreArray(name);
    }

    @Override
    public int size() {
        return (int)size;
    }



    @NotNull
    @Override
    public Iterator<Integer> iterator(){
        return new PostGresIterator(this);
    }


    public class PostGresIterator implements Iterator<Integer>{

        PostgreArray arr;
        int index = 0;

        public PostGresIterator(PostgreArray arr){
            this.arr = arr;

        }

        @Override
        public boolean hasNext() {
            return index < arr.size - 1;
        }

        @Override
        public Integer next() {
            return arr.read(this.index++);
        }
    }
}
