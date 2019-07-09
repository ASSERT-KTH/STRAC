package scripts;

import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import align.implementations.LinearMemoryDTW;
import com.google.gson.Gson;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Align {

    static List<IArray> openedArrays = new ArrayList<>();

    public static void setup(){

        ServiceRegister.getProvider();

        comparers = new HashMap<>();


        ClassLoader classLoader = Align.class.getClassLoader();
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

        comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
                , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));

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
