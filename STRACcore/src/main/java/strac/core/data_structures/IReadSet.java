package strac.core.data_structures;

import java.util.Collection;

public interface IReadSet<T> {

    int size();

    Collection<T> getKeys();

}
