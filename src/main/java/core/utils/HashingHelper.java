package core.utils;

import core.data_structures.IArray;
import core.data_structures.IReadArray;
import core.data_structures.buffered.BufferedCollection;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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
        return String.format("temp_dst/%s",  UUID.randomUUID().toString());
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

        @Override
        public Class<Integer> clazz() {
            return Integer.class;
        }
    };


    public static BufferedCollection.ITypeAdaptor<Double> DoubleAdapter = new BufferedCollection.ITypeAdaptor<Double>() {
        @Override
        public Double fromBytes(byte[] chunk) {
            return ByteBuffer.wrap(chunk).getDouble();
        }

        @Override
        public byte[] toBytes(Double i) {
            return ByteBuffer.allocate(8).putDouble(i).array();
        }

        @Override
        public int size() {
            return 8;
        }

        @Override
        public Class<Double> clazz() {
            return Double.class;
        }
    };
}
