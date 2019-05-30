import core.IServiceProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemorySet;
import core.data_structures.segment_tree.SegmentTree;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

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

        IArray<Integer> array = ServiceRegister.getProvider().allocateNewArray(generateRandomIntegers(80000));

        SegmentTree<Integer, Integer[]> root = SegmentTree.build(array, 0, array.size() - 1);




    }

}
