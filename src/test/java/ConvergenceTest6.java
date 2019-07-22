import align.event_distance.DInst;
import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import core.LogProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import core.TraceHelper;
import core.data_structures.IArray;
import core.models.TraceMap;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvergenceTest6 {


    int start = 0;
    int end = 1000;



    @Before
    public void setup(){

        LogProvider.info(DTW.class, DInst.class);

        TestLogProvider.info("Start test session", new Date());

        ServiceRegister.getProvider();

        comparers = new HashMap<>();

        System.gc();
    }

    static Map<String, IImplementationInfo> comparers;

    @After
    public void clean(){

        TestLogProvider.info("Closing test session", new Date());
        // Warming up

        ServiceRegister.dispose();
        LogProvider.info("Disposing map files");
    }
    public void testConvergence(String name,int eq, String f1, String f2) throws IOException {


        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "DTW";

        dto.method.params = new ArrayList<>();

       // dto.distanceFunctionName = "dBin";
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 5;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.outputDir="reports";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                f1,
                f2
        );

        /*comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));*/

        AlignInterpreter interpreter = new AlignInterpreter(null);



        TestLogProvider.info("['", name, "',");

        TraceHelper h = new TraceHelper();
        List<TraceMap> traces =  h.mapTraceSetByFileLine(Arrays.asList(f1, f2), false, false);

        TestLogProvider.info(traces.get(0).plainTrace.size(), ",",traces.get(1).plainTrace.size(), "," );

        long now = System.nanoTime();

        try {
            interpreter.execute(dto, (distance, success, mismatch, gaps1, gaps2, total) -> {

                TestLogProvider.info( (System.nanoTime() - now)/(1000000), ",", distance);
            });
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        TestLogProvider.info("]");

        System.gc();
        ServiceRegister.dispose();
    }


    @Test
    public void t0() throws IOException {


        testConvergence("48k-48k", 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.5.bytecode.txt.st.processed.txt");

    }

    @Test
    public void t1() throws IOException {

        String testPair = "48k-59k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.89.bytecode.txt.st.processed.txt");

    }


    @Test
    public void t3() throws IOException {

        String testPair = "59k-85k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.25.bytecode.txt.st.processed.txt");

    }


    @Test
    public void t2() throws IOException {

        String testPair = "64k-59k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.25.bytecode.txt.st.processed.txt");

    }



    @Test
    public void t5() throws IOException {

        String testPair = "64k-64k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.55.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.15.bytecode.txt.st.processed.txt");

    }



    @Test
    public void t6() throws IOException {

        String testPair = "85k-85k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.55.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.25.bytecode.txt.st.processed.txt");

    }



    @Test
    public void t7() throws IOException {

        String testPair = "64k-147k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.55.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.25.bytecode.txt.st.processed.txt");

    }



    @Test
    public void t4() throws IOException {

        String testPair = "85k-147k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.55.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.25.bytecode.txt.st.processed.txt");

    }



    @Test
    public void t8() throws IOException {

        String testPair = "85k-147k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.55.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.25.bytecode.txt.st.processed.txt");

    }


    @Test
    public void t9() throws IOException {

        String testPair = "147k-147k";

        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.55.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.5.bytecode.txt.st.processed.txt");

    }



}
