package core.data_structures.memory;

import core.data_structures.ISet;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InMemorySet<T, A extends Set<T>> implements ISet<T> {

    private A items;
    private Class<? extends A> clazz;

    public InMemorySet(Class<? extends A> clazz){
        try {
            this.items = clazz.getDeclaredConstructor().newInstance();
            this.clazz = clazz;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public InMemorySet(A seed){
        this((Class<? extends A>)seed.getClass());
        this.items.addAll(seed);
    }

    @Override
    public void add(T item) {
        this.items.add(item);
    }

    @Override
    public boolean contains(T item) {
        return items.contains(item);
    }

    @Override
    public ISet<T> intersect(ISet<T> setB) {


        ISet<T> tmp = new InMemorySet<T, A>(this.clazz);


        for(T x: this.items) // O(n)
            if (setB.contains(x)) // O(1) if set is implemented with HashSet
                tmp.add(x);


        return tmp;

    }

    @Override
    public ISet<T> union(ISet<T> set) {
        InMemorySet<T, A> result = new InMemorySet<T, A>(this.items);

        result.items.addAll(set.getKeys());

        return result;
    }

    @Override
    public ISet<T> difference(ISet<T> set) {

        InMemorySet<T, A> result = new InMemorySet<>(this.clazz);

        for(T t1: this.getKeys()){
            if(!set.contains(t1))
                result.add(t1);
        }

        return result;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public Collection<T> getKeys() {
        return this.items;
    }


    /*


    public static <T> Set<T> union(Set<T> setA, Set<T> setB){
         Set<T> result = new HashSet<>(setA);

         result.addAll(setB);

         return result;
    }
    * */
}
