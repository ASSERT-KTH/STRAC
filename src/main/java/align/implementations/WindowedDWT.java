package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.LogProvider;
import core.utils.HashingHelper;

import java.util.*;

public class WindowedDWT extends Aligner {
    @Override
    public String getName() {
        return "Windowed DWT";
    }

    private ICellComparer comparer;

    public WindowedDWT(ICellComparer comparer){
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(List<Integer> trace1, List<Integer> trace2) {
        return this.align(trace1, trace2, null);
    }

    public AlignDistance align(List<Integer> trace1, List<Integer> trace2, List<InsertOperation> window) {

        if(trace1.size() > trace2.size()){
            List<Integer> tmp = trace2;
            trace2 = trace1;
            trace1 = tmp;
        }

        WindowMap map = new WindowMap();

        if(window == null)
            map = createWindow(trace1.size() + 1, trace2.size() + 1);
        else {
            map.set(trace1.size(), trace2.size(), 0);
            map.set(0, 0, 0);

            for (InsertOperation op : window) {
                map.set(op.getTrace1Index(), op.getTrace2Index(), 0);
            }

        }


        for(int i: map.getColumns()){
            if(map.existColumn(i - 1) && map.existRow(i - 1, 0))
                map.set(i, 0, map.get(i - 1, 0) + this.getGapSymbol());

        }

        if(map.existColumn(0))
            map.set(0,0,0);
            for(int j = 0; j < trace2.size(); j++){
                if(map.existRow(0, j - 1)){
                    map.set(0, j, map.get(0, j - 1) + this.getGapSymbol());
                }
            }




        for(int i : map.getColumns()){
            for(int j: map.getRow(i)){

                if(j == 0 || i == 0)
                    continue;


                boolean evaluateLeft = map.existRow(i, j - 1) && map.existRow(i, j - 1);
                boolean evaluateUpper = map.existColumn(i - 1) && map.existRow(i - 1, j);
                boolean evaluateDiag = map.existColumn(i - 1) && map.existRow(i - 1, j - 1) && (i -1 < trace1.size() && j - 1 < trace2.size());

                int max = Integer.MIN_VALUE;

                if(evaluateLeft)
                    max = Math.max(max, map.get(i, j - 1) + getGapSymbol());

                if(evaluateUpper)
                    max = Math.max(max, map.get(i - 1, j ) + getGapSymbol());

                if(evaluateDiag) {
                    max = Math.max(max, map.get(i - 1, j - 1) + comparer.compare(trace1.get(i - 1),
                            trace2.get(j - 1)));
                }

                if(max != Integer.MIN_VALUE && i >= 0 && j >= 0)
                    map.set(i, j, max);
            }
        }



        for(int i = 0; i < trace1.size(); i++){

            System.out.print(String.format("%04d ", i));

            for(int j =0; j< trace2.size(); j++){

                if(map.existColumn(i) && map.existRow(i, j)){

                    System.out.print(String.format("@", map.get(i, j)) + " ");
                }
                else{
                    System.out.print("- ");
                }

            }

            System.out.println();
        }


        int i = trace1.size();
        int j = trace2.size();

        List<InsertOperation> ops = new ArrayList<>();

        while ((i>0) || (j>0)) // 0,0 item
        {
            final double diagCost;
            final double leftCost;
            final double downCost;

            if (map.existColumn(i - 1) && map.existRow(i - 1, j - 1))
                diagCost = map.get(i - 1, j - 1) + this.comparer.compare(trace1.get(i - 1), trace2.get(j - 1));
            else
                diagCost = Double.NEGATIVE_INFINITY;

            if (map.existColumn(i - 1) && map.existRow(i - 1, j))
                leftCost = map.get(i - 1, j) + this.getGapSymbol();
            else
                leftCost = Double.NEGATIVE_INFINITY;

            if (map.existColumn(i) && map.existRow(i, j - 1))
                downCost = map.get(i, j - 1) + this.getGapSymbol();
            else
                downCost = Double.NEGATIVE_INFINITY;

            /*if(diagCost == Double.NEGATIVE_INFINITY && leftCost == Double.NEGATIVE_INFINITY && downCost == Double.NEGATIVE_INFINITY)
                throw new RuntimeException("Impossible " + i + " " + j);*/

            if ((diagCost >= leftCost) && (diagCost >= downCost))
            {
                i--;
                j--;
            }
            else if ((leftCost > diagCost) && (leftCost > downCost))
                i--;
            else if ((downCost > diagCost) && (downCost > leftCost))
                j--;
            else if (i <= j)
                j--;
            else
                i--;

            ops.add(0, new InsertOperation(Math.max(i, 0), Math.max(j, 0)));
        }


        /*System.out.println(" ");

        for(int x: map.getColumns()){

            System.out.print(x  + " -> ");
            for(int y : map.getRow(x)){
                System.out.print(y + "(" + map.get(x, y) + ") ");

            }

            System.out.println();
        }*/

        /*System.out.println(trace1.size() - 1);
        System.out.println(trace2.size() - 1);
        LogProvider.info(ops);*/
        return new AlignDistance(map.get(trace1.size(), trace2.size()), ops);
    }


    public WindowMap createWindow(int maxI, int maxJ){

        WindowMap map = new WindowMap();

        for(int i = 0; i < maxI; i ++){
            for(int j = 0; j < maxJ; j++){
                map.set(i, j, 0);
            }
        }

        return map;
    }

    public class WindowMap{

        Map<Integer, Map<Integer, Integer>> map;

        Map<Integer, Integer> high;

        public WindowMap(){
            this.map = new HashMap<>();
            this.high = new HashMap<>();
        }

        public boolean existColumn(int i)
        {
            return this.map.containsKey(i);
        }

        public boolean existRow(int i, int j){
            return this.map.get(i).containsKey(j);
        }

        public void set(int i, int j, int value){

            if(i < 0 || j < 0)
                throw new RuntimeException("Invalid indexes " + i + " " + j);

            if(!this.map.containsKey(i))
                this.map.put(i, new HashMap<>());

            this.map.get(i).put(j, value);
        }

        public int get(int i, int j){
            return this.map.get(i).get(j);
        }

        public Iterable<Integer> getColumns(){
            return this.map.keySet();
        }

        public Iterable<Integer> getRow(int column){
            return this.map.get(column).keySet();
        }

        public int getHeight(){
            return this.map.keySet().size();
        }

        public int getWidth(int i ){
            return this.map.get(i).size();
        }


    }

}
