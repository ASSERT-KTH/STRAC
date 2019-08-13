import org.junit.Test;
import v8tor.BytecodeParser;
import v8tor.blocks.FunctionBlock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestBytecode {


    protected FileInputStream getFile(String name){

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return new FileInputStream(classLoader.getResource(name).getFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testParsing(){
        BytecodeParser p = new BytecodeParser(new Scanner(getFile("bytecode1.txt")).useDelimiter("\n"));
        ArrayList<FunctionBlock> bck =  p.parseCompleteBytecode();
        System.out.println(bck.size());

        System.out.println(bck.stream()
        .map(t -> t.getInstructions().size())
        .reduce(0 , Integer::sum)
        );

        System.out.println(bck.stream()
                .map(t -> t.getBlocks().size())
                .reduce(0 , Integer::sum)
        );

    }

}
