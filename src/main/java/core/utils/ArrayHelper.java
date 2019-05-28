package core.utils;

import align.RepresentationFunction;
import core.LogProvider;

import java.lang.reflect.Array;
import java.util.*;

public class ArrayHelper {

    public static int[] subArray(int index, int size, int[] target){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = target[i + index];

        return result;
    }

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

    public static List<Integer> reduceByHalf(List<Integer> target, RepresentationFunction<Integer, Integer> representativeExtractor){

        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < target.size() - target.size()%2; i += 2){

            result.add(representativeExtractor.getRepresentativeElement(Arrays.asList(target.get(i), target.get(i + 1))));
        }

        return result;
    }

    public static List<Integer> reduceSize(List<Integer> target, int size, RepresentationFunction<Integer, Integer> representativeExtractor){

        int newSize = target.size()/size;


        List<Integer> result = new ArrayList<>();

        int i= 0;
        for(i = 0; i < size - 1; i++){

            List<Integer> chunk = target.subList(i*newSize, (i + 1)*newSize);

            int leader = representativeExtractor.getRepresentativeElement(chunk);


            result.add(leader);
        }

        List<Integer> chunk = target.subList(i*newSize, target.size());

        int leader = representativeExtractor.getRepresentativeElement(chunk);

        result.add(leader);


        return result;
    }

}
