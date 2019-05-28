import core.persistence.array.PersistentIntegerArray;
import core.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class DataStructureTest {

    @Test
    public void test1CreateFile() throws IOException {

        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);

        store.close();
    }

    @Test
    public void test2AddNumber() throws IOException {

        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);

        long previousSize = store.size();

        store.add(10);
        store.add(10);

        Assert.assertEquals(store.size(), 2 + previousSize);
        store.close();
    }


    @Test
    public void test3CheckLoadedFileSize() throws IOException {

        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);

        store.close();
    }


    @Test
    public void test4loadFirstElement() throws IOException {

        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);

        int value = store.read(0);

        Assert.assertEquals(10, value);
        store.close();
    }



    @Test
    public void test5SetFirstValueto5() throws IOException {

        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);

        int value = store.read(0);

        Assert.assertEquals(10, value);

        store.write(0, 5);

        store.close();
    }


    @Test
    public void test6WriteHugeFile() throws IOException {

        // Write 1GB file with adding operation

        TimeUtils utl = new TimeUtils();

        utl.reset();


        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);


        for(int i = 0; i < 1 << 26; i++)
            store.add(i);


        store.close();

        utl.time("Generation took");
    }


}
