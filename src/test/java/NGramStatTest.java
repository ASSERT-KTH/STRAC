import core.LogProvider;
import core.TraceHelper;
import core.models.ComparisonDto;
import core.models.TraceMap;
import core.utils.JsonHelper;
import core.utils.SetHelper;
import core.utils.TimeUtils;
import ngram.Generator;
import ngram.generators.HashCompressinGenerator;
import ngram.generators.IdemGenerator;
import ngram.interfaces.ISetComparer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class NGramStatTest {

    public List<TraceMap> traces;


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
    public void testNGramStatAllVsAll() throws IOException {


        setup();

        TimeUtils util = new TimeUtils();
        int size = 50;
        Generator g = new IdemGenerator();


        LogProvider.info("Traces count", traces.size() + "");

        for(int k = 1; k < size; k++){

            ComparisonDto comparissonDto = new ComparisonDto(traces.size());

            for(int i = 0; i < traces.size(); i++){
                for(int j = i + 1; j < traces.size(); j++){


                    LogProvider.info("Calculating distance", "size", "" + k, "trace 1", traces.get(i).traceFile, "trace 2", traces.get(j).traceFile);

                    LogProvider.info("Generating ngran");
                    util.reset();

                    Set s1 = g.getNGramSet(k, traces.get(i).trace);
                    Set s2 = g.getNGramSet(k, traces.get(j).trace);

                    LogProvider.info("Sets info", "s1: " + s1.size(),  " s2: " + s2.size());

                    util.time();



                    // min comparison
                    LogProvider.info("Comparing ngram sets");
                    util.time();

                    double distance = SetHelper.intersection(s1, s2).size()*1.0/SetHelper.union(s1, s2).size();

                    comparissonDto.set(i, j, distance);
                    comparissonDto.set(j, i, distance);


                }
            }


            // Saving
            LogProvider.info("Saving");
            util.time();

            //JsonHelper.save(String.format("comparisson_%s_%s.json", "union", k + ""), comparissonDto);
        }
    }

    @Test
    public void testOneMutationCurveIdemGenerator(){
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
        int size = 20000;
        Generator g = new IdemGenerator();


        LogProvider.info("Traces count", traces.size() + "");

        List<ISetComparer> comparers = Arrays.asList(
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/SetHelper.union(s1, s2).size();
                    }

                    @Override
                    public String getName() {
                        return "Union";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/Math.min(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Min";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/Math.max(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Max";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/(s1.size() + s2.size());
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

            Set s1 = g.getNGramSet(k, traces.get(0).trace);
            Set s2 = g.getNGramSet(k, traces.get(1).trace);

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
        Generator g = new HashCompressinGenerator();


        LogProvider.info("Traces count", traces.size() + "");

        List<ISetComparer> comparers = Arrays.asList(
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/SetHelper.union(s1, s2).size();
                    }

                    @Override
                    public String getName() {
                        return "Union";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/Math.min(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Min";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/Math.max(s1.size(), s2.size());
                    }

                    @Override
                    public String getName() {
                        return "Max";
                    }
                },
                new ISetComparer() {
                    @Override
                    public <T> double getDistance(Set<T> s1, Set<T> s2) {
                        return 1 - SetHelper.intersection(s1, s2).size()*1.0/(s1.size() + s2.size());
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

                Set s1 = g.getNGramSet(k, traces.get(0).trace);
                Set s2 = g.getNGramSet(k, traces.get(1).trace);

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

}
