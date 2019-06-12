package core.utils;

import core.data_structures.IArray;
import core.data_structures.IReadArray;
import core.data_structures.buffered.BufferedCollection;

import java.math.BigInteger;
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


    public static List<BigInteger> hashList(IReadArray<Integer> items, int from, int to){

        BigInteger prime1 = new BigInteger("1000000007");
        BigInteger prime2 = new BigInteger("100002593");

        // These modules for example (also primes)
        BigInteger module1 = new BigInteger("1011013823");
        BigInteger module2 = new BigInteger("1217513831");

        BigInteger hash1 = new BigInteger("0");
        BigInteger hash2 = new BigInteger("0");


        for(int i = from; i < to; i++){
            BigInteger val = new BigInteger("" + items.read(i));

            hash1 = hash1.multiply(prime1).add(val).mod(module1);
            hash1 = hash1.multiply(prime2).add(val).mod(module2);

        }

        List<BigInteger> result = new ArrayList<>();
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
