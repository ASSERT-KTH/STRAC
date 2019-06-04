package core.data_structures;

import java.io.IOException;
import java.io.Writer;

public interface IReadArray<T> extends Iterable<T>{

    T read(int position);

    void close();

    void dispose();

    IArray<T> subArray(int index, int size);

    int size();

    void reset();

    void writeTo(Writer wr, IMapAdaptor<T> adaptor) throws IOException;
}
