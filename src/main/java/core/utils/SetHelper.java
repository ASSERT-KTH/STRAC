package core.utils;

import java.util.HashSet;
import java.util.Set;

public class SetHelper {

    public static <T> Set<T> intersection(Set<T> setA, Set<T> setB){
        Set<T> tmp = new HashSet<>();

        if(setA.size() > setB.size()){ // swap
            Set<T> swap = setB;
            setB = setA;
            setA = swap;
        }

        for(T x: setA) // O(n)
            if (setB.contains(x)) // O(1) if set is implemented with HashSet
                tmp.add(x);


        return tmp;

    }


    public static <T> Set<T> union(Set<T> setA, Set<T> setB){
         Set<T> result = new HashSet<>(setA);

         result.addAll(setB);

         return result;
    }

}
