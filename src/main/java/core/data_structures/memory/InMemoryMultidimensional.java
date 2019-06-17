package core.data_structures.memory;

import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IMapAdaptor;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.buffered.BufferedCollection;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class InMemoryMultidimensional<T> implements IMultidimensionalArray<T> {

    IArray<T> data;
    long maxI;
    long maxJ;

    public InMemoryMultidimensional(BufferedCollection.ITypeAdaptor<T> adaptor,long maxI, long maxJ){

        this.maxI = maxI;
        this.maxJ = maxJ;


        data = ServiceRegister.getProvider().allocateNewArray(null, maxI *maxJ, adaptor);
    }

    @Override
    public T get(int... indexes) {
        return this.data.read(maxJ*indexes[0] + indexes[1]);
    }

    @Override
    public T getDefault(T def, int... indexes) {

        T result = get(indexes);

        return result == null? def:result;
    }

    @Override
    public void set(T value, int... indexes) {
        this.data.set(maxJ*indexes[0] + indexes[1], value);
    }

    @Override
    public long size(int dimension) {
        return dimension == 0? maxI:maxJ;
    }
}