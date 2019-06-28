import align.InsertOperation;
import com.google.gson.Gson;
import core.LogProvider;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.BufferedDictionary;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.SortedMap;

public class TestBTree {

    @Test
    public void testBTree(){


        BufferedDictionary<PointClass, Long> dict = new BufferedDictionary<PointClass, Long>(
                new BufferedCollection<>("tree", 1000000, Integer.MAX_VALUE/2, new BufferedCollection.ITypeAdaptor<>() {
                    @Override
                    public BufferedDictionary.INode fromBytes(byte[] chunk) {

                        int i = 0;
                        for(i = 0; i < chunk.length && chunk[i] > 0; i++);

                        String js = new String(chunk, 0, i);

                        return new Gson().fromJson(js, BufferedDictionary.INode.class);
                    }

                    @Override
                    public byte[] toBytes(BufferedDictionary.INode i) {

                        byte[] data = Arrays.copyOf(new Gson().toJson(i).getBytes(), size());
                        return data;
                    }

                    @Override
                    public int size() {
                        return 500;
                    }
                }));

        for(long i = 0; i < 1000; i++){
            for(long j = 0; j < 1000; j++) {
                dict.insert(new PointClass(i, j), i + j);
            }
        }

    }

    public static class PointClass implements Comparable<PointClass>, Serializable {

        private static final long serialVersionUID = 1L;

        long x;
        long y;

        public PointClass(long x, long y){
            this.x = x;
            this.y = y;
        }


        private void writeObject(ObjectOutputStream oos)
                throws IOException {
            oos.defaultWriteObject();
            oos.writeObject(x);
            oos.writeObject(y);
        }

        private void readObject(ObjectInputStream ois)
                throws ClassNotFoundException, IOException {
            ois.defaultReadObject();
            x = (long) ois.readObject();
            y = (long) ois.readObject();
        }

        @Override
        public int compareTo(@NotNull TestBTree.PointClass o) {

            if(this.x != o.x) {
                if (this.x > o.x)
                    return 1;
                else
                    return -1;
            }
            else{
                if(this.y > o.y)
                    return 1;
                else if(this.y < o.y)
                    return -1;
            }

            return 0;
        }
    }

}
