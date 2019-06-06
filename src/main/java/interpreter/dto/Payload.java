package interpreter.dto;

import java.util.List;
import java.util.Map;

public class Payload extends BaseDto {

    public List<String> files;

    public int size;

    public MethodInfo method;

    public String exportComparisson;

    public boolean printComparisson;

    public boolean exportSegmentTrees;

    public String exportBag;

    public int[] exportNgram;

    public String sessionName;

    public String sessionDate;

    public static class MethodInfo{

        public String name;

        public Object[] params;
    }
}
