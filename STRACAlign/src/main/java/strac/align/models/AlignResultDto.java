package strac.align.models;

import javassist.compiler.ast.Symbol;
import strac.align.align.AlignDistance;
import strac.align.interpreter.dto.Payload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignResultDto {

    public Map<Integer, Map<Integer, Double>> functionMap;

    public static class SymbolMatch{
        public String s1;
        public String s2;

        public SymbolMatch(String s1, String s2){
            this.s1 = s1;
            this.s2 = s2;
        }
    }

    public Map<Integer, Map<Integer, ArrayList<SymbolMatch>>> mapBackAligment;

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
