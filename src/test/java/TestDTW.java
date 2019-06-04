import align.*;
import align.implementations.DTW;
import align.implementations.FastDTW;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemorySet;
import core.models.TraceMap;
import core.utils.TimeUtils;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

public class TestDTW {

    Random r = new Random();

    @Before
    public void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {

            @Override
            public <T> IArray<T> allocateNewArray(String id, int size, BufferedCollection.ITypeAdaptor<T> adaptor) {
                return new InMemoryArray<>();
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {
                return new InMemoryDict<>();
            }

            @Override
            public IHashCreator<Integer, BigInteger[]> getHashCreator() {
                return null;
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return new InMemorySet<>(new HashSet<>());
            }

            @Override
            public Generator getGenerator() {
                return null;
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

        Aligner al = new DTW((a, b) -> a == b? 2: -1);

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

        al = new FastDTW(2, (a, b) -> a == b? 2: -1);

        distance =  al.align(trace1, trace2);

        utl.time();

        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());


        utl.reset();
        al = new DTW((a, b) -> a == b? 2: -1);

        distance =  al.align(trace1, trace2);

        utl.time();
        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());
    }

    @Test
    public void testRealData(){

        List<TraceMap> traces = getTraces(
                "mutated12/original.bytecode.txt",
                "mutated10/original.bytecode.txt"
        );


        Aligner al = new FastDTW( 4, (a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(traces.get(0).plainTrace, traces.get(1).plainTrace);

        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());


        LogProvider.info("Distance", distance.getDistance(
                traces.get(0).plainTrace, traces.get(1).plainTrace,
                new IAlignComparer<Integer>() {
                    @Override
                    public double compare(Integer t1, Integer t2) {
                        return t1.equals(t2) ? 0: 1;
                    }

                    @Override
                    public double getGap() {
                        return 1;
                    }
                }

        ));
    }

    private List<TraceMap> getTraces(String path1, String path2){

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sha256.old.js").getFile());

        String testFolder = file.getAbsolutePath(); //;

        TraceHelper helper = new TraceHelper();
        List<String> files = Arrays.asList(
                String.format("%s/%s", testFolder, path1),
                String.format("%s/%s", testFolder, path2)
        );


        return helper.mapTraceSetByFileLine(files, false);
    }


    @Test
    public void testLargeAlign4(){

        // BREAK !!! due to java heap


        IArray<Integer> trace1 = new InMemoryArray(Arrays.asList(1,2,3, 2, 1, 3, 1));
        IArray<Integer> trace2 = new InMemoryArray(Arrays.asList(1, 2, 3, 2, 4, 2, 1, 4, 1));


        Aligner al = new FastDTW( 0, (a, b) -> a == b? 2: -1);

        AlignDistance distance =  al.align(trace1, trace2);

        LogProvider.info(distance.getDistance());
        LogProvider.info(distance.getInsertions());


        al = new DTW((a, b) -> a == b? 2: -1);

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
