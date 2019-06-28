package core.data_structures.buffered;

public class BufferedDictionary<TKey extends Comparable<TKey>, TValue> {

    BufferedCollection<INode> collection;
    long count;

    public BufferedDictionary(BufferedCollection<INode> bufferedCollection){
        this.collection = bufferedCollection;

        this.count = 0;

    }


    public void insert(TKey key, TValue val){

        if(this.count == 0){//Create as root
            INode node = new INode(key, val, 0);
            this.collection.set(count++, node);
            node.save();
        }
        else{
            this.collection.read(0).insert(key, val);
        }
    }

    public TValue search(TKey key){
        if(count == 0)
            return null;

        return collection.read(0).search(key);
    }



    public final class INode{

        public long left;
        public long right;

        public TKey key;
        public TValue value;
        public long position;


        public INode(TKey key, TValue val, long position){
            this.key = key;
            this.value = val;

            this.position = position;
        }

        private TValue search(TKey key){
            if(key.compareTo(this.key) == 0)
                return this.value;

            if(this.key.compareTo(key) > 0 && this.left != 0)
                return collection.read(this.left).search(key);

            if(this.key.compareTo(key) < 0 && this.right != 0)
                return collection.read(this.right).search(key);

            return null;
        }

        private void insert(TKey key, TValue val){
            if(this.key.compareTo(key) == 0){
                this.value = val;
            }
            else{
                if(this.key.compareTo(key) > 0){
                    if(this.left != 0)
                        collection.read(this.left).insert(key, value);
                    else
                    {
                        long newPos = count++;
                        INode left  = new INode(key, val, newPos);
                        this.left = newPos;

                        left.save();
                    }
                }
                if(this.key.compareTo(key) < 0){
                    if(this.right != 0)
                        collection.read(this.right).insert(key, value);
                    else
                    {
                        long newPos = count++;
                        INode right  = new INode(key, val, newPos);
                        this.right = newPos;

                        right.save();
                    }
                }

            }
            this.save();
        }

        void save(){
            collection.set(this.position, this);
        }
    }
}
