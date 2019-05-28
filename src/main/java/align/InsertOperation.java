package align;

public class InsertOperation {

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
}
