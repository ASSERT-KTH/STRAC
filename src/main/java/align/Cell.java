package align;

import com.google.gson.Gson;
import core.data_structures.buffered.BufferedCollectionInteger;

import java.io.Serializable;
import java.util.Arrays;

public class Cell implements Serializable {

    private int i;

    private int j;

    private int valueInCost;

    public Cell(int trace1Index, int trace2Index){
        this.i = trace1Index;
        this.j = trace2Index;
    }

    public int getTrace1Index(){
        return i;
    }

    public int getValueInCost(){
        return valueInCost;
    }

    public void setValueInCost(int value){
        this.valueInCost = value;
    }

    public int getTrace2Index(){
        return j;
    }


    @Override
    public String toString() {
        return String.format("%s %s", this.i, this.j);
    }

    public interface  ITypeAdaptor<T> {
       T fromBytes(byte[] chunk);

        byte[] toBytes(T i);

        int size();

        Class<T> clazz();
    }

    public static ITypeAdaptor<Cell> CELL_ADAPTOR = new ITypeAdaptor<Cell>() {
        @Override
        public Cell fromBytes(byte[] chunk) {

            int i = 0;
            for(i = 0; i < chunk.length && chunk[i] > 0; i++);

            String js = new String(chunk, 0, i);

            return new Gson().fromJson(js, Cell.class);
        }

        @Override
        public byte[] toBytes(Cell i) {

            byte[] data = Arrays.copyOf(new Gson().toJson(i).getBytes(), size());
            return data;
        }

        @Override
        public int size() {
            return 512;
        }

        @Override
        public Class<Cell> clazz() {
            return Cell.class;
        }
    };
}
