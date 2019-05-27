package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.InsertOperation;

import java.util.List;

public class FastDWT extends Aligner {


    private int radius = 2;

    public FastDWT(int radius){
        this.radius = radius;
    }

    @Override
    public String getName() {
        return "FastDWT";
    }

    @Override
    public AlignDistance align(List<Integer> trace1, List<Integer> trace2) {
        int minTimeSize = 2 + this.radius;


        return null;
    }
}
