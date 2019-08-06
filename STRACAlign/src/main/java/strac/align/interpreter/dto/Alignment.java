package strac.align.interpreter.dto;

import strac.core.dto.FileContentDto;

import java.util.List;

public class Alignment extends FileContentDto {


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
