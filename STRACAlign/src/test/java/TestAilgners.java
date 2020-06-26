import org.junit.Assert;
import org.junit.Test;
import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;
import strac.align.align.event_distance.DInst;
import strac.align.align.implementations.DTW;
import strac.align.align.implementations.FastDTW;
import strac.align.align.implementations.NoWarpPathDTW;
import strac.align.align.implementations.SIMDDTW;

import java.util.Random;

/**
 * @author Javier Cabrera-Arteaga on 2020-05-05
 */
public class TestAilgners {

    int MAX = 50;
    Random r  = new Random();

    int[] createRandomArray(int size){
        int[] result = new int[size];

        for(int i= 0; i < size; i++)
            result[i] = 1 + r.nextInt(MAX);

        return result;
    }

    double allowedApprox = 40;

    int[] tr1 = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    int[] tr2 = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    int[] tr3 = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2};
    int[] tr4 = new int[] {1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1};
    int[] tr5 = new int[] {1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,10,1,1,1,1,1,1,1,1,2,1,1,1,1,1};
    int[] tr6 = new int[] {1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,10,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1};
    int[] tr7 = new int[] {1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,10,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1, 10, 12, 13, 154, 1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

    int[] tr8 = new int[] {35,22,14,8,38,50,40,36,37,18,43,19,20,14,16,39,20,23,34,47,41,46,1,29,39,18,3,11,13,38,21,48,23,31,6,17,15,25,30,36,30,23,8,22,8,36,5,33,11,46,40,7,27,49,31,33,31,35,9,2,30,47,28,50,9,43,2,47,17,50,9,31,13,3,43,50,24,43,16,11,5,47,40,17,9,24,21,8,8,35,30,30,41,8,41,29,44,16,8,37,8,11,18,20,22,19,48,30,23,5,50,37,11,47,20,38,6,25,7,24,48,1,30,24,34,42,27,26,35,35,7,1,5,44,47,7,19,14,2,24,23,42,40,11,40,37,8,28,40,4,48,39,25,29,50,30,7,5,11,20,26,6,21,41,30,29,36,4,11,29,45,12,16,14,24,2,37,30,36,19};
    int[] tr9 = new int[] {45,29,26,28,44,50,4,16,2,2,42,3,7,49,50,12,3,9,12,6,22,41};

    int[] tr10 = {18,3,15,41,43,27,25,48,4,8,20,39,41,36,24,33,16,39,9,29,30,23,14,38,11,40,28,22,19,11,47,13,46,19,40,8,42,49,45,33,35,34,28,21,29,7,38,29,25,1,2,15,10,23,19,18,46,17,12,41,30,21,26,8,47,33,13,36,44,38,14,8,39,25,1,9,46,41,43,29,11,46,16,31,19,34,10,16,40,50,49,6,41,34,23,8,46,15,29,36,10,20,50,43,8,13,43,41,10,4,40,2,33,2,15,18,8,49,11,10,21,8,4,31,50,4,3,42,6,37,30,8,30,25,47,33,8,44,43,3,1,22,3,7,14,21,8,2,7,3,29,6,9,27,44,26,28,34,36,7,7,41,32,42,1,2,26,33,4,10,17,27,11,18,30,12,7,29,28,23,9,38,3,};
    int[] tr11 = {28,27,25,16,28,15,25,25,12,34,23,24,30,15,40,42,7,7,39,47,3,43,34,17,43,43,10,11,48,13,31,13,32,43,32,17,9,42,32,49,23,3,32,38,48,29,5,36,28,38,29,34,42,1,12,17,39,47,17,7,13,29,43,9,2,4,17,39,45,47,12,24,6,31,44,10,11,31,25,46,19,30,36,11,45,2,43,26,28,8,30,49,26,32,49,42,28,12,14,23,38,37,43,50,32,44,7,39,2,21,49,32,18,28,11,38,12,32,36,29,38,49,21,19,1,31,38,29,32,24,12,8,27,26,38,24,39,33,45,4,20,5,11,27,36,49,42,49,16,42,47,40,45,5,38,50,44,46,35,22,};

    // 0 0 0 0  0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
    @Test
    public void testMemoDTW(){
        DTW dtw = new DTW(comparer);
        AlignDistance distance = dtw.align(tr1, tr2);

        Assert.assertNotEquals(distance, null);
    }

    @Test
    public void testDTW(){
        Aligner dtw = new NoWarpPathDTW(comparer);
        AlignDistance distance = dtw.align(tr1, tr2);

        Assert.assertNotEquals(distance, null);
    }

    double getReferenceValue(int[] trace1, int[] trace2){

        DTW dtw = new DTW(comparer);
        AlignDistance distance = dtw.align(trace1, trace2);

        return distance.getDistance();
    }

    @Test
    public void testFastDTW(){
        FastDTW fdtw = new FastDTW(comparer, 200.0);
        AlignDistance d2 = fdtw.align(tr1, tr2);

        Assert.assertEquals(getReferenceValue(tr1, tr2), d2.getDistance(), allowedApprox);
    }


    @Test
    public void SIMDTest(){

        double reference = getReferenceValue(tr1, tr2);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr1, tr2);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }

    ICellComparer comparer = new ICellComparer() {
        @Override
        public int compare(int a, int b) {
            return Math.abs(a - b);
        }

        @Override
        public int gapCost(int position, TRACE_DISCRIMINATOR discriminator) {
            return 1;
        }
    };

    @Test
    public void SIMDTest2(){

        double reference = getReferenceValue(tr1, tr2);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr1, tr2);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }


    @Test
    public void SIMDTest3(){

        double reference = getReferenceValue(tr1, tr3);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr1, tr3);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }


    @Test
    public void SIMDTest4(){

        double reference = getReferenceValue(tr1, tr4);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr1, tr4);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }


    @Test
    public void SIMDTest5(){

        double reference = getReferenceValue(tr1, tr5);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr1, tr5);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }

    @Test
    public void SIMDTest6(){

        double reference = getReferenceValue(tr1, tr6);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr1, tr6);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }


    @Test
    public void SIMDTest7(){

        double reference = getReferenceValue(tr1, tr7);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr1, tr7);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }


    @Test
    public void SIMDTest8(){

        double reference = getReferenceValue(tr8, tr9);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr8, tr9);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }

    @Test
    public void SIMDTest9(){

        double reference = getReferenceValue(tr10, tr11);
        SIMDDTW dtw = new SIMDDTW(comparer);
        AlignDistance d2 = dtw.align(tr10, tr11);

        System.out.println(d2.getDistance());
        System.out.println(reference);

        Assert.assertEquals(reference, d2.getDistance(), allowedApprox);
    }

    public String getArray(int[] tr){
        String r = "";

        for(int i  = 0; i < tr.length; i++)
            r += tr[i] + ",";

        return r;
    }

    @Test
    public void SIMDTestRandom(){

        for(int i = 0; i < 1000; i++){
            int s1 = 10 + r.nextInt(1200);
            int s2 = 10 + r.nextInt(1200);

            int[] tr1 = createRandomArray(s1);
            int[] tr2 = createRandomArray(s2);


            double reference = getReferenceValue(tr1, tr2);
            SIMDDTW dtw = new SIMDDTW(comparer);
            AlignDistance d2 = dtw.align(tr1, tr2);

            System.out.println(d2.getDistance());
            System.out.println(reference);
            System.out.println();

            String msg = String.format("int[] tr1 = {%s};\nint[] tr2 = {%s};", getArray(tr1), getArray(tr2));

            Assert.assertEquals(msg, reference, d2.getDistance(), allowedApprox);
        }

    }

    @Test
    public void unsafeTest(){

        FastDTW fdtw = new FastDTW(comparer, 200.0);
        AlignDistance d2 = fdtw.align(tr1, tr2);

        Assert.assertEquals(getReferenceValue(tr1, tr2), d2.getDistance(), 0.01);
    }
}
