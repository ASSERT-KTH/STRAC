package core.models;

import java.util.ArrayList;
import java.util.List;

public class ComparisonDto {

    double[][] map;

    public List<String> traces;

    public ComparisonDto(int size){

        this.map = new double[size][size];
        traces = new ArrayList<>();
    }

    public ComparisonDto(int height, int width)
    {

        this.map = new double[height][width];
        traces = new ArrayList<>();
    }

    public void set(int row, int column, double value){

        this.map[row][column] = value;
        traces = new ArrayList<>();
    }
}
