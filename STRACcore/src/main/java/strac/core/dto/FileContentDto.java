package strac.core.dto;

import java.util.List;

public class FileContentDto extends BaseDto {

    public List<String> files;

    public List<int[]> pairs;

    public String separator;

    public String[] clean;

    public Include include;

    public static class Include {

        public String pattern;
        public int group;
    }
}
