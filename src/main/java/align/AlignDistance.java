package align;

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

}
