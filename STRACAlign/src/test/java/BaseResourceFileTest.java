
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import strac.core.TraceHelper;
import strac.core.models.TraceMap;
import strac.core.utils.ServiceRegister;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class BaseResourceFileTest {


    @Before
    public void setup() throws IOException, ClassNotFoundException {
        ServiceRegister.getInstance();
    }


    @After
    public void clean() throws IOException, ClassNotFoundException {
        System.gc();
    }

    protected FileInputStream getFile(String name){

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return new FileInputStream(classLoader.getResource(name).getFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* @ Test scanner capacities */
    public static class TestScanner extends BaseResourceFileTest {



        @Test
        public void testBytecodeOperatorPlusOperands() throws IOException, ClassNotFoundException {

            ServiceRegister.getInstance();


            TraceHelper helper = new TraceHelper();

            //                                                                               header 3717 E> 0x468d544a1e @   EOL    jump address           address
            List<TraceMap> map = helper.mapTraceSetByFileLine(Arrays.asList("bytecode.txt", "pure.txt"), "[\n\r]", new String[]{
                            "( )*\\d+ [ES]>",
                            "0x\\w+ @",
                            "\\w+ : ([0-9a-f]{2} )+( )*",
                            "\\(.*\\)",
                            "\\{.*\\}"

                    }, null, new TraceHelper.IStreamProvider() {
                        @Override
                        public InputStream getStream(String filename) {
                            return getFile(filename);
                        }

                        @Override
                        public boolean validate(String filename) {
                            return true;
                        }
                    },
                    true, false);

            Assert.assertEquals(map.get(0).plainTrace.length, map.get(1).plainTrace.length);

        }


        @Test
        public void testBytecodeOperatorOnly() throws IOException, ClassNotFoundException {

            ServiceRegister.getInstance();


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
                    }, null, new TraceHelper.IStreamProvider() {
                        @Override
                        public InputStream getStream(String filename) {
                            return getFile(filename);
                        }

                        @Override
                        public boolean validate(String filename) {
                            return true;
                        }
                    },
                    true, false);



        }



    }
}
