package strac.align.interpreter.dto;

import strac.core.dto.BaseDto;

public class Mapping extends BaseDto {

    public String[] files;

    public String pattern; // Regular expression

    public String delimiter;

    public int groupId;

    public boolean exportPayload;
}
