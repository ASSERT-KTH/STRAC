import java.util.Random;

public class BaseTest {

    Random r = new Random();

    public Integer[] generateRandomIntegers(int size){

        Integer[] result = new Integer[size];

        for(int i = 0; i < size; i++)
            result[i] = r.nextInt(30000);

        return result;
    }
}
