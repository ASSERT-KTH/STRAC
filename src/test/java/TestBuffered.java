import align.Cell;
import core.LogProvider;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.MultiDimensionalCollection;
import core.utils.HashingHelper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.ByteBuffer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBuffered {

    static BufferedCollection.ITypeAdaptor<Integer> adaptor = HashingHelper.IntegerAdapter;

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

        BufferedCollection<Cell> coll = new BufferedCollection<>("operations.buff", 4, Integer.MAX_VALUE /2, Cell.OperationAdapter);


        coll.set(0,new Cell(1, 2));
        coll.set(1,new Cell(3, 4));
        coll.set(2, new Cell(5, 12));
        coll.set(3, new Cell(1239, 412));

        coll.close();
    }


    @Test
    public void test03ReadComplexDataStructure(){

        BufferedCollection<Cell> coll = new BufferedCollection<>("operations.buff", 4, Integer.MAX_VALUE /2, Cell.OperationAdapter);

        for(Cell op: coll)
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
    public void test10Multidimensional(){
        MultiDimensionalCollection<Integer> bi = new MultiDimensionalCollection<>("bidimensional.buff", adaptor, Integer.MAX_VALUE/2, Integer.MAX_VALUE/2 + 100);

        for(int i = 0; i < Integer.MAX_VALUE/2; i++){
            for(int j = 0; j < Integer.MAX_VALUE/2 + 100; j++)
                bi.set(i*(Integer.MAX_VALUE/2 + 100) + j, i, j);
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
        BufferedCollection<Cell> bi = new BufferedCollection<Cell>("json.buff", 3,  (int)((Integer.MAX_VALUE + 1l)/2), Cell.OperationAdapter);

        bi.set(0,new Cell(0, 10));
        bi.set(1, new Cell(2, 10));
        bi.set(2, new Cell(5, 10));

        bi.close();

    }


    @Test
    public void test09ReadJson(){
        BufferedCollection<Cell> bi = new BufferedCollection<Cell>("json.buff", 3, (int)((Integer.MAX_VALUE + 1l)/2),  Cell.OperationAdapter);

        LogProvider.info(bi.read(0));
        bi.close();

    }



    @Test
    public void test12LargeFile(){
        MultiDimensionalCollection<Integer> bi = new MultiDimensionalCollection<Integer>("large.buff", adaptor, 63138, 58266);


        bi.set(10, 4607, 3993);

        bi.close();

    }

}
