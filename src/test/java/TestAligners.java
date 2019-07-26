import align.AlignDistance;
import core.LogProvider;
import core.ServiceRegister;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestAligners extends BaseResourceFileTest {


    @Test
    public void testDTW() throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "DTW";
        dto.method.params = Arrays.asList();
        //dto.distanceFunctionName = "dBin";
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 5;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.separator ="[\n\r]";
        dto.clean =new String[] {
                "( )*\\d+ [ES]>",
                "0x\\w+ @",
                "\\w+ : ",
                " [A-Z](.*)",
                "\\(.*\\)",
                "\\{.*\\}"
        };
        dto.outputDir="reports";
        //dto.exportImage = true;


        dto.files = Arrays.asList(
                "bytecodes/wikipedia.org.bytecode",
                "bytecodes/wikipedia.1.org.bytecode"
        );

        AlignInterpreter interpreter = new AlignInterpreter(null);


        interpreter.execute(dto, null, this::getFile);


    }



    @Test
    public void testDTW2() throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        Alignment dto = new Alignment();
        dto.method = new Payload.MethodInfo();
        dto.method.name = "DTW";
        dto.method.params = Arrays.asList();
        //dto.distanceFunctionName = "dBin";
        dto.comparison = new Alignment.Comparison();
        dto.comparison.gap = 1;
        dto.comparison.diff = 5;
        dto.comparison.eq = 0;
        dto.pairs = new ArrayList<>();
        dto.outputAlignment = true;
        dto.separator ="[\n\r]";
        dto.clean =new String[] {
        };
        dto.outputDir="reports";
        //dto.exportImage = true;


        dto.files = Arrays.asList(
                "test.file",
                "test3.file"
        );

        AlignInterpreter interpreter = new AlignInterpreter(null);


        interpreter.execute(dto, new AlignInterpreter.IOnAlign() {
            @Override
            public void action(AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize) {
                LogProvider.info(successCount, mismatchCount, traceSize);
            }
        }, this::getFile);


    }

}
