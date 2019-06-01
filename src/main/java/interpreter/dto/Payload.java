package interpreter.dto;

import java.util.Map;

public class Payload {

    public String[] files;

    public int size;

    public MethodInfo method;

    public String exportComparisson;

    public boolean printComparisson;

    public boolean exportSegmentTrees;

    public String exportBag;

    public int[] exportNgram;

    public static class MethodInfo{

        public String name;

        public Object[] params;
    }
}
