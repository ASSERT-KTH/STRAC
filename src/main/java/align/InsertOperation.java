package align;

import com.google.gson.Gson;
import core.data_structures.buffered.BufferedCollection;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class InsertOperation implements Serializable {

    private int trace1Index;

    private int trace2Index;

    private int valueInCost;

    public InsertOperation(int trace1Index, int trace2Index){
        this.trace1Index = trace1Index;
        this.trace2Index = trace2Index;
    }

    public int getTrace1Index(){
        return trace1Index;
    }

    public int getValueInCost(){
        return valueInCost;
    }

    public void setValueInCost(int value){
        this.valueInCost = value;
    }

    public int getTrace2Index(){
        return trace2Index;
    }


    @Override
    public String toString() {
        return String.format("%s %s", this.trace1Index, this.trace2Index);
    }

    public static BufferedCollection.ITypeAdaptor<InsertOperation> OperationAdapter = new BufferedCollection.ITypeAdaptor<InsertOperation>() {
        @Override
        public InsertOperation fromBytes(byte[] chunk) {

            int i = 0;
            for(i = 0; i < chunk.length && chunk[i] > 0; i++);

            String js = new String(chunk, 0, i);

            return new Gson().fromJson(js, InsertOperation.class);
        }

        @Override
        public byte[] toBytes(InsertOperation i) {

            byte[] data = Arrays.copyOf(new Gson().toJson(i).getBytes(), size());
            return data;
        }

        @Override
        public int size() {
            return 500;
        }
    };
}
