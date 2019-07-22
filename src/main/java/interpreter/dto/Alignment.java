package interpreter.dto;

import java.util.List;

public class Alignment extends BaseDto {

    public List<String> files;

    public List<int[]> pairs;

    public Payload.MethodInfo method;

    public boolean outputAlignment;

    public boolean exportHTML;

    public boolean exportImage;

    public Comparison comparison;

    public String distanceFunctionName;

    public String outputAlignmentMap;

    public static class Comparison{
        public int diff;
        public int eq;
        public int gap;
    }
}
