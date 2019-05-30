package ngram.hash_keys;

public interface IHashCreator<T, TResult> {

    TResult getHash(TResult left, TResult right);


    TResult getHash(T left);

}
