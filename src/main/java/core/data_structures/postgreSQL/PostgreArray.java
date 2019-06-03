package core.data_structures.postgreSQL;

import com.google.gson.Gson;
import core.LogProvider;
import core.data_structures.IArray;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static core.utils.HashingHelper.getRandomName;

public class PostgreArray<T> implements IArray<T> {

    String _traceName;
    PostgreInterface _interface;
    Class<T> clazz;
    long size;

    public PostgreArray(String name, Class<T> clazz){
        this._traceName = name;
        _interface = PostgreInterface.getInstance();

        size = _interface.executeScalarQuery(String.format("SELECT COUNT(*) as count FROM TRACE WHERE name = '%s'", name), 0l);

        this.clazz = clazz;
    }

    int addCacheSize = 30000;
    List<PosgresInsertOperation<T>> cachedElements = new ArrayList<>();


    String getObjectRepr(Object obj) {
        return new Gson().toJson(obj);
    }

    @Override
    public void add(T value) {

        if(cachedElements.size() == addCacheSize ){
           clearAddCache();
        }
        else{
            cachedElements.add(new PosgresInsertOperation<T>(value, size++));
        }

    }

    @Override
    public void add(int position, T value) {
        _interface.executeQuery(String.format("UPDATE TRACE SET index=index + 1 WHERE name='%s' AND index >= %s", _traceName, position));

        _interface.executeQuery(String.format("INSERT INTO TRACE(name, index, value) VALUES(" +
                "'%s', %s, '%s'" +
                ")", _traceName, position, getObjectRepr(value)));



    }

    private void clearAddCache(){
        if(cachedElements.size() > 0){

            CharSequence[] ops = new String[cachedElements.size()];

            for(int i = 0; i < cachedElements.size(); i++)
                ops[i] = cachedElements.get(i).getValues();

            //LogProvider.info(toInser);
            _interface.executeQuery(String.format("INSERT INTO TRACE(name, index, value) VALUES %s",
                    String.join(",",ops)));
            cachedElements.clear();
        }
    }

    int readCacheSize = 30000;
    PostgresCache cache;


    @Override
    public T read(int position) {

        try {

            if(this.cachedElements.size() != 0)
                this.clearAddCache();

            if (cache != null && cache.inCache(position)) {
                return cache.getValue(position);
            } else {

                String query = String.format("SELECT value FROM TRACE WHERE index >= %s AND index <= %s AND name='%s'"
                        , Math.max(position - readCacheSize/2, 0), position + readCacheSize/2 - 1, _traceName);

                cache = new PostgresCache();

                cache.cache = _interface.executeCollection(query, clazz);
                cache.from = Math.max(position - readCacheSize/2, 0);

                return cache.getValue(position);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
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
    public IArray<T> subArray(int index, int size) {

        String name = getRandomName();

        _interface.executeQuery(String.format("INSERT INTO TRACE(name, value, index, map)" +
                " (SELECT '%s', value, index - %s, map FROM TRACE " +
                "WHERE name='%s' AND index >= %s AND index <= %s)", name, index, _traceName, index, index + size - 1));

        return new PostgreArray<T>(name, clazz);
    }

    @Override
    public int size() {
        return (int)size;
    }



    @NotNull
    @Override
    public Iterator<T> iterator(){
        return new PostGresIterator(this);
    }


    public class PostgresCache {

        public int from = 0;
        public int size;

        List<T> cache;


        public int getSize(){
            return cache.size();
        }

        public boolean inCache(int position){
            return position >= from && position - from < cache.size();
        }

        public T getValue(int position){
            return cache.get(position - from);
        }

    }


    public class PosgresInsertOperation<T> {

        public T value;
        public long index;

        public PosgresInsertOperation(T value, long index){
            this.value = value;
            this.index = index;
        }

        public String getValues(){
            return String.format("('%s', %s, '%s')", _traceName, index, getObjectRepr(value));
        }
    }

    public class PostGresIterator implements Iterator<T>{

        PostgreArray<T> arr;
        int index = 0;

        public PostGresIterator(PostgreArray<T> arr){
            this.arr = arr;

        }

        @Override
        public boolean hasNext() {

            return index < arr.size;
        }

        @Override
        public T next() {

            return arr.read(this.index++);
        }
    }
}
