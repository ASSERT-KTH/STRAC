import core.TestLogProvider;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class FastDTWParameterTest extends FastDTWTest {





    @Test
    public void _000000() throws IOException {
        dto.method.params = new Object[]{
                0.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(0, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000001() throws IOException {
        dto.method.params = new Object[]{
                1.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(1, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000002() throws IOException {
        dto.method.params = new Object[]{
                2.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(2, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000003() throws IOException {
        dto.method.params = new Object[]{
                3.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(3, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000004() throws IOException {
        dto.method.params = new Object[]{
                4.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(4, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000010() throws IOException {
        dto.method.params = new Object[]{
                10.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(10, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000020() throws IOException {
        dto.method.params = new Object[]{
                20.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(20, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000030() throws IOException {
        dto.method.params = new Object[]{
                30.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(30, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000050() throws IOException {
        dto.method.params = new Object[]{
                50.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(50, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000070() throws IOException {
        dto.method.params = new Object[]{
                70.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(70, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000100() throws IOException {
        dto.method.params = new Object[]{
                100.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(100, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000200() throws IOException {
        dto.method.params = new Object[]{
                200.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(200, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000300() throws IOException {
        dto.method.params = new Object[]{
                300.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(300, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _000400() throws IOException {
        dto.method.params = new Object[]{
                400.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(400, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _001000() throws IOException {
        dto.method.params = new Object[]{
                1000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(1000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _001500() throws IOException {
        dto.method.params = new Object[]{
                1500.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(1500, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _002000() throws IOException {
        dto.method.params = new Object[]{
                2000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(2000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _003000() throws IOException {
        dto.method.params = new Object[]{
                3000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(3000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _003500() throws IOException {
        dto.method.params = new Object[]{
                3500.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(3500, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _004000() throws IOException {
        dto.method.params = new Object[]{
                4000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(4000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _004500() throws IOException {
        dto.method.params = new Object[]{
                4500.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(4500, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _005000() throws IOException {
        dto.method.params = new Object[]{
                5000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(5000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _006000() throws IOException {
        dto.method.params = new Object[]{
                6000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(6000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _006100() throws IOException {
        dto.method.params = new Object[]{
                6100.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(6100, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _006400() throws IOException {
        dto.method.params = new Object[]{
                6400.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(6400, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _008000() throws IOException {
        dto.method.params = new Object[]{
                8000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(8000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _010000() throws IOException {
        dto.method.params = new Object[]{
                10000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(10000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _020000() throws IOException {
        dto.method.params = new Object[]{
                20000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(20000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



    @Test
    public void _030000() throws IOException {
        dto.method.params = new Object[]{
                30000.0
        };

        AtomicLong now = new AtomicLong(System.nanoTime());

        interpreter.execute(dto, (distance, tr1, tr2, al1, al2, total) -> {
            TestLogProvider.info(30000, ",", distance.getDistance(), ",", total, ",", System.nanoTime() - now.get(), ",");
            now.set(System.nanoTime());
        });
    }



}
