package scripts;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemorySet;
import core.data_structures.postgres.PostgreInterface;
import core.models.TraceMap;
import interpreter.dto.Payload;
import ngram.Generator;
import ngram.generators.StringKeyGenerator;
import ngram.generators.comparers.*;
import ngram.hash_keys.IHashCreator;

import java.io.*;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static core.utils.HashingHelper.getRandomName;

public class Mapping {

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

                return null;
            }
        });
    }

    public static void main(String[] args) throws IOException, SQLException, ParseException {

        setup();



        Payload payload = null;

        LogProvider.info(args);

        String payloadPath = args[0];

        payload = new Gson().fromJson(new JsonReader(new FileReader(payloadPath)), Payload.class);



        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(payload.files, false, false);

        for(TraceMap tr: traces){
            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/%s.json", tr.traceFileName));

            wr.write(new Gson().toJson(tr.plainTrace));
            wr.close();
        }

    }
}
