package ngram.interfaces;

import java.util.Set;

public interface ISetComparer {

    <T> double getDistance(Set<T> set1, Set<T> set2);

    String getName();
}
