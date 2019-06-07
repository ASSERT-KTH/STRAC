package scripts;

import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import com.google.gson.Gson;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.utils.HashingHelper.getRandomName;

public class Align {

    static List<IArray> openedArrays = new ArrayList<>();

    public static void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {


            @Override
            public <T> IArray<T> allocateNewArray(String id, int size, BufferedCollection.ITypeAdaptor<T> adaptor) {
                //IArray<T> result = new BufferedCollection<>(id==null? getRandomName(): id, size, adaptor);

                //openedArrays.add(result);

                return new InMemoryArray<>();
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
        comparers.put("DTW", (objs) -> new DTW((x, y) -> x == y? 1: -1));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , (x, y) -> x == y? 2: -1));

        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("templates").getFile());

        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, file.getAbsolutePath());
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute" );
        ve.init();
    }


    static VelocityEngine ve;

    static Map<String, IImplementationInfo> comparers;

    public static void main(String[] args) throws IOException, SQLException {

        setup();

        Alignment dto = new Gson().fromJson(new FileReader(args[0]), Alignment.class);

        //PostgreInterface.setup(dto.dbHost, dto.dbPort, dto.dbName, dto.user, dto.password, false);

        AlignInterpreter executor = new AlignInterpreter(comparers, ve);


        try{
            executor.execute(dto);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            LogProvider.info("Disposing map files");
            for(IArray arr: openedArrays)
                arr.dispose();
        }



    }
}
