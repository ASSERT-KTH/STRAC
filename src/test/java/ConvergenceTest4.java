import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
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
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvergenceTest4 {


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
    public void testConvergence(String name,int eq, String f1, String f2) throws IOException {


        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "DTW";
        dto.method.params = Arrays.asList(
                5.0
        );
        dto.distanceFunctionName = "dBin";
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

        /* comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));*/

        AlignInterpreter interpreter = new AlignInterpreter(null);


        try {
            interpreter.execute(dto, (distance, success, mismatch, gaps1, gaps2, total) -> {
                TestLogProvider.info( "[",eq, ",", distance.getDistance(), ",", success, ",",  mismatch, ",", gaps1, ",",gaps2, ",",total, ",",
                        String.format("\"%s\"", name),"],");
            });
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }


        System.gc();
        ServiceRegister.dispose();
    }


    //@Test
    public void _www_github_com_www_github_com() throws IOException {
        String testPair = "www_github_com_www_github_com";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/15.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/74.www.github.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/26.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/66.www.github.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/69.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/55.www.github.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/40.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/46.www.github.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/84.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/24.www.github.com.timer.traces.txt");



    }

    @Test
    public void _www_github_com_wikipedia_org() throws IOException {
        String testPair = "www_github_com_wikipedia_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/66.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/49.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/92.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/63.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/26.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/49.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/98.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/17.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/98.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/51.wikipedia.org.timer.traces.txt");



    }

    //@Test
    public void _www_github_com_www_kth_se() throws IOException {
        String testPair = "www_github_com_www_kth_se";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/22.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/69.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/6.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/75.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/92.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/66.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/93.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/77.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/91.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/83.www.kth.se.timer.traces.txt");



    }

    //@Test
    public void _www_github_com_www_google_com() throws IOException {
        String testPair = "www_github_com_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/10.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/100.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/51.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/8.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/37.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/77.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/97.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/29.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/85.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/98.www.google.com.timer.traces.txt");



    }

    //@Test
    public void _www_github_com_youtube_com() throws IOException {
        String testPair = "www_github_com_youtube_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/60.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/79.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/58.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/64.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/58.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/21.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/13.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/27.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/6.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/67.youtube.com.timer.traces.txt");



    }

    //@Test
    public void _www_github_com_2019_splashcon_org() throws IOException {
        String testPair = "www_github_com_2019_splashcon_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/91.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/62.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/77.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/44.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/38.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/82.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/41.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/29.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/6.www.github.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/27.2019.splashcon.org.timer.traces.txt");



    }

    //@Test
    public void _wikipedia_org_wikipedia_org() throws IOException {
        String testPair = "wikipedia_org_wikipedia_org";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/51.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/18.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/28.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/2.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/52.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/96.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/40.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/52.wikipedia.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/2.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/51.wikipedia.org.timer.traces.txt");



    }

    //@Test
    public void _wikipedia_org_www_kth_se() throws IOException {
        String testPair = "wikipedia_org_www_kth_se";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/68.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/27.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/63.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/45.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/28.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/41.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/60.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/99.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/3.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/87.www.kth.se.timer.traces.txt");



    }

    //@Test
    public void _wikipedia_org_www_google_com() throws IOException {
        String testPair = "wikipedia_org_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/40.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/48.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/36.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/81.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/94.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/19.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/76.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/1.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/63.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/45.www.google.com.timer.traces.txt");



    }

    //@Test
    public void _wikipedia_org_youtube_com() throws IOException {
        String testPair = "wikipedia_org_youtube_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/100.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/50.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/72.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/11.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/17.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/3.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/85.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/7.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/68.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/25.youtube.com.timer.traces.txt");



    }

    //@Test
    public void _wikipedia_org_2019_splashcon_org() throws IOException {
        String testPair = "wikipedia_org_2019_splashcon_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/2.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/72.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/42.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/65.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/96.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/55.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/89.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/42.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/71.wikipedia.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/83.2019.splashcon.org.timer.traces.txt");



    }

    //@Test
    public void _www_kth_se_www_kth_se() throws IOException {
        String testPair = "www_kth_se_www_kth_se";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/68.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/83.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/99.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/90.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/17.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/61.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/36.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/6.www.kth.se.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/35.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/39.www.kth.se.timer.traces.txt");



    }

    //@Test
    public void _www_kth_se_www_google_com() throws IOException {
        String testPair = "www_kth_se_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/73.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/76.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/100.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/70.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/2.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/49.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/20.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/28.www.google.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/57.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/91.www.google.com.timer.traces.txt");



    }

    //@Test
    public void _www_kth_se_youtube_com() throws IOException {
        String testPair = "www_kth_se_youtube_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/47.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/41.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/26.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/94.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/20.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/81.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/52.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/52.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/9.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/46.youtube.com.timer.traces.txt");



    }

    //@Test
    public void _www_kth_se_2019_splashcon_org() throws IOException {
        String testPair = "www_kth_se_2019_splashcon_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/4.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/58.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/78.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/86.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/46.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/5.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/78.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/99.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/68.www.kth.se.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/58.2019.splashcon.org.timer.traces.txt");



    }

    //@Test
    public void _www_google_com_www_google_com() throws IOException {
        String testPair = "www_google_com_www_google_com";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/28.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/15.www.google.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/88.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/41.www.google.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/26.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/53.www.google.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/71.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/37.www.google.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/18.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/96.www.google.com.timer.traces.txt");



    }

    //@Test
    public void _www_google_com_youtube_com() throws IOException {
        String testPair = "www_google_com_youtube_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/21.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/20.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/89.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/10.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/90.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/62.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/57.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/88.youtube.com.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/21.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/48.youtube.com.timer.traces.txt");



    }

    //@Test
    public void _www_google_com_2019_splashcon_org() throws IOException {
        String testPair = "www_google_com_2019_splashcon_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/42.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/69.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/59.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/54.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/70.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/82.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/82.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/88.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/38.www.google.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/59.2019.splashcon.org.timer.traces.txt");



    }

    //@Test
    public void _youtube_com_youtube_com() throws IOException {
        String testPair = "youtube_com_youtube_com";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/66.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/27.youtube.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/100.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/59.youtube.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/69.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/60.youtube.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/24.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/72.youtube.com.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/54.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/12.youtube.com.timer.traces.txt");



    }

    //@Test
    public void _youtube_com_2019_splashcon_org() throws IOException {
        String testPair = "youtube_com_2019_splashcon_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/52.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/28.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/6.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/69.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/9.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/95.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/40.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/51.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/29.youtube.com.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/7.2019.splashcon.org.timer.traces.txt");



    }

    //@Test
    public void _2019_splashcon_org_2019_splashcon_org() throws IOException {
        String testPair = "2019_splashcon_org_2019_splashcon_org";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/93.2019.splashcon.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/53.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/16.2019.splashcon.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/96.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/64.2019.splashcon.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/26.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/92.2019.splashcon.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/66.2019.splashcon.org.timer.traces.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/89.2019.splashcon.org.timer.traces.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_timer/1.2019.splashcon.org.timer.traces.txt");



    }


}
