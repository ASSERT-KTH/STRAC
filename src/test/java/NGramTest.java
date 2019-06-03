import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.memory.InMemorySet;
import core.utils.SetHelper;
import ngram.Generator;
import ngram.generators.IdemGenerator;
import ngram.hash_keys.IHashCreator;
import ngram.hash_keys.ListHashKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NGramTest {

    Random r = new Random();

    @Before
    public void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {
            @Override
            public  <T> IArray<T> allocateNewArray(Class<T> clazz) {
                return new InMemoryArray<T>();
            }

            @Override
            public  <T> IArray<T> allocateNewArray(int size, Class<T> clazz) {
                return new InMemoryArray<T>(size);
            }

            @Override
            public  <T> IArray<T> allocateNewArray(String id, Class<T> clazz) {
                return null;
            }

            @Override
            public  <T> IArray<T> allocateNewArray(T[] items, Class<T> clazz) {
                return new InMemoryArray<T>(items);
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {
                return new InMemoryDict<>();
            }

            @Override
            public <T, R> IHashCreator<T, R> getHashCreator() {
                return null;
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return new InMemorySet<>(new HashSet<>());
            }

            @Override
            public Generator getGenerator() {
                return null;
            }
        });
    }

    @Test
    public void testIdemNGramEqualsOverrideChecking() {

        /*Generator g = new IdemGenerator();

        ISet<ListHashKey> s =  g.getNGramSet(2, new InMemoryArray(Arrays.asList(1,2,3,4,5,6,7,8,9)));

        Assert.assertEquals(8, s.size());

        Assert.assertTrue(s.contains(new ListHashKey(Arrays.asList(1, 2))));*/
    }


    @Test
    public void testSetOperationsIntersect() {

        /*Generator g = new IdemGenerator();

        ISet<ListHashKey> s1 =  g.getNGramSet(2, new InMemoryArray(Arrays.asList(1,2,3,4,5,6,7,8,9)));

        ISet<ListHashKey> s2 =  g.getNGramSet(2, new InMemoryArray(Arrays.asList(1,2,3,4,5,6,7)));


        ISet<ListHashKey> intersection = s1.intersect(s2);

        LogProvider.info(intersection.size() + " ");*/

    }

    @Test
    public void stressTesting() {

       /* int max = 1000;
        int nGramSize = 4;
        Generator g = new IdemGenerator();

        for(int i = 1; i < max; i++){

            ISet<ListHashKey> s1 =  g.getNGramSet(500, new InMemoryArray(generateRandomIntegers(i)));
            ISet<ListHashKey> s2 =  g.getNGramSet(500, new InMemoryArray(generateRandomIntegers(i)));

            long now = System.nanoTime();
            ISet<ListHashKey> intersection = s1.intersect(s2);

            long delta = System.nanoTime() - now;

            System.out.println(delta + " ns " + i);
        }*/

    }


    public Integer[] generateRandomIntegers(int size){

        Integer[] result = new Integer[size];

        for(int i = 0; i < size; i++)
            result[i] = r.nextInt(30000);

        return result;
    }


}
