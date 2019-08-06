package models;

public class OutputDto {



    public MutationInfo[] info;
    public int max;
    public int[][] seqs;

    public class MutationInfo{

        public String mutationName;

        public Location loc;

        public int hitCount;

        public String repr;
    }

    public class Location{
        public LocationPoint start;
        public LocationPoint end;
    }

    public class LocationPoint{
        public int column;
        public int line;
    }

}
