package core.data_structures.segment_tree;

import core.data_structures.IArray;

public class SegmentTree<T, THash> {

    public SegmentTree<T, THash> left;
    public SegmentTree<T, THash> right;

    public int st;
    public int nd;

    public THash hash;

    private T value;


    public SegmentTree(T value, int st, int nd, SegmentTree<T, THash> left, SegmentTree<T, THash> right){
        this.st = st;
        this.nd = nd;

        this.left = left;
        this.right = right;

        this.value = value;
    }

    public SegmentTree(T value, int st, int nd){
        this.st = st;
        this.nd = nd;

        this.value = value;
    }

    public SegmentTree(int st, int nd, SegmentTree<T, THash> left, SegmentTree<T, THash> right){
        this.st = st;
        this.nd = nd;

        this.left = left;
        this.right = right;
    }

    public boolean isLeaf(){
        return st == nd;
    }


    public static  <T, THash> SegmentTree<T, THash> build(IArray<T> stream, int start, int end){

        if(start == end){
            // is Leaf
            return new SegmentTree<>(stream.read(start), start, end);
        }
        else{
            int mid = (start + end)/2;

            SegmentTree<T, THash> left = build(stream, start, mid);
            SegmentTree<T, THash> right = build(stream, mid + 1, end);

            // Calculate hash here

            return new SegmentTree<T, THash>(start, end, left, right);
        }

    }

}
