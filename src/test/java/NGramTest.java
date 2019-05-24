import core.LogProvider;
import core.TraceHelper;
import core.utils.SetHelper;
import ngram.Generator;
import ngram.generators.IdemGenerator;
import ngram.hash_keys.ListHashKey;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class NGramTest {

    Random r = new Random();

    @Test
    public void testIdemNGramEqualsOverrideChecking() {

        Generator g = new IdemGenerator();

        Set s =  g.getNGramSet(2, Arrays.asList(1,2,3,4,5,6,7,8,9));

        Assert.assertEquals(8, s.size());

        Assert.assertTrue(s.contains(new ListHashKey(Arrays.asList(1, 2))));
    }


    @Test
    public void testSetOperationsIntersect() {

        Generator g = new IdemGenerator();

        Set s1 =  g.getNGramSet(2, Arrays.asList(1,2,3,4,5,6,7,8,9));

        Set s2 =  g.getNGramSet(2, Arrays.asList(1,2,3,4,5,6,7));


        Set intersection = SetHelper.intersection(s1, s2);

        LogProvider.info(intersection.size() + " ");

    }

    @Test
    public void stressTesting() {

        int max = 20000;
        int nGramSize = 4;
        Generator g = new IdemGenerator();

        for(int i = 1; i < max; i++){

            Set s1 =  g.getNGramSet(500, Arrays.asList(generateRandomIntegers(i)));
            Set s2 =  g.getNGramSet(500, Arrays.asList(generateRandomIntegers(i)));

            long now = System.nanoTime();
            Set intersection = SetHelper.intersection(s1, s2);

            long delta = System.nanoTime() - now;

            System.out.println(delta + " ns " + i);
        }

    }


    public int[] generateRandomIntegers(int size){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = r.nextInt(30000);

        return result;
    }

}
