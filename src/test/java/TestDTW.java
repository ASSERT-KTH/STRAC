import align.*;
import align.implementations.*;
import core.*;
import core.data_structures.IArray;
import core.data_structures.memory.InMemoryArray;
import core.utils.DWTHelper;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class TestDTW {

    static List<IArray> openedArrays = new ArrayList<>();

    @Before
    public void setup(){

        TestLogProvider.info("Start test session", new Date());

        ServiceRegister.getProvider();
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


        IArray<Cell> ops = new InMemoryArray<>(null, 5);

        ops.set(4, new Cell(2 ,2));
        ops.set(3, new Cell(2 ,1));
        ops.set(2, new Cell(1 ,1));
        ops.set(1, new Cell(1 ,0));
        ops.set(0, new Cell(0 ,0));

        WindowedDTW.Window w = DWTHelper.expandWindow(ops, 2, 6, 6, 5,0, 0);

    }
}
