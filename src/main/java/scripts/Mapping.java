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

public class Mapping {

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
    }

    public static void main(String[] args) throws IOException, SQLException, ParseException {

        setup();



        Payload payload = null;

        LogProvider.info(args);

        String payloadPath = args[0];

        payload = new Gson().fromJson(new JsonReader(new FileReader(payloadPath)), Payload.class);
        PostgreInterface.setup(payload.dbHost, payload.dbPort, payload.dbName, payload.user, payload.password, false);


        // Create session
        String sessionName = payload.sessionName;
        String dateString = payload.sessionDate;

        PostgreInterface.getInstance()
                .executeQuery(String.format("INSERT INTO public.\"Session\" VALUES('%s', false, '%s') ON CONFLICT DO NOTHING", sessionName, dateString));


        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(payload.files, false, true);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for(TraceMap tr: traces){

            LogProvider.info("Uploading file", tr.traceFile);

            PreparedStatement ts = PostgreInterface.getInstance()
                    .connection.prepareStatement("INSERT INTO public.\"File\" VALUES(?, ?, ?, ?, ?) ON CONFLICT DO NOTHING");
            ts.setString(1, tr.traceFileName);
            ts.setString(2, sessionName);
            ts.setDate(3,  new Date(format.parse(dateString).getTime()));
            ts.setArray(4, PostgreInterface.getInstance().connection.createArrayOf("integer",
                    tr.plainTrace.getPlain()));
            ts.setArray(5, PostgreInterface.getInstance().connection
            .createArrayOf("text", tr.originalSentences));


            ts.executeUpdate();
        }

    }
}
