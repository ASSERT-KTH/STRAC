package core.models;

import java.util.HashMap;
import java.util.Map;

public class AlignResultDto {

    public Map<Integer, Map<Integer, Double>> distanceMap;

    public Map<Integer, String> fileMap;

    public AlignResultDto(){
        fileMap = new HashMap<>();

        distanceMap = new HashMap<>();
    }

    public void set(int i, int j, double value){

        if(!distanceMap.containsKey(i)){
            distanceMap.put(i, new HashMap<>());
        }

        distanceMap.get(i).put(j, value);
    }
}
