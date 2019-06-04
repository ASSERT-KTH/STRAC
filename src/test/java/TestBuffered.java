import core.LogProvider;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.MultiDimensionalCollection;
import org.apache.velocity.runtime.log.Log;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class TestBuffered {

    static BufferedCollection.ITypeAdaptor<Integer> adaptor = new BufferedCollection.ITypeAdaptor<Integer>() {
        @Override
        public Integer fromBytes(byte[] chunk) {
            return ByteBuffer.wrap(chunk).getInt();
        }

        @Override
        public byte[] toBytes(Integer i) {
            return ByteBuffer.allocate(4).putInt(i).array();
        }

        @Override
        public int size() {
            return 4;
        }
    };

    @Test
    public void testCreateOne(){

        BufferedCollection<Integer> coll = new BufferedCollection<>("test.buff", 4, adaptor);


        coll.add(10);
        coll.add(11);
        coll.add(12);
        coll.add(13);

        coll.close();
    }


    @Test
    public void testLoadAndRetrieve(){

        BufferedCollection<Integer> coll = new BufferedCollection<>("test.buff", adaptor);


        LogProvider.info(coll.size());

        Assert.assertEquals(4, coll.size());

        for(int i = 0; i< coll.size(); i++)
            LogProvider.info(coll.read(i));

        coll.close();
    }


    @Test
    public void testCreateLarge(){

        // 1M byte file
        BufferedCollection<Integer> coll = new BufferedCollection<>("large.buff", 10000000 ,adaptor);


        for(int i = 0; i< 1000000; i++)
            coll.add(10);

        coll.close();

    }

    @Test
    public void testMultidimensional(){
        MultiDimensionalCollection<Integer> bi = new MultiDimensionalCollection<>("bidimensional.buff", adaptor, 10, 10);

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++)
                bi.set(i*10 + j, i, j);
        }

        bi.close();
    }


    @Test
    public void testReadMultidimensional(){
        MultiDimensionalCollection<Integer> bi = new MultiDimensionalCollection<>("bidimensional.buff", adaptor, 10, 10);

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++)
                System.out.print(bi.get(i, j) + " ");

            System.out.println();
        }

        bi.close();
    }

}
