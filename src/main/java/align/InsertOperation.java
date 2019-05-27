package align;

public class InsertOperation {

    private int trace1Index;

    private int trace2Index;

    public InsertOperation(int trace1Index, int trace2Index){
        this.trace1Index = trace1Index;
        this.trace2Index = trace2Index;
    }

    public int getTraceIndex(){
        return trace1Index;
    }

    public int getGapIndex(){
        return trace2Index;
    }


    @Override
    public String toString() {
        return String.format("%s %s", this.trace1Index, this.trace2Index);
    }
}
