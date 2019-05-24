import core.TraceHelper;
import core.models.OutputDto;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class ParsertTest {

    @Test
    public void loadSimpleTraces() throws FileNotFoundException {

        TraceHelper helper = new TraceHelper();

        helper.mapTraceSetByFileLine(Arrays.asList("/Users/javier/Documents/Develop/naenie/out/sha256.old.js/mutated11/mutation.bytecode.txt"));

    }

    @Test
    public void processMoreThanOneTrace() throws FileNotFoundException {

        TraceHelper helper = new TraceHelper();

        helper.mapTraceSetByFileLine(Arrays.asList("/Users/javier/Documents/Develop/naenie/out/sha256.old.js/mutated11/mutation.bytecode.txt", "/Users/javier/Documents/Develop/naenie/out/sha256.old.js/mutated11/original.bytecode.txt"));

    }


    @Test
    public void testSave() throws IOException {

        TraceHelper helper = new TraceHelper();

        helper.mapTraceSetByFileLine(Arrays.asList("/Users/javier/Documents/Develop/naenie/out/sha256.old.js/mutated11/mutation.bytecode.txt", "/Users/javier/Documents/Develop/naenie/out/sha256.old.js/mutated11/original.bytecode.txt"));

        helper.save("test.json");

    }


    @Test
    public void testLoad() throws IOException {

        TraceHelper.load("test.json");
    }
}