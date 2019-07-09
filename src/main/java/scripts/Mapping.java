package scripts;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.models.TraceMap;
import interpreter.dto.Payload;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Mapping {

    public static void setup(){
        ServiceRegister.getProvider();
    }

    public static void main(String[] args) throws IOException, SQLException, ParseException {

        setup();



        Payload payload = null;

        LogProvider.info(args);

        String payloadPath = args[0];

        payload = new Gson().fromJson(new JsonReader(new FileReader(payloadPath)), Payload.class);



        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(payload.files, false, false);

        /*
        for(TraceMap tr: traces){
            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/%s.json", tr.traceFileName));

            wr.write(new Gson().toJson(tr.plainTrace));
            wr.close();
        }*/

        for(TraceMap tr: traces){

            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/trace_%s.csv", tr.traceFileName));

            for(Integer i : tr.plainTrace) {
                wr.write(String.format("%s\n", String.valueOf((i))));
            }

            wr.close();
        }

    }
}
