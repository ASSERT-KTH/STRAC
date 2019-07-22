import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import core.LogProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import core.TraceHelper;
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
import java.util.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvergenceTest3 {


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



        List<String> l = Arrays.asList(
                f1,
                f2
        );

        TraceHelper h = new TraceHelper();
        List<TraceMap> traces = h.mapTraceSetByFileLine(l, false, true);

        long min = Math.min(traces.get(0).plainTrace.size(), traces.get(1).plainTrace.size());
        long max = Math.max(traces.get(0).plainTrace.size(), traces.get(1).plainTrace.size());

        double total = 0;

        for(long i  = 0; i < min; i++)
        {
            TraceMap t1 = traces.get(0);
            TraceMap t2= traces.get(1);

            int s1 = t1.plainTrace.read(i);
            int s2 = t2.plainTrace.read(i);

            if(s1 == s2)
                total += 1;
        }

        TestLogProvider.info( "[",eq, ",", total, ",", total/max, ",",total/min, ",",
                String.format("\"%s\"", name),"],");

    }



    @Test
    public void _youtube_com_youtube_com() throws IOException {
        String testPair = "youtube_com_youtube_com";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.89.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.54.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.56.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.82.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.48.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.42.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.86.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.89.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.74.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _youtube_com_2019_splashcon_org() throws IOException {
        String testPair = "youtube_com_2019_splashcon_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.54.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.69.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.1.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.28.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.72.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.57.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.50.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.3.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.81.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.79.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _youtube_com_www_kth_se() throws IOException {
        String testPair = "youtube_com_www_kth_se";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.79.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.2.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.57.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.79.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.76.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.94.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.12.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.52.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.87.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _youtube_com_www_github_com() throws IOException {
        String testPair = "youtube_com_www_github_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.63.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.19.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.87.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.10.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.20.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.1.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.45.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.81.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.31.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.66.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _youtube_com_wikipedia_org() throws IOException {
        String testPair = "youtube_com_wikipedia_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.93.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.64.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.39.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.31.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.19.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.26.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.1.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.2.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.21.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.27.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _youtube_com_www_google_com() throws IOException {
        String testPair = "youtube_com_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.34.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.89.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.44.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.44.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.51.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.93.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.89.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.20.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/youtube.com.53.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.61.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _2019_splashcon_org_2019_splashcon_org() throws IOException {
        String testPair = "2019_splashcon_org_2019_splashcon_org";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.36.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.54.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.74.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.87.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.33.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.88.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.83.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.74.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.71.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.42.bytecode.txt.st.processed.txt");



    }

    //@Test
    public void _2019_splashcon_org_www_kth_se() throws IOException {
        String testPair = "2019_splashcon_org_www_kth_se";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.53.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.50.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.48.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.28.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.4.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.20.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.31.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.72.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.7.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.11.bytecode.txt.st.processed.txt");



    }

    //@Test
    public void _2019_splashcon_org_www_github_com() throws IOException {
        String testPair = "2019_splashcon_org_www_github_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.86.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.9.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.73.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.28.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.26.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.7.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.19.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.47.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.4.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.23.bytecode.txt.st.processed.txt");



    }

    //@Test
    public void _2019_splashcon_org_wikipedia_org() throws IOException {
        String testPair = "2019_splashcon_org_wikipedia_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.63.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.30.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.1.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.17.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.54.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.26.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.68.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.71.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.96.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.75.bytecode.txt.st.processed.txt");



    }

    //@Test
    public void _2019_splashcon_org_www_google_com() throws IOException {
        String testPair = "2019_splashcon_org_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.21.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.19.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.43.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.85.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.65.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.62.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.80.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.3.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/2019.splashcon.org.88.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.49.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_kth_se_www_kth_se() throws IOException {
        String testPair = "www_kth_se_www_kth_se";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.35.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.92.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.14.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.74.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.90.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.25.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.49.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.15.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.73.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.93.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_kth_se_www_github_com() throws IOException {
        String testPair = "www_kth_se_www_github_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.42.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.84.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.78.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.10.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.17.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.74.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.44.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.4.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.4.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.7.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_kth_se_wikipedia_org() throws IOException {
        String testPair = "www_kth_se_wikipedia_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.78.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.37.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.34.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.40.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.28.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.39.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.10.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.16.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.94.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_kth_se_www_google_com() throws IOException {
        String testPair = "www_kth_se_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.29.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.53.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.73.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.37.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.50.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.85.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.1.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.98.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.6.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.5.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_github_com_www_github_com() throws IOException {
        String testPair = "www_github_com_www_github_com";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.75.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.73.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.18.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.51.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.87.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.26.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.84.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.96.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.86.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_github_com_wikipedia_org() throws IOException {
        String testPair = "www_github_com_wikipedia_org";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.42.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.51.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.95.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.93.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.2.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.95.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.65.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.75.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.11.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_github_com_www_google_com() throws IOException {
        String testPair = "www_github_com_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.25.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.17.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.46.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.4.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.2.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.44.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.37.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.69.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.15.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.64.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _wikipedia_org_wikipedia_org() throws IOException {
        String testPair = "wikipedia_org_wikipedia_org";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.49.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.61.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.83.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.1.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.95.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.21.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.73.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.6.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.75.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.1.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _wikipedia_org_www_google_com() throws IOException {
        String testPair = "wikipedia_org_www_google_com";



        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.66.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.48.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.75.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.4.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.95.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.67.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.35.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.57.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 1,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/wikipedia.org.51.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.10.bytecode.txt.st.processed.txt");



    }

    @Test
    public void _www_google_com_www_google_com() throws IOException {
        String testPair = "www_google_com_www_google_com";



        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.50.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.89.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.66.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.87.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.1.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.70.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.27.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.93.bytecode.txt.st.processed.txt");




        testConvergence(testPair, 0,
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.55.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.google.com.87.bytecode.txt.st.processed.txt");



    }


}
