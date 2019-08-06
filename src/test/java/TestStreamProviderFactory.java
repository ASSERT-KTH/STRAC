import strac.core.LogProvider;
import strac.core.stream_providers.CommandStdInputProvider;
import strac.core.stream_providers.NetworkInputProvider;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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

}
