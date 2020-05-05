package strac.core.utils;

import strac.core.LogProvider;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IReadArray;

import java.util.*;

public class ArrayHelper {

    public static int getMostFequentRepresentation(List<Integer> target){

        Map<Integer, Integer> count = new HashMap<>();

        for(int i: target)
        {
            if(!count.containsKey(i))
                count.put(i, 0);

            count.put(i, count.get(i) + 1);
        }

        int max = Integer.MIN_VALUE;
        int index = 0;

        for(int i: count.keySet()){
            if(count.get(i) > max)
            {
                index = i;
                max = count.get(i);
            }
        }

        return index;
    }

    static Random r = new Random();

    public static void reduceByHalf(int[] target, int[] result, RepresentationFunction<Integer, Integer> representativeExtractor){

        for(int i = 0; i < target.length - target.length%2; i += 2){
            result[i/2] = target[i];
        }

    }


}
