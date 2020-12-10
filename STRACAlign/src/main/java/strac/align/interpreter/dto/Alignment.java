package strac.align.interpreter.dto;

import strac.core.dto.FileContentDto;

import java.util.List;

public class Alignment extends FileContentDto {



    public Payload.MethodInfo method;

    public boolean outputAlignment;

    public boolean exportImage;

    public Comparison comparison;

    public String distanceFunctionName;

    public String outputAlignmentMap;

    public String explicitToWriteSeparator = "\n";

    public String generateInteractiveMap = "";

    public static class Comparison{
        public int diff;
        public int eq;
        public int gap;
    }
}
