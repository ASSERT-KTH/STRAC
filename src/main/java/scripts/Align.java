package scripts;

import align.AlignDistance;
import align.Aligner;
import align.implementations.DWT;
import align.implementations.FastDWT;
import align.implementations.IImplementationInfo;
import align.implementations.WindowedDWT;
import com.google.gson.Gson;
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
import core.models.TraceMap;
import interpreter.dto.Alignment;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Align {

    public static void setup(){
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
            public IArray<Integer> allocateNewArray(String id) {
                return new InMemoryArray();
            }

            @Override
            public IArray<Integer> allocateNewArray(Integer[] items) {
                return new InMemoryArray(items);
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {
                return new InMemoryDict<TKey, TValue>();
            }

            @Override
            public <T, R> IHashCreator<T, R> getHashCreator() {
                return null;
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return null;
            }

            @Override
            public Generator getGenerator() {
                return null;
            }
        });

        comparers = new HashMap<>();
        comparers.put("DTW", (objs) -> new DWT((x, y) -> x == y? 2: -1));
        comparers.put("FastDTW", (objs) -> new FastDWT(((Double)objs[0]).intValue(), (x, y) -> x == y? 2: -1));
    }

    static Map<String, IImplementationInfo> comparers;

    public static void main(String[] args) throws IOException {

        setup();

        Alignment dto = new Gson().fromJson(new FileReader(args[0]), Alignment.class);

        TraceHelper helper = new TraceHelper();

        if(!comparers.containsKey(dto.method.name)){
            LogProvider.info(comparers.keySet());
            throw  new RuntimeException("Method nos allowed");
        }

        LogProvider.info("Parsing traces");

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, false);
        Aligner align = comparers.get(dto.method.name).getAligner(dto.method.params);

        for(int[] pair: dto.pairs){
            TraceMap tr1 = traces.get(pair[0]);
            TraceMap tr2 = traces.get(pair[1]);

            AlignDistance distance = align.align(tr1.plainTrace, tr2.plainTrace);

            LogProvider.info("Distance",  distance.getDistance());
            LogProvider.info("Path", distance.getInsertions());

            if(dto.outputData){
                FileWriter writer = new FileWriter(String.format("%s_%s.align.json", tr1.traceFileName, tr2.traceFileName));
                writer.write(new Gson().toJson(distance));
                writer.close();
            }
        }
    }

}
