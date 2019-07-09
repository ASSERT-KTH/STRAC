import align.*;
import align.implementations.*;
import core.*;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.MultiDimensionalCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemoryMultidimensional;
import core.data_structures.memory.InMemorySet;
import core.models.TraceMap;
import core.utils.DWTHelper;
import core.utils.HashingHelper;
import core.utils.TimeUtils;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import ngram.Generator;
import ngram.generators.StringKeyGenerator;
import ngram.hash_keys.IHashCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.DTDHandler;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static core.utils.HashingHelper.getRandomName;

public class TestDTW {

    static List<IArray> openedArrays = new ArrayList<>();

    @Before
    public void setup(){

        TestLogProvider.info("Start test session", new Date());

        ServiceRegister.registerProvider(new IServiceProvider() {


            @Override
            public <T> IArray<T> allocateNewArray(String id, long size, BufferedCollection.ITypeAdaptor<T> adaptor) {
                IArray<T> result = new InMemoryArray<T>(null, (int)size);


                return result;
                //return new InMemoryArray<T>(getRandomName(), (int)size);
            }

            @Override
            public <T> IMultidimensionalArray<T> allocateMuldimensionalArray(BufferedCollection.ITypeAdaptor<T> adaptor, int... dimensions) {

                return new InMemoryMultidimensional<>(adaptor, dimensions[0], dimensions[1]);

                //return new InMemoryMultidimensional<>(adaptor, dimensions[0], dimensions[1]);
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {



                return new InMemoryDict<TKey, TValue>();
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return new InMemorySet<>(new HashSet<>());
            }



            @Override
            public Generator getGenerator() {

                return new StringKeyGenerator(t -> t.stream().map(String::valueOf).collect(Collectors.joining(","))
                        , HashingHelper::hashList);
            }
        });
        comparers = new HashMap<>();

    }

    static Map<String, IImplementationInfo> comparers;

    @After
    public void clean(){

        TestLogProvider.info("Closing test session", new Date());
        // Warming up


        LogProvider.info("Disposing map files");
        for(IArray arr: openedArrays)
            arr.dispose();
    }




    @Test
    public void testShortAlignment() throws IOException {
        TestLogProvider.info("FastDTW radius time test");

        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "FastDTW";
        dto.method.params = new Object[]{
                5.0
        };
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 5;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.outputDir="reports";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/test_traces/1.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/test_traces/2.txt"
        );

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));

        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        TestLogProvider.info(dto.files.get(0), dto.files.get(1), "[");

        for(int i = 0; i < 100; i++){

            dto.method.params = new Object[]{
                    i*1.0
            };

            AtomicLong now = new AtomicLong(System.nanoTime());

            int finalI = i;

            interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
                TestLogProvider.info(finalI, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
                now.set(System.nanoTime());
            });

        }

        TestLogProvider.info("]");

    }


    @Test
    public void testShort2Alignment() throws IOException {
        TestLogProvider.info("FastDTW radius time test");

        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "FastDTW";
        dto.method.params = new Object[]{
                5.0
        };
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 5;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.outputDir="reports";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.github.com.10.bytecode.txt.st.processed.txt"
        );

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));

        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        TestLogProvider.info(dto.files.get(0), dto.files.get(1), "[");

        for(int i = 10; i < 100; i++){

            dto.method.params = new Object[]{
                    i*1.0
            };

            AtomicLong now = new AtomicLong(System.nanoTime());

            int finalI = i;

            interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
                TestLogProvider.info(finalI, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
                now.set(System.nanoTime());
            });

        }

        TestLogProvider.info("]");

    }



    @Test
    public void testExpand(){


        IArray<InsertOperation> ops = new InMemoryArray<>(null, 5);

        ops.set(4, new InsertOperation(2 ,2));
        ops.set(3, new InsertOperation(2 ,1));
        ops.set(2, new InsertOperation(1 ,1));
        ops.set(1, new InsertOperation(1 ,0));
        ops.set(0, new InsertOperation(0 ,0));

        WindowedDTW.Window w = DWTHelper.expandWindow(ops, 2, 6, 6, 5,0, 0);

    }
}
