package scripts;

import core.utils.ServiceRegister;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Mapping {

    public static void setup(){
        //ServiceRegister.getProvider();
    }

    public static void main(String[] args) throws IOException, SQLException, ParseException {

        /*setup();



        Payload payload = null;

        LogProvider.info(args);

        String payloadPath = args[0];

        payload = new Gson().fromJson(new JsonReader(new FileReader(payloadPath)), Payload.class);



        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(payload.files, "\r\n",  t -> {
            try {
                return new FileInputStream(t);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, false, payload.com);

        /*
        for(TraceMap tr: traces){
            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/%s.json", tr.traceFileName));

            wr.write(new Gson().toJson(tr.plainTrace));
            wr.close();
        }

        for(TraceMap tr: traces){

            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/trace_%s.csv", tr.traceFileName));

            for(Integer i : tr.plainTrace) {
                wr.write(String.format("%s\n", String.valueOf((i))));
            }

            wr.close();
        }*/

    }
}
