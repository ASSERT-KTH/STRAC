package microbenchmarks;

import org.openjdk.jmh.annotations.*;
import strac.align.align.AlignDistance;
import strac.align.align.event_distance.DInst;
import strac.align.align.implementations.DTW;
import strac.align.align.implementations.FastDTW;
import strac.align.align.implementations.NoWarpPathDTW;
import strac.align.align.implementations.SIMDDTW;
import strac.align.interpreter.AlignInterpreter;
import strac.align.interpreter.dto.Alignment;
import strac.align.interpreter.dto.Payload;
import strac.align.utils.AlignServiceProvider;
import strac.core.StreamProviderFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Javier Cabrera-Arteaga on 2020-04-23
 */
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(MILLISECONDS)
@BenchmarkMode(AverageTime)
@Fork(value = 1, jvmArgsAppend = {
        "-XX:+UseSuperWord",
        "-XX:+UnlockDiagnosticVMOptions",
        "-XX:CompileCommand=print,*Main.compare*"})
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class Main {

    @State(org.openjdk.jmh.annotations.Scope.Thread)
    public static class Context {

        @Param({ "100", "200"})
        public int size;

        @Param({ "100", "200"})
        public int size2;

        public int MAX  = 50;
        public Random r = new Random();

        int[] trace1;
        int[] trace2;

        int[] createRandomArray(int size){
            int[] result = new int[size];

            for(int i= 0; i < size; i++)
                result[i] = 1 + r.nextInt(MAX);

            return result;
        }

        @Setup
        public void init(){
            trace1 = createRandomArray(size);
            trace2 = createRandomArray(size2);
        }




    }

    static String getFile(String name){
        ClassLoader classLoader = Context.class.getClassLoader();

        return classLoader.getResource(name).getFile();
    }
    public static void main(String[] args) throws Exception{
        org.openjdk.jmh.Main.main(args);
    }



    @Benchmark
    @BenchmarkMode(AverageTime)
    public void compareFastDTW(Context context){

        FastDTW fdtw = new FastDTW(new DInst(), 2000.0);
        fdtw.align(context.trace1, context.trace2);
    }

    @Benchmark
    @BenchmarkMode(AverageTime)
    public void compareDTWWithWarpPath(Context context){

        DTW dtw = new DTW(new DInst());
        dtw.align(context.trace1, context.trace2);
    }

    @Benchmark
    @BenchmarkMode(AverageTime)
    public  void compareSIMD(Context context){
        SIMDDTW simdtw = new SIMDDTW(new DInst());
        simdtw.align(context.trace1, context.trace2);
    }

    @Benchmark
    @BenchmarkMode(AverageTime)
    public void comparePureDTW(Context context){
        NoWarpPathDTW ndtw = new NoWarpPathDTW(new DInst());
        ndtw.align(context.trace1, context.trace2);
    }
}
