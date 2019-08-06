package core.utils;

import core.LogProvider;
import core.data_structures.IArray;
import core.data_structures.IReadArray;

import java.lang.reflect.Array;
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

    public static void reduceByHalf(IReadArray<Integer> target, IArray<Integer> result, RepresentationFunction<Integer, Integer> representativeExtractor){

        for(int i = 0; i < target.size() - target.size()%2; i += 2){

            //result.add(target.read( i + r.nextInt(1 << 30)%2));
            result.set(i/2,target.read( i));
        }

    }


}
