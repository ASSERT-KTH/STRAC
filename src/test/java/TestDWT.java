import align.*;
import align.implementations.DWT;
import align.implementations.FastDWT;
import align.implementations.WindowedDWT;
import core.LogProvider;
import core.utils.ArrayHelper;
import core.utils.DWTHelper;
import core.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestDWT {

    Random r = new Random();


    public List<Integer> generateRandomIntegers(int size){

        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < size; i++)
            result.add(r.nextInt(30000));

        return result;
    }


    @Test
    public void testShortAlignment(){

        List<Integer> trace1 = Arrays.asList(1,2,3, 2, 1, 3, 1);
        List<Integer> trace2 = Arrays.asList(1, 2, 3, 2, 4, 2, 1, 4, 1);

        Aligner al = new DWT((a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(trace2, trace1);

        LogProvider.info(distance.getDistance());

        LogProvider.info(distance.getInsertions());
    }


    @Test
    public void testReduction(){

        List<Integer> trace1 = Arrays.asList(1,2,3, 2, 1, 3, 1);

        LogProvider.info(trace1);

        List<Integer> reduction = ArrayHelper.reduceSize(trace1, trace1.size() / 2,
                ArrayHelper::getMostFequentRepresentation);

    }
    @Test
    public void testLargeAlign(){

        // BREAK !!! due to java heap

        List<Integer> trace1 = generateRandomIntegers(80000);
        List<Integer> trace2 = generateRandomIntegers(80000);


        List<Integer> reduction1 = ArrayHelper.reduceSize(trace1, 100,
                ArrayHelper::getMostFequentRepresentation);

        List<Integer> reduction2 = ArrayHelper.reduceSize(trace2, 100,
                ArrayHelper::getMostFequentRepresentation);

        Aligner al = new DWT((a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(reduction1, reduction2);

        LogProvider.info(distance.getDistance());

        LogProvider.info(distance.getInsertions());
    }

    @Test
    public void testLargeAlign2(){

        // BREAK !!! due to java heap

        List<Integer> trace1 = generateRandomIntegers(80000);
        List<Integer> trace2 = generateRandomIntegers(80000);


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
    public void testLargeAlign3(){

        // BREAK !!! due to java heap

        List<Integer> trace1 = Arrays.asList(1,2,3, 2, 1, 3, 1);
        List<Integer> trace2 = Arrays.asList(1, 2, 3, 2, 4, 2, 1, 4, 1);



        Aligner al = new WindowedDWT((a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(trace2, trace1);

        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());
    }


    @Test
    public void testLargeAlign4(){

        // BREAK !!! due to java heap

        List<Integer> trace1 = Arrays.asList(1,2,3, 2, 1, 3, 1);
        List<Integer> trace2 = Arrays.asList(1, 2, 3, 2, 4, 2, 1, 4, 1);



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
