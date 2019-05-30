import align.*;
import align.implementations.DWT;
import align.implementations.FastDWT;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemorySet;
import core.utils.TimeUtils;
import ngram.hash_keys.IHashCreator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

public class TestDWT {

    Random r = new Random();

    @Before
    public void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {
            @Override
            public IArray<Integer> allocateNewArray() {
                return new InMemoryArray();
            }

            @Override
            public IArray<Integer> allocateNewArray(int size) {
                return new InMemoryArray(size);
            }

            @Override
            public IArray<Integer> allocateNewArray(Integer[] items) {
                return new InMemoryArray(items);
            }

            @Override
            public IHashCreator<Integer, BigInteger[]> getHashCreator() {
                return null;
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return new InMemorySet<>(new HashSet<>());
            }
        });
    }


    public List<Integer> generateRandomIntegers(int size){

        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < size; i++)
            result.add(r.nextInt(30000));

        return result;
    }


    @Test
    public void testShortAlignment(){

        IArray<Integer> trace1 = new InMemoryArray(Arrays.asList(1,2,3, 2, 1, 3, 1));
        IArray<Integer> trace2 = new InMemoryArray(Arrays.asList(1, 2, 3, 2, 4, 2, 1, 4, 1));

        Aligner al = new DWT((a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(trace2, trace1);

        LogProvider.info(distance.getDistance());

        LogProvider.info(distance.getInsertions());
    }


    @Test
    public void testLargeAlign2(){

        // BREAK !!! due to java heap


        IArray<Integer> trace1 = new InMemoryArray(generateRandomIntegers(10000));
        IArray<Integer> trace2 = new InMemoryArray(generateRandomIntegers(10000));

        TimeUtils utl = new TimeUtils();
        Aligner al;
        AlignDistance distance;



        utl.reset();

        al = new FastDWT(2, (a, b) -> a == b? 2: -1);

        distance =  al.align(trace1, trace2);

        utl.time();

        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());


        utl.reset();
        al = new DWT((a, b) -> a == b? 2: -1);

        distance =  al.align(trace1, trace2);

        utl.time();
        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());
    }


    @Test
    public void testLargeAlign4(){

        // BREAK !!! due to java heap


        IArray<Integer> trace1 = new InMemoryArray(Arrays.asList(1,2,3, 2, 1, 3, 1));
        IArray<Integer> trace2 = new InMemoryArray(Arrays.asList(1, 2, 3, 2, 4, 2, 1, 4, 1));


        Aligner al = new FastDWT( 0, (a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(trace1, trace2);

        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());


        al = new DWT((a, b) -> a == b? 2: -1);

        distance =  al.align(trace1, trace2);

        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());
    }

    @Test
    public void testScaleOperations(){

        List<InsertOperation> ops = new ArrayList<>();
        ops.add(new InsertOperation(0,0));
        ops.add(new InsertOperation(1,1));
        ops.add(new InsertOperation(1,2));
        ops.add(new InsertOperation(1,3));
        ops.add(new InsertOperation(1,4));
        ops.add(new InsertOperation(1,4));
        ops.add(new InsertOperation(5,4));


        //DWTHelper.scalePath(ops, 2, 6, 6);
    }


    @Test
    public void testRadius(){

        List<InsertOperation> ops = new ArrayList<>();
        ops.add(new InsertOperation(0,0));
        ops.add(new InsertOperation(1,1));
        ops.add(new InsertOperation(1,2));
        ops.add(new InsertOperation(1,3));
        ops.add(new InsertOperation(1,4));
        ops.add(new InsertOperation(1,4));
        ops.add(new InsertOperation(5,4));


       // List<InsertOperation> grow = DWTHelper.scalePath(ops, 2, 6, 6);

        //DWTHelper.createWindow(grow, 2, 10, 10);
    }
}
