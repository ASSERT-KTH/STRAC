package interpreter.dto;

public class Mapping extends BaseDto {

    public String[] files;

    public String pattern; // Regular expression

    public String delimiter;

    public int groupId;

    public boolean exportPayload;
}
