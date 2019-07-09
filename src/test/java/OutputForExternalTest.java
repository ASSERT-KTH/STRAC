import com.google.gson.Gson;
import core.ServiceRegister;
import core.TestLogProvider;
import core.TraceHelper;
import core.models.TraceMap;
import interpreter.dto.Alignment;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class OutputForExternalTest {

    @Before
    public void setup(){

        TestLogProvider.info("Start test session", new Date());

        ServiceRegister.getProvider();

    }

    @Test
    public void exportR_DTW() throws IOException {

        Alignment dto = new Alignment();

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.github.com.10.bytecode.txt.st.processed.txt"
        );


        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, false, false);


        for(TraceMap tr: traces){
            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/R_DTW/%s.json", tr.traceFileName));

            wr.write(new Gson().toJson(tr.plainTrace));
            wr.close();
        }
    }

    @Test
    public void exportCSV() throws IOException {

        Alignment dto = new Alignment();

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.kth.se.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces_tiny/www.github.com.10.bytecode.txt.st.processed.txt"
        );


        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, false, false);

        for(TraceMap tr: traces){

            FileWriter wr = new FileWriter(String.format("/Users/javier/Documents/R_DTW/trace_%s.csv", tr.traceFileName));

            for(Integer i : tr.plainTrace) {
                wr.write(String.format("%s\n", String.valueOf((i))));
            }

            wr.close();
        }
    }
}
