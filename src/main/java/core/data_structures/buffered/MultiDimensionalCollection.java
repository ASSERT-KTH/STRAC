package core.data_structures.buffered;

public class MultiDimensionalCollection<T> extends BufferedCollection<T> {


    int[] dimensions;

    public MultiDimensionalCollection(String fileName, ITypeAdaptor<T> adaptor, int...dimensions) {
        super(fileName, multiply(dimensions), adaptor);

        this.dimensions = dimensions;
    }

    public T get(int...index){
        return super.read(getPosition(index));
    }

    public void set(T value, int...index){
        super.add(getPosition(index), value);
    }

    int getPosition(int...index){

        int result = 0;

        int cumul = 1;
        int i = 0;

        for(i = index.length - 1; i > 0; i--){
            result += index[i]*cumul;
            cumul *= dimensions[i];
        }

        result += index[i]*cumul;

        return result;
    }

    static int multiply(int[] dimensions){
        int result = 1;

        for(int i : dimensions)
            result *= i;

        return result;
    }
}
