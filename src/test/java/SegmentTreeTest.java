import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemorySet;
import core.data_structures.segment_tree.SegmentTree;
import ngram.hash_keys.IHashCreator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SegmentTreeTest extends BaseTest {


    @Before
    public void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {
            @Override
            public IArray<Integer> allocateNewArray() {
                return new InMemoryArray();
            }

            @Override
            public IArray<Integer> allocateNewArray(int size) {
                return new InMemoryArray(size);
            }

            @Override
            public IArray<Integer> allocateNewArray(Integer[] items) {
                return new InMemoryArray(items);
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return new InMemorySet<>(new HashSet<>());
            }
        });
    }

    @Test
    public void segmentTreeTest(){

        IArray<Integer> array = ServiceRegister.getProvider().allocateNewArray(generateRandomIntegers(8000));

        SegmentTree<Integer, Integer[]> root = SegmentTree.build(array, 0, array.size() - 1, new IHashCreator<Integer, Integer[]>() {
            @Override
            public Integer[] getHash(Integer[] left, Integer[] right) {

                int prime1 = 1000000000 + 7;
                int prime2 = 100002593;

                // These modules for example (also primes)
                int module1 = 1011013823;
                int module2 = 1217513831;

                int hash1 = 0;
                int hash2 = 0;


                for(Integer val1: left) {
                    hash1 = (hash1 * prime1 + val1) % module1;
                    hash2 = (hash2 * prime2 + val1) % module2;
                }


                for(Integer val1: right) {
                    hash1 = (hash1 * prime1 + val1) % module1;
                    hash2 = (hash2 * prime2 + val1) % module2;
                }



                return new Integer[] { hash1, hash2  };
            }

            @Override
            public Integer[] getHash(Integer val) {


                int prime1 = 1000000000 + 7;
                int prime2 = 100002593;

                // These modules for example (also primes)
                int module1 = 1011013823;
                int module2 = 1217513831;

                int hash1 = 0;
                int hash2 = 0;


                hash1 = (hash1 * prime1 + val) % module1;
                hash2 = (hash2 * prime2 + val) % module2;



                return new Integer[] { hash1, hash2  };
            }
        });




    }

    @Test
    public void segmentTreeBasic(){

        IArray<Integer> array = ServiceRegister.getProvider().allocateNewArray(new Integer[]{1, 2, 3, 4, 5, 6});

        //IArray<Integer> array = ServiceRegister.getProvider().allocateNewArray(generateRandomIntegers(8000000));

        IHashCreator<Integer, BigInteger[]> hash1 = new IHashCreator<Integer, BigInteger[]>() {
            @Override
            public BigInteger[] getHash(BigInteger[] left, BigInteger[] right) {


                BigInteger prime1 = new BigInteger(String.valueOf(1000000000 + 7));
                BigInteger prime2 = new BigInteger(String.valueOf(100002593));

                // These modules for example (also primes)
                BigInteger module1 = new BigInteger(String.valueOf(1011013823));
                BigInteger module2 = new BigInteger(String.valueOf(1011013823));

                BigInteger hash1 = new BigInteger("0");
                BigInteger hash2 = new BigInteger("0");


                for(BigInteger val: left){
                    hash1 = hash1.multiply(prime1).add(val).mod(module1);
                    hash2 = hash2.multiply(prime2).add(val).mod(module2);
                }

                for(BigInteger val: right){
                    hash1 = hash1.multiply(prime1).add(val).mod(module1);
                    hash2 = hash2.multiply(prime2).add(val).mod(module2);
                }


                return new BigInteger[]{hash1, hash2};
            }

            @Override
            public BigInteger[] getHash(Integer left) {
                return new BigInteger[] {
                        new BigInteger(String.valueOf(left)),
                        new BigInteger(String.valueOf(left))
                };
            }
        };

        SegmentTree<Integer, BigInteger[]> root = SegmentTree.build(array, 0, array.size() - 1, hash1);


        LogProvider.info(root.query(0, 1, hash1));
        LogProvider.info(root.query(2, 3, hash1));
        LogProvider.info(root.query(0, 5, hash1));
        LogProvider.info(root.query(0, 5, hash1));
        LogProvider.info(root.query(0, 3, hash1));


    }

}
