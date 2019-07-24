import core.ServiceRegister;
import core.TraceHelper;
import core.models.TraceMap;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
}
