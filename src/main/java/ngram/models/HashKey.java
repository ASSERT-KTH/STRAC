package ngram.models;

public abstract class HashKey {


    @Override
    public int hashCode() {
        throw new RuntimeException("You must provide a comparisson. Constant time is prefered");
    }

    @Override
    public boolean equals(Object obj) {
        throw new RuntimeException("You must provide a comparisson. Constant time is prefered");
    }
}
