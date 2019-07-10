import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import align.implementations.LinearMemoryDTW;
import core.*;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class StressTest {


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

        ServiceRegister.dispose();
        LogProvider.info("Disposing map files");
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
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.10.bytecode.txt.st.processed.txt",
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
    static int end = 230;

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
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.12.bytecode.txt.st.processed.txt"
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


    @Test
    public void testLatexExampleAlign() throws IOException {

        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "DTW";
        dto.method.params = new Object[]{
               //2.0
        };
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 10;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.outputDir="reports";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.1.bytecode.txt.st.processed.txt"
        );

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));


        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        interpreter.execute(dto);

    }

    @Test
    public void testLatexExample2Align() throws IOException {

        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "DTW";
        dto.method.params = new Object[]{
                //2.0
        };
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 2;
        dto.comparison.diff = 2;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.outputDir="reports";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.github.com.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.12.bytecode.txt.st.processed.txt"
        );



        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));


        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        interpreter.execute(dto);

    }

}
