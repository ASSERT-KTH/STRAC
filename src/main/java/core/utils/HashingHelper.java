package core.utils;

import core.data_structures.IArray;
import core.data_structures.IReadArray;
import core.data_structures.buffered.BufferedCollection;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    public static int hashList1(IReadArray<Integer> items){
        return hashList(items).get(0);
    }

    public static String getRandomName(){
        return UUID.randomUUID().toString();
    }

    public static BufferedCollection.ITypeAdaptor<Integer> IntegerAdapter = new BufferedCollection.ITypeAdaptor<Integer>() {
        @Override
        public Integer fromBytes(byte[] chunk) {
            return ByteBuffer.wrap(chunk).getInt();
        }

        @Override
        public byte[] toBytes(Integer i) {
            return ByteBuffer.allocate(4).putInt(i).array();
        }

        @Override
        public int size() {
            return 4;
        }
    };
}
