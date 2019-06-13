package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IReadArray;

import java.lang.reflect.Array;
import java.util.*;

import static core.utils.DWTHelper.draw;

public class WindowedDTW extends Aligner {
    @Override
    public String getName() {
        return "Windowed DTW";
    }

    private ICellComparer comparer;

    public WindowedDTW(int gap, ICellComparer comparer){
        super(gap);
        this.comparer = comparer;
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {
        return this.align(trace1, trace2, null);
    }

    static long getMin(List<Long> ls){
        long result = ls.get(0);

        for(long l: ls)
            if(result > l)
                result = l;

        return result;
    }

    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2, EmptyMap window) {


        EmptyMap map = window;

        if(window == null)
            map = createWindow(trace1.size() + 1, trace2.size() + 1);


        long oo = Integer.MAX_VALUE;


        WindowMap<Long> D = new WindowMap<>();

        D.set(0, 0, 0l);
        //D.set((int)trace1.size(), (int)trace2.size(), -1l);


        for(Integer j: map.getRow(0)){
            D.set(0, j, (long)j*gap);
        }

        for(Integer i: map.getColumns()){
            D.set(i, 0, (long)i*gap);
        }

        for(Integer i : map.getColumns()){

            if(i > 0 && i <= trace1.size())
                for(Integer j: map.getRow(i)){

                    if(j > 0 && j <= trace2.size()) {
                        int nI = i;
                        int nJ = j;

                        long dt = comparer.compare(trace1.read(i - 1), trace2.read(j - 1));

                        List<Long> values = new ArrayList<>(3);

                        if(D.existColumn(nI - 1) && D.existRow(nI - 1, nJ - 1))
                            values.add(D.get(nI - 1, nJ - 1) + dt);

                        if(D.existColumn(nI - 1) && D.existRow(nI - 1, nJ))
                            values.add(D.get(nI - 1, nJ) + gap);

                        if(D.existColumn(nI) && D.existRow(nI, nJ - 1))
                            values.add(D.get(nI, nJ - 1) + gap);

                        if(values.size() == 0){
                            LogProvider.info(nI, nJ, trace1.size(), trace2.size());

                            draw(window, "test_window", (int)trace2.size(), (int)trace1.size());
                            draw(D, "test_D", (int)trace2.size(), (int)trace1.size(), nJ, nI);

                            throw new RuntimeException("Error");
                        }

                        if(values.size() > 0)
                            D.set(i, j, getMin(values));
                    }
                }
        }


        //draw(D,  String.format("%s_%s.png", trace1.size(), trace2.size()), (int)trace2.size(), (int)trace1.size());

        /*for(int i = 0; i <= trace1.size(); i++){

            System.out.print(String.format("%04d ", i));

            for(int j =0; j<= trace2.size(); j++){


                System.out.print(String.format("%01d", D.getOrDefault(i, j, 0)) + " ");


            }

            System.out.println();
        }*/


        int i = (int)trace1.size();
        int j = (int)trace2.size();

        IArray<InsertOperation> ops = ServiceRegister.getProvider().allocateNewArray(null, trace1.size()+trace2.size() + 2, InsertOperation.OperationAdapter);


        long position = 0;

        while ((i > 0) || (j > 0)) // 0,0 item
        {

            long diag = oo;
            long left = oo;
            long up = oo;

            if(j > 0 && i > 0){
                diag = D.getOrDefault(i - 1, j - 1, oo) + comparer.compare(trace1.read(i - 1), trace2.read(j - 1));
            }

            if( j > 0){
                left = D.getOrDefault(i, j - 1, oo) + getGapSymbol();
            }


            if( i > 0){
                up = D.getOrDefault(i - 1, j, oo) + getGapSymbol();
            }

            if(diag <= left && diag <= up){
                i--;
                j--;
            }
            else if(left < diag && left < up){
                j--;
            }
            else if(up < left){
                i--;
            }
            else if (i <= j)
                j--;
            else
                i--;

            //LogProvider.info(i, j);
            ops.set(position++,new InsertOperation(i, j));
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
        Long val = D.get((int)trace1.size(), (int)trace2.size());
        //draw(D, "merde.png", (int)trace2.size() + 1, (int)trace1.size() + 1);
        if(val == null){
            //
            throw new RuntimeException("Ahhh");
        }

        return new AlignDistance(val, ops);
    }


    public EmptyMap createWindow(long maxI, long maxJ){

        EmptyMap map = new EmptyMap();

        for(int i = 1; i < maxI; i ++){
            for(int j = 1; j < maxJ; j++){
                map.set(i, j);
            }
        }

        return map;
    }


    public static class EmptyMap{
        Map<Integer, Set<Integer>> map;


        public EmptyMap(){
            this.map = new TreeMap<>();

        }

        public boolean existColumn(int i)
        {
            return this.map.containsKey(i);
        }

        public boolean existRow(int i, int j){
            return this.map.get(i).contains(j);
        }

        public void set(int i, int j){

            if(i < 0 || j < 0)
                throw new RuntimeException("Invalid indexes " + i + " " + j);

            if(!this.map.containsKey(i))
                this.map.put(i, new TreeSet<>());

            this.map.get(i).add(j);
        }

        public Iterable<Integer> getColumns(){
            return this.map.keySet();
        }

        public Iterable<Integer> getRow(int column){
            return this.map.get(column);
        }
    }

    public static class WindowMap<T>{

        Map<Integer, Map<Integer, T>> map;


        public WindowMap(){
            this.map = new HashMap<>();

        }

        public boolean existColumn(int i)
        {
            return this.map.containsKey(i);
        }

        public boolean existRow(int i, int j){
            return this.map.get(i).containsKey(j);
        }

        public void set(int i, int j, T value){

            if(i < 0 || j < 0)
                throw new RuntimeException("Invalid indexes " + i + " " + j);

            if(!this.map.containsKey(i))
                this.map.put(i, new HashMap<>());

            this.map.get(i).put(j, value);
        }

        public T get(int i, int j){

            return this.map.get(i).get(j);
        }

        public T getOrDefault(int i, int j, T def){

            if(this.existColumn(i) && this.existRow(i, j))
                return this.map.get(i).get(j);

            return def;
        }

        public Iterable<Integer> getColumns(){
            return this.map.keySet();
        }

        public Iterable<Integer> getRow(int column){
            return this.map.get(column).keySet();
        }

        public void removeRow(int row){
            this.map.remove(row);
        }

        public void removeCol(int row, int col){
            this.map.get(row).remove(col);
        }

    }

}
