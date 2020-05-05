package strac.core.data_structures;

import java.io.IOException;
import java.io.Writer;

public interface IReadArray<T> extends Iterable<T>, IDisposable{

    T read(long position);

    void close();

    long size();

    void reset();

    T[] getPlain();

    void writeTo(Writer wr, IMapAdaptor<T> adaptor) throws IOException;

    String getUniqueId();
}
