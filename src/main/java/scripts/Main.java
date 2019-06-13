package scripts;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.*;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemorySet;
import core.models.ComparisonDto;
import core.models.NGramSetDto;
import core.models.TraceMap;
import core.utils.HashingHelper;
import core.utils.SetHelper;
import core.utils.TimeUtils;
import interpreter.NGramsInterpreter;
import interpreter.dto.Payload;
import ngram.Generator;
import ngram.generators.IdemGenerator;
import ngram.generators.StringKeyGenerator;
import ngram.generators.comparers.*;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.IIHashSetKeyCreator;
import ngram.interfaces.ISetComparer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static core.utils.HashingHelper.getRandomName;

public class Main {

    public static void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {

            @Override
            public <T> IArray<T> allocateNewArray(String id, long size, BufferedCollection.ITypeAdaptor<T> adaptor) {
                return new InMemoryArray<T>(getRandomName(), (int)size);
            }

            @Override
            public <T> IMultidimensionalArray<T> allocateMuldimensionalArray(BufferedCollection.ITypeAdaptor<T> adaptor, int... dimensions) {
                return null;
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


    public static<T> T[] subArray(T[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }

    static int size;
    static List<TraceMap> traces;

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

        new NGramsInterpreter().execute(payload);


    }

}
