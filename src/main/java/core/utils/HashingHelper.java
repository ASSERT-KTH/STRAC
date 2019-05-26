package core.utils;

import java.util.ArrayList;
import java.util.List;

public class HashingHelper {


    public static List<Integer> hashList(List<Integer> items){
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

    public static int hashList1(List<Integer> items){
        return hashList(items).get(0);
    }
}
