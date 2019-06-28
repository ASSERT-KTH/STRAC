import align.AlignDistance;
import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import align.implementations.LinearMemoryDTW;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.MultiDimensionalCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemorySet;
import core.utils.HashingHelper;
import interpreter.AlignInterpreter;
import interpreter.NGramsInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import ngram.Generator;
import ngram.generators.StringKeyGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static core.utils.HashingHelper.getRandomName;

public class StressTest {

    static List<IArray> openedArrays = new ArrayList<>();

    @Before
    public void setup(){

        TestLogProvider.info("Start test session", new Date());

        ServiceRegister.registerProvider(new IServiceProvider() {


            @Override
            public <T> IArray<T> allocateNewArray(String id, long size, BufferedCollection.ITypeAdaptor<T> adaptor) {
                IArray<T> result = new BufferedCollection<>(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2, adaptor);

                openedArrays.add(result);

                return result;
                //return new InMemoryArray<T>(getRandomName(), (int)size);
            }

            @Override
            public <T> IMultidimensionalArray<T> allocateMuldimensionalArray(BufferedCollection.ITypeAdaptor<T> adaptor, int... dimensions) {
                MultiDimensionalCollection<T> result = new MultiDimensionalCollection<T>(getRandomName(),adaptor, dimensions);
                openedArrays.add(result);

                return result;

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
    //@Test
    public void testHugeAlignment() throws IOException {

        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "FastDTW";
        dto.method.params = new Object[]{
                0.0
        };
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 2;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.outputDir="reports";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.7.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.bbc.com.10.bytecode.txt.st.processed.txt"
        );

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));


        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        interpreter.execute(dto);

    }

    @Test
    public void testConvergenceDifferents() throws IOException {

        TestLogProvider.info("Convergence different test");

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
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.google.com.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.github.com.10.bytecode.txt.st.processed.txt"
        );

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));

        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        TestLogProvider.info(dto.files.get(0), dto.files.get(1), "[");

        for(int i = start; i < end; i++){

            dto.method.params = new Object[]{
                    i*1.0
            };

            int finalI = i;

            interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
                TestLogProvider.info(finalI, ",", distance.getDistance(), ",", total, ",");
            });

        }

        TestLogProvider.info("]");


    }

    static int start  = 0;
    static int end = 100;
    @Test
    public void testConvergenceEquals() throws IOException {

        TestLogProvider.info("Convergence equals test");

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
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.google.com.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.google.com.12.bytecode.txt.st.processed.txt"
        );

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));

        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        TestLogProvider.info(dto.files.get(0), dto.files.get(1), "[");

        for(int i = start; i < end; i++){

            dto.method.params = new Object[]{
                    i*1.0
            };

            int finalI = i;

            interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
                TestLogProvider.info(finalI, ",", distance.getDistance(), ",", total, ",");
            });

        }

        TestLogProvider.info("]");

    }

    //@Test
    public void testGenerateNGrams() throws IOException {

        Payload dto = new Payload();
        dto.printComparisson = true;
        dto.exportNgram = new int[] {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 30, 50, 70, 80, 90, 100, 150, 160, 170, 200, 400, 600, 800, 1000, 2000, 3000, 4000, 5000, 8000, 10000, 15000, 20000, 30000, 40000, 50000
        };
        dto.outputDir="reports/ngrams";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.7.bytecode.txt.st.processed.txt"
        );

        NGramsInterpreter interpreter = new NGramsInterpreter();

        interpreter.execute(dto);

    }

    //@Test
    public void testLatexExampleAlign() throws IOException {

        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "DTW";
        dto.method.params = new Object[]{
               //2.0
        };
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 2;
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

        interpreter.execute(dto);

    }
}
