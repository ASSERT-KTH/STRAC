import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.LinearMemoryDTW;
import com.google.gson.Gson;
import core.IServiceProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.MultiDimensionalCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemoryMultidimensional;
import core.data_structures.memory.InMemorySet;
import core.models.TraceMap;
import core.utils.HashingHelper;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import ngram.Generator;
import ngram.generators.StringKeyGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static core.utils.HashingHelper.getRandomName;

public class OutputForExternalTest {

    @Before
    public void setup(){

        TestLogProvider.info("Start test session", new Date());

        ServiceRegister.registerProvider(new IServiceProvider() {


            @Override
            public <T> IArray<T> allocateNewArray(String id, long size, BufferedCollection.ITypeAdaptor<T> adaptor) {
                IArray<T> result = new InMemoryArray<T>(id==null? getRandomName(): id, (int)size);


                return result;
                //return new InMemoryArray<T>(getRandomName(), (int)size);
            }

            @Override
            public <T> IMultidimensionalArray<T> allocateMuldimensionalArray(BufferedCollection.ITypeAdaptor<T> adaptor, int... dimensions) {
                IMultidimensionalArray<T> result = new InMemoryMultidimensional<T>(adaptor, dimensions[0], dimensions[1]);

                return result;

                //return new InMemoryMultidimensional<>(adaptor, dimensions[0], dimensions[1]);
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {



                return new InMemoryDict<TKey, TValue>();
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return new InMemorySet<>(new HashSet<>());
            }



            @Override
            public Generator getGenerator() {

                return new StringKeyGenerator(t -> t.stream().map(String::valueOf).collect(Collectors.joining(","))
                        , HashingHelper::hashList);
            }
        });

    }

    @Test
    public void exportR_DTW() throws IOException {

        Alignment dto = new Alignment();

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.github.com.10.bytecode.txt.st.processed.txt"
        );


        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, false, false);


        for(TraceMap tr: traces){
            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/R_DTW/%s.json", tr.traceFileName));

            wr.write(new Gson().toJson(tr.plainTrace));
            wr.close();
        }
    }

    @Test
    public void exportCSV() throws IOException {

        Alignment dto = new Alignment();

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.github.com.10.bytecode.txt.st.processed.txt"
        );


        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, false, false);

        for(TraceMap tr: traces){

            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/R_DTW/trace_%s.csv", tr.traceFileName));

            for(Integer i : tr.plainTrace) {
                wr.write(String.format("%s\n", String.valueOf((i))));
            }

            wr.close();
        }
    }
}
