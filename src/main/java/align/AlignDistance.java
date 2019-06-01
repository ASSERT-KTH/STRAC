package align;

import core.data_structures.IReadArray;

import java.util.List;

public class AlignDistance {

    List<InsertOperation> operations;

    double distance;

    public AlignDistance(double distance, List<InsertOperation> ops){
        this.operations = ops;
        this.distance = distance;
    }


    public double getDistance(){
        return distance;
    }

    public List<InsertOperation> getInsertions(){
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
