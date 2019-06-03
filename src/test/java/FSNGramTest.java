import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemorySet;
import core.models.ComparisonDto;
import core.models.TraceMap;
import core.persistence.array.PersistentIntegerArray;
import core.utils.TimeUtils;
import ngram.Generator;
import ngram.generators.StringKeyGenerator;
import ngram.hash_keys.IHashCreator;
import ngram.interfaces.ISetComparer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static core.utils.HashingHelper.getRandomName;

public class FSNGramTest {

/*
    @Before
    public void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {
            @Override
            public  <T extends Serializable> IArray<T> allocateNewArray() {
                try {
                    return new PersistentIntegerArray("grams/" + getRandomName() + ".array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 10000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public IArray<Integer> allocateNewArray(int size) {
                try {
                    return new PersistentIntegerArray("grams/" + getRandomName() + ".array", PersistentIntegerArray.CachePolicy.SEQUENTIAL, 10000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public IArray<Integer> allocateNewArray(String id) {
                return null;
            }

            @Override
            public IArray<Integer> allocateNewArray(Integer[] items) {
                return null;
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {
                return new InMemoryDict<>();
            }

            @Override
            public IHashCreator<Integer, BigInteger[]> getHashCreator() {
                return null;
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


    @Test
    public void testNGram(){
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
        List<TraceMap> traces = helper.mapTraceSetByFileLine(files);


        TimeUtils util = new TimeUtils();
        int size = 1000;
        Generator g = new StringKeyGenerator(t -> t[0] + " " + t[1]);


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

            ISet s1 = g.getNGramSet(k, traces.get(0).trace).keySet();
            ISet s2 = g.getNGramSet(k, traces.get(1).trace).keySet();

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
    }*/
}
