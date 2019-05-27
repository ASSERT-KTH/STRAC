import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.implementations.DWT;
import core.LogProvider;
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
    public void testLargeAlign(){

        // BREAK !!! duw to java heap

        List<Integer> trace1 = generateRandomIntegers(80000);
        List<Integer> trace2 = generateRandomIntegers(80000);

        Aligner al = new DWT((a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(trace1, trace2);

        LogProvider.info(distance.getDistance());

        LogProvider.info(distance.getInsertions());
    }
}
