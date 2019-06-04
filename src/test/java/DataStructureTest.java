import core.utils.TimeUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataStructureTest {

    /*@Test
    public void test0CreateFile() throws IOException {

        new File("test1.array").delete();
    }

    @Test
    public void test1CreateFile() throws IOException {

        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);

        store.close();
    }



    @Test
    public void test6WriteHugeFile() throws IOException {

        // Write 1GB file with adding operation

        TimeUtils utl = new TimeUtils();

        utl.reset();


        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 100);


        for(int i = 0; i < 1 << 26; i++) {
            store.add(i);

            //int r = store.read((int)store.size() - 1);

            //Assert.assertEquals(i, r);
        }


        store.close();

        utl.time("Generation took");
    }



    @Test
    public void test7ReadHugeFile() throws IOException {

        // Write 1GB file with adding operation




        PersistentIntegerArray store = new PersistentIntegerArray("test1.array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 1000);

        Assert.assertEquals(store.size(), 1 << 26);

        for(int i = 0; i < 1000; i++){
            int value = store.read(i);

            Assert.assertEquals(value, i);
        }

        store.close();

    }*/

}
