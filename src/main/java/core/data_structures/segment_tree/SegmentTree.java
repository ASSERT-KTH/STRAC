package core.data_structures.segment_tree;

import core.LogProvider;
import core.data_structures.IArray;
import core.data_structures.IReadArray;
import ngram.hash_keys.IHashCreator;

public class SegmentTree<T, THash> {

    public SegmentTree<T, THash> left;
    public SegmentTree<T, THash> right;

    public long st;
    public long nd;

    public THash hash;

    private T value;


    public SegmentTree(T value, long st, long nd, SegmentTree<T, THash> left, SegmentTree<T, THash> right){
        this.st = st;
        this.nd = nd;

        this.left = left;
        this.right = right;

        this.value = value;
    }

    public SegmentTree(T value, long st, long nd){
        this.st = st;
        this.nd = nd;

        this.value = value;
    }

    public SegmentTree(long st, long nd, SegmentTree<T, THash> left, SegmentTree<T, THash> right){
        this.st = st;
        this.nd = nd;

        this.left = left;
        this.right = right;
    }

    public boolean isLeaf(){
        return st == nd;
    }

    public long getSize(){
        return nd - st + 1;
    }

    public THash query(long start, long end, IHashCreator<T, THash> hashCreator){

        if(start < this.st || end > this.nd)
            return null;

        if(this.st == start && this.nd == end)
            return this.hash;


        long nodeMid = (this.st + this.nd)/2;

        if(start > nodeMid){
            return this.right.query(start, end, hashCreator);
        }
        if(nodeMid >= end)
        {
            return this.left.query(start, end, hashCreator);
        }
        else{
            THash h1 = left.query( start, nodeMid, hashCreator);
            THash h2 = right.query(nodeMid + 1, end, hashCreator);

            if(h1 == null)
                return h2;

            if(h2 == null)
                return h1;

            return hashCreator.getHash(h1, h2);
        }
    }


    public static  <T, THash> SegmentTree<T, THash> build(IReadArray<T> stream, long start, long end, IHashCreator<T, THash> hashCreator){

        if(stream.size() == 0)
            return null;

        if(start == end){
            // is Leaf

            T value = stream.read(start);

            SegmentTree<T, THash> result = new SegmentTree<>(value, start, end);

            THash h = hashCreator.getHash(value);

            result.hash = h;


            return result;
        }
        else{
            long mid = (start + end)/2;

            SegmentTree<T, THash> left = build(stream, start, mid, hashCreator);
            SegmentTree<T, THash> right = build(stream, mid + 1, end, hashCreator);

            // Calculate hash here

            SegmentTree<T, THash> result =  new SegmentTree<T, THash>(start, end, left, right);
            THash h = hashCreator.getHash(left.hash, right.hash);
            result.hash = h;

            return result;
        }

    }

}
