package scripts;

import core.LogProvider;
import core.TraceHelper;
import core.models.ComparisonDto;
import core.models.TraceMap;
import core.utils.JsonHelper;
import core.utils.SetHelper;
import core.utils.TimeUtils;
import ngram.Generator;
import ngram.generators.IdemGenerator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String testFolder ="/Users/javier/Documents/Develop/naenie/out/sha256.old.js"; //;

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
        List<TraceMap> traces = helper.mapTraceSetByFileLine(files);


        TimeUtils util = new TimeUtils();
        int size = 20000;
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

                        util.time();

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

                JsonHelper.save(String.format("comparisson_%s_%s.json", "union", k + ""), comparissonDto);
            }

    }
}
