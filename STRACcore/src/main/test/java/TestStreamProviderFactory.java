import strac.core.LogProvider;
import strac.core.StreamProviderFactory;
import strac.core.TraceHelper;
import strac.core.dto.FileContentDto;
import strac.core.models.TraceMap;
import strac.core.stream_providers.CommandStdInputProvider;
import strac.core.stream_providers.NetworkInputProvider;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class TestStreamProviderFactory {


    @Test
    public void testNetworkProvider(){
        NetworkInputProvider p = new NetworkInputProvider();

        Assert.assertEquals(p.validate("http://www.google.com"), true);
        Assert.assertEquals(p.validate("https://www.google.com"), true);
    }



    @Test
    public void testNetworkProvider2() throws IOException {
        NetworkInputProvider p = new NetworkInputProvider();

        InputStream str = p.getStream("http://www.google.com");

        LogProvider.info(new String(str.readAllBytes()));
    }


    @Test
    public void testCommnadStd() throws IOException {
        CommandStdInputProvider p = new CommandStdInputProvider();

        InputStream str = p.getStream("cat pom.xml");

        LogProvider.info(new String(str.readAllBytes()));
    }



    @Test
    public void testCommand3() throws IOException {
        CommandStdInputProvider p = new CommandStdInputProvider();

        InputStream str = p.getStream("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  http://www.google.com");

        LogProvider.info(new String(str.readAllBytes()));
    }


    @Test
    public void testProvider() throws IOException {
        StreamProviderFactory p = StreamProviderFactory.getInstance();


        TraceHelper h = new TraceHelper();


        List<TraceMap> map = h.mapTraceSetByFileLine(Arrays.asList("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  http://www.google.com"),
                "[\r\n]", new String[] {
                        "( )*\\d+ [ES]>",
                        "0x\\w+ @",
                        "\\w+ : ",
                        " [A-Z](.*)"
                }, null, p, true, false);

        FileWriter wr = new FileWriter("testout1.txt");

        for(String s: map.get(0).originalSentences) {
            wr.write(s + "\n");
        }

        wr.close();
    }



    @Test
    public void testProvider2() throws IOException {
        StreamProviderFactory p = StreamProviderFactory.getInstance();


        TraceHelper h = new TraceHelper();

        FileContentDto.Include inc = new FileContentDto.Include();
        inc.pattern="^([0-9a-f]{2})";
        inc.group = 0;

        List<TraceMap> map = h.mapTraceSetByFileLine(Arrays.asList("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  http://www.google.com"),
                "[\r\n]", new String[] {
                        "( )*\\d+ [ES]>",
                        "0x\\w+ @",
                        "\\w+ : ",
                        " [A-Z](.*)"
                }, inc, p, true, false);

        FileWriter wr = new FileWriter("testout.txt");

        for(String s: map.get(0).originalSentences) {
            wr.write(s + "\n");
        }

        wr.close();
    }

}
