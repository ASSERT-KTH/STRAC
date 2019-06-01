package core.models;

public class ComparisonDto {

    double[][] map;

    public ComparisonDto(int size){
        this.map = new double[size][size];
    }

    public ComparisonDto(int height, int width)
    {
        this.map = new double[height][width];
    }

    public void set(int row, int column, double value){
        this.map[row][column] = value;
    }
}
