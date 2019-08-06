package models;

import interpreter.dto.Payload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignResultDto {

    public Map<Integer, Map<Integer, Double>> distanceMap;
    public Map<Integer, Map<Integer, Double>> functionMap;

    public Map<Integer, String> fileMap;
    public List<Double> results;
    public Payload.MethodInfo method;

    public AlignResultDto(){
        fileMap = new HashMap<>();

        distanceMap = new HashMap<>();
        functionMap = new HashMap<>();

        results = new ArrayList<>();

    }


    public void setFunctioNMap(int i, int j, double value){

        if(!functionMap.containsKey(i)){
            functionMap.put(i, new HashMap<>());
        }

        functionMap.get(i).put(j, value);
    }

    public void set(int i, int j, double value){

        if(!distanceMap.containsKey(i)){
            distanceMap.put(i, new HashMap<>());
        }

        distanceMap.get(i).put(j, value);
    }
}
