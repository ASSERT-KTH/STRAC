package strac.align.models;

import strac.align.align.AlignDistance;
import strac.align.interpreter.dto.Payload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignResultDto {

    public Map<Integer, Map<Integer, Double>> functionMap;

    public Map<Integer, String> fileMap;
    public Payload.MethodInfo method;

    public AlignResultDto(){
        fileMap = new HashMap<>();

        functionMap = new HashMap<>();

    }


    public void setFunctioNMap(int i, int j, double value){

        if(!functionMap.containsKey(i)){
            functionMap.put(i, new HashMap<>());
        }

        functionMap.get(i).put(j, value);
    }

}
