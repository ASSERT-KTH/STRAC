import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.models.TraceMap;
import interpreter.dto.Alignment;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


/* @ Test scanner capacities */
public class TestScanner extends BaseResourceFileTest {


    @Test
    public void testScanner() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), ",", t -> {

            return new ByteArrayInputStream("hello, world, hello, 1, 2, 3".getBytes());
        }, false, false);

        Assert.assertEquals(6, map.get(0).plainTrace.size());
    }


    @Test
    public void testScannerComplexOne1() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), ", |m", t -> {

            return new ByteArrayInputStream("hello, world, hello, 1, 2, 3m".getBytes());
        }, true, false);

        Assert.assertEquals(6, map.get(0).plainTrace.size());
    }

    @Test
    public void testFileStream() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();



        ClassLoader classLoader = getClass().getClassLoader();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), "\r|\n", t -> getFile("test.file"), true, false);

        Assert.assertEquals(16, map.get(0).plainTrace.size());

        for(String sentence: map.get(0).originalSentences)
            LogProvider.info(sentence);
    }

    @Test
    public void testFileStream2() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), ",", t -> getFile("test2.file"), true, false);

        Assert.assertEquals(11, map.get(0).plainTrace.size());


        for(String sentence: map.get(0).originalSentences)
            LogProvider.info(sentence);
    }


    @Test
    public void testFileStreamComplement() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();

        TraceHelper helper = new TraceHelper();

        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("test"), "\\d+", t -> getFile("test.file"), true, true);

        Assert.assertEquals(16, map.get(0).plainTrace.size());


        for(String sentence: map.get(0).originalSentences)
            LogProvider.info(sentence);
    }

    @Test
    public void testBytecodeOperatorPlusOperands() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();


        TraceHelper helper = new TraceHelper();

        //                                                                               header 3717 E> 0x468d544a1e @   EOL    jump address           address
        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("bytecode.txt", "pure.txt"), "[\n\r]", new String[] {
                        "( )*\\d+ [ES]>",
                        "0x\\w+ @",
                        "\\w+ : ([0-9a-f]{2} )+( )*",
                        "\\(.*\\)",
                        "\\{.*\\}"

                }, null,
                t -> getFile(t),
                true, false);

        Assert.assertEquals(map.get(0).plainTrace.size(), map.get(1).plainTrace.size());

    }


    @Test
    public void testBytecodeOperatorOnly() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();


        TraceHelper helper = new TraceHelper();

        //                                                                               header 3717 E> 0x468d544a1e @   EOL    jump address           address
        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("bytecode.txt"), "[\r\n]",new String[]{
                        "( )*\\d+ [ES]>",
                        "0x\\w+ @",
                        "\\w+ : ",
                        "[A-Z](.*)",
                        "\\(.*\\)",
                        "\\{.*\\}",
                        "r[0-9]+[,-]?",
                        "<this>[,-]?",
                        "<closure>[,-]?",
                        "<context>[,-]?",
                        "\\[\\w+\\][,-]?",
                        "#\\w+[,-]?"
                }, null,
                t -> getFile(t),
                true, false);



    }



    @Test
    public void testTraceHelperRegexpCapabilities() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();


        TraceHelper helper = new TraceHelper();

        Alignment.Include in = new Alignment.Include();
        in.pattern="^([0-9a-f]{2}) ([0-9a-f]{2} )*";
        in.group = 1;

        //                                                                               header 3717 E> 0x468d544a1e @   EOL    jump address           address
        List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("bytecode.txt"), "[\n\r]", new String[] {
                        "( )*\\d+ [ES]>",
                        "0x\\w+ @",
                        "\\w+ : ",
                        " [A-Z](.*)"

                }, in ,
                t -> getFile(t),
                true, false);

        int count = 0;
        for(String sentence: map.get(0).originalSentences) {
            LogProvider.info(sentence);

            if(count++ > 50)
                break;
        }


    }

}
