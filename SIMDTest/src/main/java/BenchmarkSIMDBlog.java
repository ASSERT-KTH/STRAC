package SIMDTest;

import org.openjdk.jmh.annotations.*;

import java.util.Random;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Javier Cabrera-Arteaga on 2020-04-23
 */
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(NANOSECONDS)
@BenchmarkMode(AverageTime)
@Fork(value = 1, jvmArgsAppend = {
        "-XX:+UseSuperWord",
        "-XX:+UnlockDiagnosticVMOptions",
        "-XX:CompileCommand=print,*BenchmarkSIMDBlog.array1"})
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class BenchmarkSIMDBlog {


    public static void main(String[] args) throws Exception{
        org.openjdk.jmh.Main.main(args);
    }

    public static final int SIZE = 1024;

    @State(org.openjdk.jmh.annotations.Scope.Thread)
    public static class Context
    {
        public final int[] values = new int[SIZE];
        public final int[] results = new int[SIZE];

        @Setup
        public void setup()
        {
            Random random = new Random();
            for (int i = 0; i < SIZE; i++) {
                values[i] = random.nextInt(Integer.MAX_VALUE / 32);
            }
        }
    }

    @Benchmark
    public int[] hashLoop(Context context)
    {
        for (int i = 0; i < SIZE; i++) {
            context.results[i] = getHashPosition(context.values[i], 1048575);
        }
        return context.results;
    }

    private static int getHashPosition(int rawHash, int mask)
    {
        rawHash ^= rawHash >>> 15;
        rawHash *= 0xed558ccd;
        rawHash ^= rawHash >>> 15;
        rawHash *= 0x1a85ec53;
        rawHash ^= rawHash >>> 15;

        return rawHash & mask;
    }
}
