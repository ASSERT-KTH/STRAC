package align;

import core.data_structures.IArray;
import core.data_structures.IReadArray;

import java.util.List;

public class AlignDistance {

    IArray<InsertOperation> operations;
    public IArray<Integer> costMatrix;

    public long operationsCount;

    double distance;

    public AlignDistance(double distance, IArray<InsertOperation> ops, long operationsCount){
        this.operations = ops;
        this.distance = distance;
        this.operationsCount = operationsCount;
    }


    public double getDistance(){
        return distance;
    }

    public IArray<InsertOperation> getInsertions(){
        return operations;
    }

    public <T>  double getDistance(IReadArray<T> trace1, IReadArray<T> trace2, IAlignComparer<T> comparer){

        double result = 0;


        for(InsertOperation op: operations){
                result += comparer.compare(trace1.read(op.getTrace2Index()), trace2.read(op.getTrace2Index()));

        }

        return result;
    }

}
