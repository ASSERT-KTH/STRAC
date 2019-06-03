import core.LogProvider;
import core.data_structures.IArray;
import core.data_structures.postgreSQL.PostgreArray;
import core.data_structures.postgreSQL.PostgreInterface;
import org.junit.Test;

import java.io.PipedOutputStream;
import java.sql.SQLException;

public class TestPostgresInterface {

    @Test
    public void testCreatingDatabase() throws SQLException {

        PostgreInterface.setup("localhost", 5433, "test4", "postgres", "Omaha1942", true);

        PostgreInterface i = PostgreInterface.getInstance();

    }
    @Test
    public void testPostgresArray() throws SQLException {

        PostgreInterface.setup("localhost", 5433, "test4", "postgres", "Omaha1942", false);


        PostgreArray<Integer> arr = new PostgreArray<>("testTrace2", Integer.class);

        arr.add(10);
        arr.add(10);
        arr.add(10);
        arr.add(10);
        arr.add(16);

        arr.close();

    }


    @Test
    public void testPostgresArray2() throws SQLException {

        PostgreInterface.setup("localhost", 5433, "test4", "postgres", "Omaha1942", false);


        PostgreArray<Integer> arr = new PostgreArray<>("testTrace2", Integer.class);

        LogProvider.info(arr.read(2));
        LogProvider.info(arr.read(3));
        LogProvider.info(arr.read(4));
        LogProvider.info(arr.read(5));

    }


    @Test
    public void testPostgresArrayStress() throws SQLException {

        PostgreInterface.setup("localhost", 5433, "test4", "postgres", "Omaha1942", false);

        PostgreArray arr = new PostgreArray("largeTrace", Integer.class);

        for(int i = 0; i < 1 << 30; i++)
            arr.add(i);
    }


    @Test
    public void testPostgresArrayDeleteTrace() throws SQLException {

        PostgreInterface.setup("localhost", 5433, "test4", "postgres", "Omaha1942", false);

        PostgreArray arr = new PostgreArray("largeTrace", Integer.class);

        arr.dispose();
    }


    @Test
    public void testPostgressSubarray() throws SQLException {

        PostgreInterface.setup("localhost", 5433, "test4", "postgres", "Omaha1942", false);

        PostgreArray arr = new PostgreArray("testTrace2", Integer.class);

        IArray copy = arr.subArray(0, 2);

    }


    @Test
    public void testPostgresIterator() throws SQLException {

        PostgreInterface.setup("localhost", 5433, "test4", "postgres", "Omaha1942", false);

        PostgreArray<Integer> arr = new PostgreArray<>("testTrace2", Integer.class);

        for(Integer i: arr){
            LogProvider.info(i);
        }

    }


}
