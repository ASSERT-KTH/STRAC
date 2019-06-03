package interpreter.dto;

import java.util.List;

public class Alignment extends BaseDto {

    public List<String> files;

    public int[][] pairs;

    public Payload.MethodInfo method;

    public boolean outputAlignment;

    public boolean exportHTML;

    public Comparison comparison;

    public String outputAlignmentMap;

    public class Comparison{
        public double diff;
        public double eq;
        public double gap;
    }
}
