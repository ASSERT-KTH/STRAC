package align.data_structures;


import align.implementations.WindowedDTW;
import core.data_structures.IWindow;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DoubleCostMatrix extends BufferedCostMatrix<Double> {

    public DoubleCostMatrix(String fileName, long maxI, long maxJ, WindowedDTW.Window window) {
        super(fileName, maxI, maxJ, window);
    }

    public DoubleCostMatrix(String fileName, long maxI, long maxJ) {
        super(fileName, maxI, maxJ);
    }

    @Override
    public Double[] allocateArray(int size) {
        return new Double[size];
    }

    @Override
    Double readFromFile(RandomAccessFile file) throws IOException {
        return file.readDouble();
    }

    @Override
    void setToFile( Double value, ByteBuffer buff) {
        if(value != null)
            buff.putDouble(value);
    }

    @Override
    int dataSize() {
        return 8;
    }
}
