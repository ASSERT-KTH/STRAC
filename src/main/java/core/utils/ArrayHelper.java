package core.utils;

import java.lang.reflect.Array;

public class ArrayHelper {

    public static int[] subArray(int index, int size, int[] target){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = target[i + index];

        return result;
    }

    public int[] reduce(int[] target, int newSize){

        return target;
    }

}
