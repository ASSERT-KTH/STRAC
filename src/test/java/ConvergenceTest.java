import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import align.implementations.LinearMemoryDTW;
import core.LogProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvergenceTest {


    int start = 0;
    int end = 1000;



    @Before
    public void setup(){

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
    public void testConvergence(String name, String f1, String f2) throws IOException {


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
                f1,
                f2
        );

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));

        AlignInterpreter interpreter = new AlignInterpreter(comparers, null);

        TestLogProvider.info(name, "[");

        for(int i = start; i < end; i += 10){

            dto.method.params = new Object[]{
                    i*1.0
            };

            int finalI = i;




            interpreter.execute(dto, (distance, success, mismatch, gaps1, gaps2, total) -> {
                TestLogProvider.info( "[", distance.getDistance(), ",", success, ",",  mismatch, ",", gaps1, ",",gaps2, ",",total, ",",
                        String.format("\"%s\"", name),"],");
            });


        }

        TestLogProvider.info("]");


    }



    @Test
    public void _youtube_com_youtube_com() throws IOException {
        String testPair = "youtube_com_youtube_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.88.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.82.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _youtube_com_2019_splashcon_org() throws IOException {
        String testPair = "youtube_com_2019_splashcon_org";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.20.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.90.bytecode.txt.st.processed.txt");
    }



    @Test
    public void _youtube_com_www_kth_se() throws IOException {
        String testPair = "youtube_com_www_kth_se";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.73.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.53.bytecode.txt.st.processed.txt");
    }



    @Test
    public void _youtube_com_www_github_com() throws IOException {
        String testPair = "youtube_com_www_github_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.69.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.10.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _youtube_com_wikipedia_org() throws IOException {
        String testPair = "youtube_com_wikipedia_org";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.29.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.18.bytecode.txt.st.processed.txt");
    }



    @Test
    public void _youtube_com_www_google_com() throws IOException {
        String testPair = "youtube_com_www_google_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.35.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.68.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _2019_splashcon_org_2019_splashcon_org() throws IOException {
        String testPair = "2019_splashcon_org_2019_splashcon_org";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.87.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.88.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _2019_splashcon_org_www_kth_se() throws IOException {
        String testPair = "2019_splashcon_org_www_kth_se";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.99.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.98.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _2019_splashcon_org_www_github_com() throws IOException {
        String testPair = "2019_splashcon_org_www_github_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.49.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _2019_splashcon_org_wikipedia_org() throws IOException {
        String testPair = "2019_splashcon_org_wikipedia_org";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.27.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.15.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _2019_splashcon_org_www_google_com() throws IOException {
        String testPair = "2019_splashcon_org_www_google_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.78.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.19.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_kth_se_www_kth_se() throws IOException {
        String testPair = "www_kth_se_www_kth_se";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.41.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.35.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_kth_se_www_github_com() throws IOException {
        String testPair = "www_kth_se_www_github_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.91.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.89.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_kth_se_wikipedia_org() throws IOException {
        String testPair = "www_kth_se_wikipedia_org";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.18.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.100.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_kth_se_www_google_com() throws IOException {
        String testPair = "www_kth_se_www_google_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.1.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.55.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_github_com_www_github_com() throws IOException {
        String testPair = "www_github_com_www_github_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.58.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.27.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_github_com_wikipedia_org() throws IOException {
        String testPair = "www_github_com_wikipedia_org";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.76.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.61.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_github_com_www_google_com() throws IOException {
        String testPair = "www_github_com_www_google_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.67.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.94.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _wikipedia_org_wikipedia_org() throws IOException {
        String testPair = "wikipedia_org_wikipedia_org";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.47.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.37.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _wikipedia_org_www_google_com() throws IOException {
        String testPair = "wikipedia_org_www_google_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.16.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.68.bytecode.txt.st.processed.txt");
    }



    //@Test
    public void _www_google_com_www_google_com() throws IOException {
        String testPair = "www_google_com_www_google_com";

        testConvergence(testPair,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.5.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.83.bytecode.txt.st.processed.txt");
    }


}
