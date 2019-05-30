package ngram.hash_keys;

public interface IIHashSetKeyCreator<R, H> {

    H transform(R t);
}
