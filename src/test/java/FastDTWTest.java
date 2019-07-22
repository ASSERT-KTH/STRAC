import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import core.LogProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FastDTWTest {


    Alignment dto = new Alignment();
    AlignInterpreter interpreter;

    @Before
    public void setup(){

        ServiceRegister.getProvider();


        dto.method = new Payload.MethodInfo();
        dto.method.name = "FastDTW";
        dto.method.params = Arrays.asList(
                5.0
        );
        dto.distanceFunctionName = "dBin";
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 5;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.outputDir="reports";
        //dto.exportImage = true;

        dto.files = Arrays.asList(
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.kth.se.10.bytecode.txt.st.processed.txt",
                "/Users/javier/IdeaProjects/kTToolkit/scripts/chrome_scripts/traces/www.github.com.10.bytecode.txt.st.processed.txt"
        );

        //comparers.put("DTW", (objs) -> new DTW(dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        //comparers.put("Linear", (objs) -> new LinearMemoryDTW(dto.comparison.gap,(x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));
        //comparers.put("FastDTW", (objs) -> new FastDTW(((Double)objs[0]).intValue()
        //        , dto.comparison.gap, (x, y) -> x == y? dto.comparison.eq: dto.comparison.diff));

        interpreter = new AlignInterpreter(null);

        //TestLogProvider.info(dto.files.get(0), dto.files.get(1), "[");

    }


    @After
    public void clean(){
        ServiceRegister.dispose();
    }



}
