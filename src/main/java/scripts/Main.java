package scripts;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemorySet;
import core.models.ComparisonDto;
import core.models.NGramSetDto;
import core.models.TraceMap;
import core.utils.SetHelper;
import core.utils.TimeUtils;
import interpreter.dto.Payload;
import ngram.Generator;
import ngram.generators.IdemGenerator;
import ngram.generators.StringKeyGenerator;
import ngram.generators.comparers.*;
import ngram.hash_keys.IHashCreator;
import ngram.interfaces.ISetComparer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {



            @Override
            public <T> IArray<T> allocateNewArray(String id, int size, BufferedCollection.ITypeAdaptor<T> adaptor) {
                return new InMemoryArray<T>();
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {
                return new InMemoryDict<TKey, TValue>();
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

                return new StringKeyGenerator(t -> t[0] + " " + t[1]);
            }
        });

        comparerMap.put("Overall", OverallComparer.class);
        comparerMap.put("Jaccard", JaccardComparer.class);
        comparerMap.put("Dice", DiceComparer.class);
        comparerMap.put("Min", MinComparer.class);
        comparerMap.put("Max", MaxComparer.class);
        comparerMap.put("Frequency", FrequencyComparer.class);
        comparerMap.put("Extractor", GramsExtractor.class);
    }

    public static<T> T[] subArray(T[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }

    static int size;
    static String method;
    static List<TraceMap> traces;


    static Map<String, Class<? extends Comparer>> comparerMap = new HashMap<>();





    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        setup();

        Payload payload = null;

        LogProvider.info(args);

        if(args.length > 0) {

            String payloadPath = args[0];

            payload = new Gson().

                    fromJson(new JsonReader(new FileReader(payloadPath)), Payload.class);
        }
        else{

            payload = new Gson().fromJson(new JsonReader(new InputStreamReader(System.in)), Payload.class);
        }



        if(payload.method != null) {
            method = payload.method.name;

            if (method != null && !comparerMap.containsKey(method)) {

                LogProvider.info(comparerMap.keySet());
                throw new RuntimeException("Method not allowed " + method);
            }
        }


        TraceHelper helper = new TraceHelper();

        size = payload.size;
        traces = helper.mapTraceSetByFileLine(payload.files);

        Generator generatpr = ServiceRegister.getProvider().getGenerator();
        ComparisonDto dto = new ComparisonDto(traces.size(), traces.size());


        if(payload.exportBag != null){
            LogProvider.info("Exporting bag...");

            FileWriter writer = new FileWriter(String.format("%s/%s",payload.outputDir, payload.exportBag));

            writer.write(new Gson().toJson(helper));

            writer.close();
        }

        if(payload.exportSegmentTrees){
            LogProvider.info("Saving trees");
            int i = 0;
            for(TraceMap tm: traces){
                String[] chunks = tm.traceFile.split("/");

                FileWriter writer = new FileWriter(String.format("%s/%s_%s.tree.json",payload.outputDir, i++, chunks[chunks.length - 1]));

                writer.write(new Gson().toJson(tm.trace));

                writer.close();
            }
        }



        if(payload.exportNgram != null){
            LogProvider.info("Exporting ngram...");
            for(int size: payload.exportNgram){
                int i = 0;

                for(TraceMap tm: traces){
                    try {
                        String[] chunks = tm.traceFile.split("/");

                        LogProvider.info("Exporting ", size, chunks[chunks.length - 1]);

                        FileWriter writer = new FileWriter(String.format("%s/%s.%s.gram.json", payload.outputDir, size, chunks[chunks.length - 1]));

                        IDict dict = generatpr.getNGramSet(size, tm.trace);

                        NGramSetDto ngramOutDto = new NGramSetDto();
                        ngramOutDto.set = dict;
                        ngramOutDto.bagPath = String.format("%s/%s", payload.outputDir, payload.exportBag);
                        ngramOutDto.keyCount = dict.size();
                        ngramOutDto.sentenceCount = tm.trace.getSize();
                        ngramOutDto.n = size;
                        ngramOutDto.path = tm.traceFile;

                        writer.write(new Gson().toJson(ngramOutDto));

                        writer.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        if(payload.method != null) {
            // Generator g = new StringKeyGenerator(t -> String.format("%s %s", t[0], t[1]));

            Comparer cmp = comparerMap.get(payload.method.name).getDeclaredConstructor().newInstance();

            new StringKeyGenerator(t -> t[0] + " " + t[1]);

            for (int i = 0; i < traces.size(); i++) {

                if (payload.printComparisson)
                    System.out.print(traces.get(i).traceFile + " ");

                for (int j = i + 1; j < traces.size(); j++) {


                    cmp.setTraces(traces.get(i), traces.get(j));

                    try {
                        double distance = reflectExecution(cmp, payload.method.params);

                        if (payload.printComparisson)
                            System.out.print(distance + " ");

                        dto.set(i, j, distance);
                        dto.set(j, i, distance);
                        dto.set(i, i, 0);
                    }catch(Exception  e){
                        if (payload.printComparisson)
                            System.out.print("unreachable");

                    }
                }

                if (payload.printComparisson)
                    System.out.println();
            }
        }

        if(payload.method != null && payload.exportComparisson != null){
            FileWriter writer = new FileWriter(payload.exportComparisson);

            writer.write(new Gson().toJson(dto));

            writer.close();
        }



        //Scanner sc = new Scanner(System.in);


        /*while(true){
            System.out.print("> ");
            String command = sc.nextLine();

            System.out.println(command);
        }*/

    }

    public static <T> T reflectExecution(Comparer target, Object[] params) throws InvocationTargetException, IllegalAccessException {

        Method info = Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(t -> t.getName().equals("compare"))
                .findFirst().get();


        return (T)info.invoke(target, params);
    }

    public static void parseCommand(String command){

    }
}
