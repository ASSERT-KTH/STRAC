import align.implementations.IImplementationInfo;
import com.google.gson.Gson;
import core.LogProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import core.TraceHelper;
import core.models.TraceMap;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvergenceTest8 {


    int start = 0;
    int end = 1000;



    @Before
    public void setup(){

        //TestLogProvider.info("Start test session", new Date());

        ServiceRegister.getProvider();

        comparers = new HashMap<>();

        System.gc();
    }

    static Map<String, IImplementationInfo> comparers;

    @After
    public void clean(){

        //TestLogProvider.info("Closing test session", new Date());
        // Warming up

        ServiceRegister.dispose();
        LogProvider.info("Disposing map files");
    }
    public void testConvergence(String name,int eq, String f1, String f2) throws IOException {


        TraceHelper helper = new TraceHelper();



        List<TraceMap> traces = helper.mapTraceSetByFileLine(Arrays.asList(f1, f2), false, false);



        String name1 = String.format("/Users/javier/Documents/export/%s_%s.json", name, traces.get(0).traceFileName);
        String name2 = String.format("/Users/javier/Documents/export/%s_%s.json", name, traces.get(1).traceFileName);


        TestLogProvider.info("[",  String.format("'%s'", name), ",",
                String.format("'%s'", name1), ",", String.format("'%s'", name2), "],"
        );


        FileWriter wr = new FileWriter(name1);

        wr.write(new Gson().toJson(traces.get(0).plainTrace));
        wr.close();


        FileWriter wr1 = new FileWriter(name2);

        wr1.write(new Gson().toJson(traces.get(1).plainTrace));
        wr1.close();

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
