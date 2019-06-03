package interpreter.dto;

import java.util.List;

public class Alignment extends BaseDto {

    public List<String> files;

    public int[][] pairs;

    public Payload.MethodInfo method;

    public boolean outputAlignment;
}
