import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.models.TraceMap;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;


/* @ Test scanner capacities */
public class TestScanner {


    @Test
    public void testScanner() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), ",", t -> {

            return new ByteArrayInputStream("hello, world, hello, 1, 2, 3".getBytes());
        }, false);

        Assert.assertEquals(6, map.get(0).plainTrace.size());
    }


    @Test
    public void testScannerComplexOne1() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), ", |m", t -> {

            return new ByteArrayInputStream("hello, world, hello, 1, 2, 3m".getBytes());
        }, true);

        Assert.assertEquals(6, map.get(0).plainTrace.size());
    }

    @Test
    public void testFileStream() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();



        ClassLoader classLoader = getClass().getClassLoader();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), "\r|\n", t -> {

            try {
                return new FileInputStream(classLoader.getResource("test.file").getFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, true);

        Assert.assertEquals(16, map.get(0).plainTrace.size());

        for(String sentence: map.get(0).originalSentences)
            LogProvider.info(sentence);
    }

    @Test
    public void testFileStream2() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();



        ClassLoader classLoader = getClass().getClassLoader();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), ",", t -> {

            try {
                return new FileInputStream(classLoader.getResource("test2.file").getFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, true);

        Assert.assertEquals(11, map.get(0).plainTrace.size());


        for(String sentence: map.get(0).originalSentences)
            LogProvider.info(sentence);
    }
}
