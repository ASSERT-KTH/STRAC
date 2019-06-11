package core.data_structures;

import java.io.IOException;
import java.io.Writer;
import java.sql.Array;

public interface IReadArray<T> extends Iterable<T>{

    T read(long position);

    void close();

    void dispose();

    IArray<T> subArray(long index, long size);

    long size();

    void reset();

    T[] getPlain();

    void writeTo(Writer wr, IMapAdaptor<T> adaptor) throws IOException;
}
