import com.google.gson.Gson;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemorySet;
import core.models.ComparisonDto;
import core.models.TraceMap;
import core.utils.SetHelper;
import core.utils.TimeUtils;
import ngram.Generator;
import ngram.generators.StringKeyGenerator;
import ngram.generators.IdemGenerator;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.interfaces.ISetComparer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class NGramStatTest {

    public List<TraceMap> traces;

    @Before
    public void setProvider(){
        ServiceRegister.registerProvider(new IServiceProvider() {
            @Override
            public IArray<Integer> allocateNewArray() {
                return new InMemoryArray();
            }

            @Override
            public IArray<Integer> allocateNewArray(int size) {
                return new InMemoryArray(size);
            }

            @Override
            public IArray<Integer> allocateNewArray(Integer[] items) {
                return new InMemoryArray(items);
            }

            @Override
            public IHashCreator<Integer, BigInteger[]> getHashCreator() {
                return new IHashCreator<Integer, BigInteger[]>() {
                    @Override
                    public BigInteger[] getHash(BigInteger[] left, BigInteger[] right) {


                        BigInteger prime1 = new BigInteger(String.valueOf(1000000000 + 7));
                        BigInteger prime2 = new BigInteger(String.valueOf(100002593));

                        // These modules for example (also primes)
                        BigInteger module1 = new BigInteger(String.valueOf(1011013823));
                        BigInteger module2 = new BigInteger(String.valueOf(1011013823));

                        BigInteger hash1 = new BigInteger("0");
                        BigInteger hash2 = new BigInteger("0");


                        for(BigInteger val: left){
                            hash1 = hash1.multiply(prime1).add(val).mod(module1);
                            hash2 = hash2.multiply(prime2).add(val).mod(module2);
                        }

                        for(BigInteger val: right){
                            hash1 = hash1.multiply(prime1).add(val).mod(module1);
                            hash2 = hash2.multiply(prime2).add(val).mod(module2);
                        }


                        return new BigInteger[]{hash1, hash2};
                    }

                    @Override
                    public BigInteger[] getHash(Integer left) {
                        return new BigInteger[] {
                                new BigInteger(String.valueOf(left)),
                                new BigInteger(String.valueOf(left))
                        };
                    }
                };
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return new InMemorySet<>(new HashSet<>());
            }

            @Override
            public Generator getGenerator() {
                return null;
            }
        });
    }

    public void setup(){

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sha256.old.js").getFile());

        String testFolder = file.getAbsolutePath(); //;
        LogProvider.info(testFolder);

        TraceHelper helper = new TraceHelper();
        List<String> files = new ArrayList<>();
        boolean originalFound = false;

        for (File d: new File(testFolder).listFiles()
        ) {

            if(d.isDirectory()){
                // mutation folder name

                LogProvider.info("Processing folder", d.getName());

                Optional<String> original = Arrays.stream(d.list())
                        .filter(f -> f.endsWith("original.bytecode.txt"))
                        .map(f -> String.format("%s/%s", d, f))
                        .findFirst();

                if(!originalFound) {
                    files.add(original.get());
                    originalFound = true;
                }

                Optional<String> mutation = Arrays.stream(d.list())
                        .filter(f -> f.endsWith("mutation.bytecode.txt"))
                        .map(f -> String.format("%s/%s", d, f))
                        .findFirst();

                files.add(mutation.get());

            }

        }

        LogProvider.info("Mapping and getting traces...");
        traces = helper.mapTraceSetByFileLine(files);
    }

    @Test
    public void testOneMutationCurveIdemGenerator(){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sha256.old.js/mutated0").getFile());

        String testFolder = file.getAbsolutePath(); //;
        LogProvider.info(testFolder);

        TraceHelper helper = new TraceHelper();
        List<String> files = new ArrayList<>();

        for (File d: new File(testFolder).listFiles()
        ) {

            // mutation folder name

            LogProvider.info("Processing folder", d.getName());

            if(d.getAbsolutePath().endsWith("original.bytecode.txt")) {
                files.add(d.getAbsolutePath());
            }


            if(d.getAbsolutePath().endsWith("mutation.bytecode.txt")) {
                files.add(d.getAbsolutePath());
            }


        }

        LogProvider.info("Mapping and getting traces...");
        traces = helper.mapTraceSetByFileLine(files);


        TimeUtils util = new TimeUtils();
        int size = 6;
        Generator g = new IdemGenerator(t -> t[0].intValue());


        LogProvider.info("Traces count", traces.size() + "");

        List<ISetComparer> comparers = Arrays.asList(
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                        return 1 - s1.intersect(s2).size()*1.0/s1.union(s2).size();
                    }

                    @Override
                    public String getName() {
                        return "Union";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                        return s1.intersect(s2).size()*1.0/Math.min(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Min";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                        return 1 - s1.intersect(s2).size()*1.0/Math.max(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Max";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                        return 1 - s1.intersect(s2).size()*1.0/(s1.size() + s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Sum";
                    }
                }
        );

        for(int k = size; k <= size; k++) {

            LogProvider.info("Calculating distance", "size", "" + k, "trace 1",
                    traces.get(0).traceFile, "trace 2", traces.get(1).traceFile);

            LogProvider.info("Generating ngran");
            util.reset();

            LogProvider.info("Traces info", "s1: " + traces.get(0).trace.getSize(), " s2: " + traces.get(1).trace.getSize());

            ISet s1 = g.getNGramSet(k, traces.get(0).trace);
            ISet s2 = g.getNGramSet(k, traces.get(1).trace);

            LogProvider.info("Sets info", "s1: " + s1.size(), " s2: " + s2.size());

            util.time();

            for (ISetComparer comparer : comparers) {

                ComparisonDto comparissonDto = new ComparisonDto(traces.size());


                // min comparison
                LogProvider.info("Comparing ngram sets");
                util.reset();

                double distance = comparer.getDistance(s1, s2);
                util.time();

                comparissonDto.set(0, 1, distance);
                comparissonDto.set(1, 0, distance);


                LogProvider.info(comparer.getName(), " " + distance);

            }

        }

    }

    @Test
    public void testOneMutationCurveHashingGenerator(){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sha256.old.js/mutated11").getFile());

        String testFolder = file.getAbsolutePath(); //;
        LogProvider.info(testFolder);

        TraceHelper helper = new TraceHelper();
        List<String> files = new ArrayList<>();

        for (File d: new File(testFolder).listFiles()
        ) {

                // mutation folder name

            LogProvider.info("Processing folder", d.getName());

            if(d.getAbsolutePath().endsWith("original.bytecode.txt")) {
                files.add(d.getAbsolutePath());
            }


            if(d.getAbsolutePath().endsWith("mutation.bytecode.txt")) {
                files.add(d.getAbsolutePath());
            }


        }

        LogProvider.info("Mapping and getting traces...");
        traces = helper.mapTraceSetByFileLine(files);


        TimeUtils util = new TimeUtils();
        int size = 3;
        Generator g = new StringKeyGenerator(t -> t[0] + " " + t[1]);


        LogProvider.info("Traces count", traces.size() + "");

        List<ISetComparer> comparers = Arrays.asList(

                new ISetComparer() {
                    @Override
                    public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                        return 1 - s1.intersect(s2).size()*1.0/Math.min(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Min";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                        return 1 - s1.intersect(s2).size()*1.0/Math.max(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Max";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                        return 1 - s1.intersect(s2).size()*1.0/(s1.size() + s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Sum";
                    }
                }
        );

            for(int k = size; k <= size; k++) {

                LogProvider.info("Calculating distance", "size", "" + k, "trace 1",
                        traces.get(0).traceFile, "trace 2", traces.get(1).traceFile);

                LogProvider.info("Generating ngran");
                util.reset();

                LogProvider.info("Traces info", "s1: " + traces.get(0).trace.getSize(), " s2: " + traces.get(1).trace.getSize());

                ISet s1 = g.getNGramSet(k, traces.get(0).trace);
                ISet s2 = g.getNGramSet(k, traces.get(1).trace);

                LogProvider.info("Sets info", "s1: " + s1.size(), " s2: " + s2.size());

                util.time();

                for (ISetComparer comparer : comparers) {

                    ComparisonDto comparissonDto = new ComparisonDto(traces.size());


                    // min comparison
                    LogProvider.info("Comparing ngram sets");
                    util.reset();

                    double distance = comparer.getDistance(s1, s2);
                    util.time();

                    comparissonDto.set(0, 1, distance);
                    comparissonDto.set(1, 0, distance);


                    LogProvider.info(comparer.getName(), " " + distance);

                }

            }

    }

    @Test
    public void testStressHasing(){

        TraceMap trace1 =
                new TraceMap(ServiceRegister.getProvider().allocateNewArray(generateRandomIntegers(180000)), "test.1");


        TraceMap trace2 =
                new TraceMap(ServiceRegister.getProvider().allocateNewArray(generateRandomIntegers(180000)), "test.2");



        int size = 10000;

        Generator g = new StringKeyGenerator(t -> t[0] + " " + t[1]);

        TimeUtils util = new TimeUtils();


        LogProvider.info("Calculating distance", "size", "" + size, "trace 1",
                trace1.traceFile, "trace 2", trace2.traceFile);

        LogProvider.info("Generating ngran");
        util.reset();

        ISet s1 = g.getNGramSet(size, trace1.trace);
        ISet s2 = g.getNGramSet(size, trace2.trace);

        ISetComparer comparer = new ISetComparer() {
            @Override
            public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                return 1 - s1.intersect(s2).size()*1.0/s1.union(s2).size();
            }

            @Override
            public String getName() {
                return "Union";
            }
        };

        LogProvider.info("Sets info", "s1: " + s1.size(), " s2: " + s2.size());

        util.time();




        // min comparison
        LogProvider.info("Comparing ngram sets");
        util.reset();

        double distance = comparer.getDistance(s1, s2);
        util.time();

        LogProvider.info(comparer.getName(), " " + distance);


    }

    @Test
    public void measureOriginals() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sha256.old.js").getFile());

        String testFolder = file.getAbsolutePath(); //;
        LogProvider.info(testFolder);

        TraceHelper helper = new TraceHelper();
        List<String> files = new ArrayList<>();

        for (File d: new File(testFolder).listFiles()
        ) {

            // mutation folder name

            LogProvider.info("Processing folder", d.getName());

            if(d.isDirectory())
            for(File inner: d.listFiles()){
                if(inner.getAbsolutePath().endsWith("original.bytecode.txt")) {
                    files.add(inner.getAbsolutePath());
                }
            }
        }

        Generator g = new StringKeyGenerator(t -> t[0] + " " + t[1]);

        TimeUtils util = new TimeUtils();


        LogProvider.info("Mapping and getting traces...");
        traces = helper.mapTraceSetByFileLine(files);

        LogProvider.info("Trees had been created");

        ISetComparer comparer = new ISetComparer() {
            @Override
            public <T> double getDistance(ISet<T> s1, ISet<T> s2) {
                return 1 - s1.intersect(s2).size()*1.0/s1.union(s2).size();
            }

            @Override
            public String getName() {
                return "Union";
            }
        };

        ComparisonDto dto = new ComparisonDto(100, traces.size() - 1);

        for(int i  = 1; i <= 100; i++) {


            int k = 0;

            for (int j  = k + 1; j < traces.size(); j++) {

                TraceMap tr = traces.get(k);
                TraceMap tr2 = traces.get(j);

                ISet s1 = g.getNGramSet(i, tr.trace);
                ISet s2 = g.getNGramSet(i, tr2.trace);

                double distance = comparer.getDistance(s1, s2);

                dto.set(i, j - 1, distance);
            }



            new FileOutputStream(String.format("reports/originals_%s_%s.json", i, comparer.getName())).write(
                    new Gson().toJson(dto).getBytes());
        }

    }


    Random r = new Random();


    public Integer[] generateRandomIntegers(int size){

        Integer[] result = new Integer[size];

        for(int i = 0; i < size; i++)
            result[i] = r.nextInt(30000);

        return result;
    }
}
