import align.InsertOperation;
import core.LogProvider;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.MultiDimensionalCollection;
import org.apache.velocity.runtime.log.Log;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.ByteBuffer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void test01CreateOne(){

        BufferedCollection<Integer> coll = new BufferedCollection<>("test.buff", 4, Integer.MAX_VALUE /2, adaptor);


        coll.set(0,10);
        coll.set(1,11);
        coll.set(2, 12);
        coll.set(3, 13);

        coll.close();
    }



    @Test
    public void test02ComplexDataStructure(){

        BufferedCollection<InsertOperation> coll = new BufferedCollection<>("operations.buff", 4, Integer.MAX_VALUE /2, InsertOperation.OperationAdapter);


        coll.set(0,new InsertOperation(1, 2));
        coll.set(1,new InsertOperation(3, 4));
        coll.set(2, new InsertOperation(5, 12));
        coll.set(3, new InsertOperation(1239, 412));

        coll.close();
    }


    @Test
    public void test03ReadComplexDataStructure(){

        BufferedCollection<InsertOperation> coll = new BufferedCollection<>("operations.buff", 4, Integer.MAX_VALUE /2, InsertOperation.OperationAdapter);

        for(InsertOperation op: coll)
            LogProvider.info(op);

        coll.close();
    }


    @Test
    public void test04LoadAndRetrieve(){

        BufferedCollection<Integer> coll = new BufferedCollection<>("test.buff", 4, Integer.MAX_VALUE/2, adaptor);


        LogProvider.info(coll.size());

        Assert.assertEquals(4, coll.size());

        for(int i = 0; i< coll.size(); i++)
            LogProvider.info(coll.read(i));

        coll.close();
    }


    @Test
    public void test05CreateLarge(){

        // 1M byte file
        BufferedCollection<Integer> coll = new BufferedCollection<>("large.buff", 10000000, Integer.MAX_VALUE/2 ,adaptor);


        for(int i = 0; i< 10000000; i++)
            coll.set(i, 10);

        coll.close();

    }

    @Test
    public void test06Multidimensional(){
        MultiDimensionalCollection<Integer> bi = new MultiDimensionalCollection<>("bidimensional.buff", adaptor, 10, 10);

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++)
                bi.set(i*10 + j, i, j);
        }

        bi.close();
    }


    @Test
    public void test07ReadMultidimensional(){
        MultiDimensionalCollection<Integer> bi = new MultiDimensionalCollection<>("bidimensional.buff", adaptor, 10, 10);

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++) {
                System.out.print(bi.get(i, j) + " ");

                Assert.assertEquals(10*i + j, (int)bi.get(i, j));
            }

            System.out.println();
        }

        bi.close();

        bi.dispose();
    }


    @Test
    public void test08Json(){
        BufferedCollection<InsertOperation> bi = new BufferedCollection<InsertOperation>("json.buff", 3, Integer.MAX_VALUE/2, InsertOperation.OperationAdapter);

        bi.set(0,new InsertOperation(0, 10));
        bi.set(1, new InsertOperation(2, 10));
        bi.set(2, new InsertOperation(5, 10));

        bi.close();

    }


    @Test
    public void test09ReadJson(){
        BufferedCollection<InsertOperation> bi = new BufferedCollection<InsertOperation>("json.buff", 3,Integer.MAX_VALUE/2,  InsertOperation.OperationAdapter);

        LogProvider.info(bi.read(0));
        bi.close();

    }

}
