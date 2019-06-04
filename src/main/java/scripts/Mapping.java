package scripts;

import com.google.gson.Gson;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryDict;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mapping {

/*
    public static void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {
            @Override
            public  <T extends Serializable> IArray<T> allocateNewArray() {
                return null;
            }

            @Override
            public  <T extends Serializable> IArray<T> allocateNewArray(int size) {
                return null;
            }

            @Override
            public  <T extends Serializable> IArray<T> allocateNewArray(String id) {
                try {
                    return new PersistentIntegerArray(id, PersistentIntegerArray.CachePolicy.SEQUENTIAL, 1000);
                } catch (IOException e) {
                    e.printStackTrace();

                    return null;
                }
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
    }

    public static void main(String[] args) throws IOException {

        setup();

        interpreter.dto.Mapping mappingDto =
                new Gson().fromJson(new FileReader(args[0]), interpreter.dto.Mapping.class);

        TraceHelper helper = new TraceHelper();

        int size = 1000;
        StringBuilder lines = new StringBuilder();
        Pattern p = Pattern.compile(mappingDto.pattern);

        for(String f: mappingDto.files){

            LogProvider.info("Transforming", f);

            String mapFileName = String.format("%s.marray", f);

            if(new File(mapFileName).exists())
                new File(String.format("%s.marray", f)).delete();

            IArray<Integer> mapResult = ServiceRegister.getProvider().allocateNewArray(mapFileName);

            FileInputStream str = new FileInputStream(f);

            while(str.available() != 0) {

                byte[] read = str.readNBytes(size);

                lines.append(new String(read));

                // Split builder;

                String[] split = lines.toString().split(mappingDto.delimiter);

                for (int i = 0; i < split.length - 1; i++) {

                    Matcher m = p.matcher(split[i]);

                    if (m.find()) {
                        Integer id = helper.updateBag(m.group(mappingDto.groupId));

                        lines = new StringBuilder(lines.substring(split[i].length() +
                                mappingDto.delimiter.length()));

                        mapResult.add(id);
                    }
                }

            }

            Matcher m = p.matcher(lines);

            if (m.find()) {
                Integer id = helper.updateBag(m.group(mappingDto.groupId));
                mapResult.add(id);
            }

            mapResult.close();
        }

        if(mappingDto.exportPayload){
            FileWriter writer = new FileWriter("bags.json");

            writer.write(new Gson().toJson(helper));

            writer.close();
        }
    }\*/
}
