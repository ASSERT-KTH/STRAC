import core.TestLogProvider;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class FastDTWParameterTestBeyond extends FastDTWTest {


    @Test
    public void _031000() throws IOException {
        dto.method.params = new Object[]{
                31000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(31000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }


    @Test
    public void _035000() throws IOException {
        dto.method.params = new Object[]{
                35000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(35000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _040000() throws IOException {
        dto.method.params = new Object[]{
                40000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(40000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }


    @Test
    public void _045000() throws IOException {
        dto.method.params = new Object[]{
                45000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(45000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }




    @Test
    public void _060000() throws IOException {
        dto.method.params = new Object[]{
                60000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(60000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _065000() throws IOException {
        dto.method.params = new Object[]{
                65000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(65000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }





}
