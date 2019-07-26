import core.ServiceRegister;
import org.junit.After;
import org.junit.Before;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BaseResourceFileTest {


    @Before
    public void setup() throws IOException, ClassNotFoundException {
        ServiceRegister.setup();
        ServiceRegister.getProvider();
    }


    @After
    public void clean() throws IOException, ClassNotFoundException {
        ServiceRegister.dispose();
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

}
