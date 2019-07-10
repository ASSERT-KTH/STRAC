package core;

import align.Cell;
import align.implementations.WindowedDTW;
import core.data_structures.IArray;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.buffered.BidimensionalBufferedCollectionDouble;
import core.data_structures.buffered.BufferedCollectionInteger;
import core.data_structures.buffered.BufferedCollectionLong;
import core.data_structures.buffered.BufferedWarpPath;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryLongArray;
import core.data_structures.memory.InMemoryMultidimensional;
import core.data_structures.memory.InMemoryWarpPath;

import java.util.ArrayList;
import java.util.List;

import static core.utils.HashingHelper.getRandomName;

public class ServiceRegister {

    static IServiceProvider _provider;

    public static void registerProvider(IServiceProvider provider){
        _provider = provider;
    }

    static List<IArray> openedArrays = new ArrayList<>();

    public static IServiceProvider getProvider(){

        /*IArray<Integer> allocateIntegerArray(String id, long size, ALLOCATION_METHOD method);

        IArray<Long> (String id, long size, ALLOCATION_METHOD method);

        IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrix(long maxI, long maxJ, ALLOCATION_METHOD method);
*/


        if(_provider == null){
            _provider = new IServiceProvider() {
                @Override
                public ALLOCATION_METHOD selectMethod(long size) {

                    if(size < 1L << 30)
                        return ALLOCATION_METHOD.MEMORY;

                    return ALLOCATION_METHOD.EXTERNAL;
                }

                @Override
                public IArray<Integer> allocateIntegerArray(String id, long size, ALLOCATION_METHOD method) {

                    if(method == ALLOCATION_METHOD.MEMORY){
                        return new InMemoryArray(id, (int)size);
                    }

                    IArray<Integer> result = new BufferedCollectionInteger(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public IArray<Cell> allocateWarpPath(String id, long size, ALLOCATION_METHOD method) {
                    if(method == ALLOCATION_METHOD.MEMORY){
                        return new InMemoryWarpPath(null, (int)size);
                    }

                    IArray<Cell> result = new BufferedWarpPath(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public IArray<Long> allocateLonArray(String id, long size, ALLOCATION_METHOD method) {
                    if(method == ALLOCATION_METHOD.MEMORY){
                        return new InMemoryLongArray(id, (int)size);
                    }

                    IArray<Long> result = new BufferedCollectionLong(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrix(long maxI, long maxJ, ALLOCATION_METHOD method) {

                    if(method == ALLOCATION_METHOD.MEMORY)
                        return new InMemoryMultidimensional( maxI, maxJ);

                    BidimensionalBufferedCollectionDouble result = new BidimensionalBufferedCollectionDouble(getRandomName(), maxI, maxJ);

                    //openedArrays.add(result);

                    return result;
                }

                @Override
                public IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrixWindow(long maxI, long maxJ, ALLOCATION_METHOD method, WindowedDTW.Window window) {
                    if(method == ALLOCATION_METHOD.MEMORY)
                        return new InMemoryMultidimensional( maxI, maxJ);

                    BidimensionalBufferedCollectionDouble result = new BidimensionalBufferedCollectionDouble(getRandomName(), maxI, maxJ, window);

                    //openedArrays.add(result);

                    return result;
                }


            };
        }
        return _provider;
    }

    public static void dispose(){

        for(IArray arr: openedArrays)
            arr.dispose();
    }

}
