package core;

import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.ISet;
import core.data_structures.buffered.BufferedCollection;
import core.data_structures.buffered.MultiDimensionalCollection;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryMultidimensional;

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

        if(_provider == null){
            _provider = new IServiceProvider() {
                @Override
                public ALLOCATION_METHOD selectMethod(long size) {

                    if(size < 1 << 30)
                        return ALLOCATION_METHOD.MEMORY;

                    return ALLOCATION_METHOD.EXTERNAL;
                }

                @Override
                public <T> IArray<T> allocateNewArray(String id, long size, BufferedCollection.ITypeAdaptor<T> adaptor, ALLOCATION_METHOD method) {

                    if(method == ALLOCATION_METHOD.MEMORY){
                        return new InMemoryArray<T>(id, (int)size);
                    }

                    IArray<T> result = new BufferedCollection<>(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2, adaptor);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public <T> IMultidimensionalArray<T> allocateMuldimensionalArray(BufferedCollection.ITypeAdaptor<T> adaptor, ALLOCATION_METHOD method, int... dimensions) {

                    if(method == ALLOCATION_METHOD.MEMORY)
                        return new InMemoryMultidimensional<>(adaptor, dimensions[0], dimensions[1]);

                    MultiDimensionalCollection<T> result = new MultiDimensionalCollection<T>(getRandomName(),adaptor, dimensions);
                    openedArrays.add(result);

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
