package strac.align.align;

import strac.core.data_structures.IArray;
import strac.core.data_structures.IReadArray;

public class AlignDistance {

    IArray<Cell> operations;
    public int minI;
    public int minJ;

    public IArray<Integer> costMatrix;

    public long operationsCount;

    double distance;

    public AlignDistance(double distance, IArray<Cell> ops, int minI, int minJ, long operationsCount){
        this.operations = ops;
        this.distance = distance;
        this.operationsCount = operationsCount;

        this.minI = minI;
        this.minJ = minJ;
    }


    public double getDistance(){
        return distance;
    }

    public IArray<Cell> getInsertions(){
        return operations;
    }

    public <T>  double getDistance(IReadArray<T> trace1, IReadArray<T> trace2, IAlignComparer<T> comparer){

        double result = 0;


        for(Cell op: operations){
                result += comparer.compare(trace1.read(op.getTrace2Index()), trace2.read(op.getTrace2Index()));

        }

        return result;
    }

}
