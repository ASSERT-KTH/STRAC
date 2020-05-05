package strac.core.utils;

import strac.core.data_structures.IReadArray;

import java.io.File;
import java.util.*;

public class HashingHelper {


    public static List<Integer> hashList(IReadArray<Integer> items){
        int prime1 = 1000000000 + 7;
        int prime2 = 100002593;

        // These modules for example (also primes)
        int module1 = 1011013823;
        int module2 = 1217513831;

        int hash1 = 0;
        int hash2 = 0;


        for(int val: items){
            hash1 = (hash1 * prime1 + val) % module1;
            hash2 = (hash2 * prime2 + val) % module2;
        }

        List<Integer> result = new ArrayList<>();
        result.add(hash1);
        result.add(hash2);

        return result;

    }

    public static Map<String, List<Long>> cache = new HashMap<>();

    public static List<Long> hashList(IReadArray<Integer> items, int from, int to){


        long prime1 = 1000000007;
        long prime2 = 100002593;

        // These modules for example (also primes)
        long module1 = 1011013823;
        long module2 = 1217513831;

        long hash1 = 0;
        long hash2 = 0;

        List<Long> result = new ArrayList<>();


        for(int i = from; i < to; i++){
            int val= items.read(i);

            hash1 = (hash1*prime1 + val)%module1;
            hash2 = (hash2*prime2 + val)%module2;

        }

        result.add(hash1);
        result.add(hash2);


        return result;
    }

    public static int hashList1(IReadArray<Integer> items){
        return hashList(items).get(0);
    }

    public static String getRandomName(){

        if(!new File("temp_dst").exists())
            new File("temp_dst").mkdir();

        return String.format("temp_dst/%s",  UUID.randomUUID().toString());
    }

}
