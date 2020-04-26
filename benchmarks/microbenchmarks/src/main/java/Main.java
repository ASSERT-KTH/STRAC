package microbenchmarks;

import org.openjdk.jmh.annotations.*;
import strac.align.align.AlignDistance;
import strac.align.interpreter.AlignInterpreter;
import strac.align.interpreter.dto.Alignment;
import strac.align.interpreter.dto.Payload;
import strac.align.utils.AlignServiceProvider;
import strac.core.StreamProviderFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
        "-XX:CompileCommand=print,*Main.compare*"})
@Warmup(iterations = 10)
@Measurement(iterations = 50)
public class Main {

    @State(org.openjdk.jmh.annotations.Scope.Thread)
    public static class Context {

        Alignment dto = new Alignment();
        @Setup
        public void init() throws IOException, ClassNotFoundException {
            AlignServiceProvider.setup();
            AlignServiceProvider.getInstance().getProvider();

            dto.distanceFunctionName = "dBin";
            dto.outputAlignment = true;
            dto.pairs = new ArrayList();
            dto.method = new Payload.MethodInfo();
            dto.threadPoolCount = 10;

            dto.method.params = Arrays.asList();
            dto.outputDir = "outDemo";
            dto.exportImage = true;
            dto.separator = "[\r\n]";
            dto.outputAlignmentMap = "map.json";
            dto.clean = new String[]{
                    "^( )*\\d+ [ES]>",
                    "0x\\w+ @",
                    "\\w+ : ",
                    " [A-Z](.*)"
            };

            String f1 = "benchmarks/resources/t1.txt";
            String f2 = "benchmarks/resources/t2.txt";
            //val f2 = "/Users/javier/IdeaProjects/STRAC/scripts/chrome_scripts/tiny_test/ten/wiki-9/w6.txt"
            //TestLogProvider.info("#%s".format(site))
            //for(site2 in sites) {

            dto.files = Arrays.asList(f1, f2);
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
    public void compareSIMD(Context context) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {


        AlignInterpreter interpreter = new AlignInterpreter();
        context.dto.method.name = "SIMD";

        interpreter.execute(context.dto, new AlignInterpreter.IOnAlign() {
            @Override
            public void action(AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize) {
                //sSystem.out.println(String.format("%s", distance.getDistance()));
            }
        }, StreamProviderFactory.getInstance());

        AlignServiceProvider.getInstance().getAllocator().dispose();

    }

    @Benchmark
    public void comparePureDTW(Context context) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {


        AlignInterpreter interpreter = new AlignInterpreter();
        context.dto.method.name = "PureDTW";

        interpreter.execute(context.dto, new AlignInterpreter.IOnAlign() {
            @Override
            public void action(AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize) {
                //sSystem.out.println(String.format("%s", distance.getDistance()));
            }
        }, StreamProviderFactory.getInstance());

        AlignServiceProvider.getInstance().getAllocator().dispose();

    }
}
