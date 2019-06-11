package align;

import com.google.gson.Gson;
import core.data_structures.buffered.BufferedCollection;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class InsertOperation implements Serializable {

    private long i;

    private long j;

    private int valueInCost;

    public InsertOperation(long trace1Index, long trace2Index){
        this.i = trace1Index;
        this.j = trace2Index;
    }

    public long getTrace1Index(){
        return i;
    }

    public int getValueInCost(){
        return valueInCost;
    }

    public void setValueInCost(int value){
        this.valueInCost = value;
    }

    public long getTrace2Index(){
        return j;
    }


    @Override
    public String toString() {
        return String.format("%s %s", this.i, this.j);
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
