package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IReadArray;

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
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {
        return this.align(trace1, trace2, null);
    }

    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2, WindowMap<Boolean> window) {


        WindowMap<Boolean> map = window;

        if(window == null)
            map = createWindow(trace1.size() + 1, trace2.size() + 1);


        int oo = 1000000000;

        WindowMap<Integer> D = new WindowMap<>();

        D.set(0, 0, 0);


        for(int i : map.getColumns()){
            for(int j: map.getRow(i)){

                int dt = comparer.compare(trace1.read(i - 1), trace2.read(j - 1));
                int diag = D.getOrDefault(i - 1, j - 1, 0);
                int left = D.getOrDefault(i, j - 1, 0);
                int up = D.getOrDefault(i  - 1, j, 0);

                D.set(i, j, Math.max(diag + dt,
                        Math.max(left + getGapSymbol(),
                                up + getGapSymbol())) );
            }
        }



        /*for(int i = 0; i <= trace1.size(); i++){

            System.out.print(String.format("%04d ", i));

            for(int j =0; j<= trace2.size(); j++){


                System.out.print(String.format("%01d", D.getOrDefault(i, j, 0)) + " ");


            }

            System.out.println();
        }*/


        int i = trace1.size();
        int j = trace2.size();

        IArray<InsertOperation> ops = ServiceRegister.getProvider().allocateNewArray(null, 1000000, InsertOperation.OperationAdapter);


        while ((i > 0) || (j > 0)) // 0,0 item
        {

            int diag = -oo;
            int left = -oo;
            int up = -oo;

            if(j > 0 && i > 0){
                diag = D.getOrDefault(i - 1, j - 1, -oo) + comparer.compare(trace1.read(i - 1), trace2.read(j - 1));
            }

            if( j > 0){
                left = D.getOrDefault(i, j - 1, -oo) + getGapSymbol();
            }


            if( i > 0){
                up = D.getOrDefault(i - 1, j, -oo) + getGapSymbol();
            }

            if(diag >= left && diag >= up){
                i--;
                j--;
            }
            else if(left > diag && left > up){
                j--;
            }
            else if(up > left){
                i--;
            }
            else if (i <= j)
                j--;
            else
                i--;

            //LogProvider.info(i, j);
            ops.add(new InsertOperation(i, j));
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
        return new AlignDistance(D.getOrDefault(trace1.size(), trace2.size(), 0), ops);
    }


    public WindowMap<Boolean> createWindow(int maxI, int maxJ){

        WindowMap<Boolean> map = new WindowMap<>();

        for(int i = 1; i < maxI; i ++){
            for(int j = 1; j < maxJ; j++){
                map.set(i, j, true);
            }
        }

        return map;
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

    }

}
