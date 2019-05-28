package ngram.interfaces;

import core.data_structures.ISet;

import java.util.Set;

public interface ISetComparer {

    <T> double getDistance(ISet<T> set1, ISet<T> set2);

    String getName();
}
