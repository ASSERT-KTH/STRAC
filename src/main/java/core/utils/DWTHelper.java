package core.utils;

import align.InsertOperation;
import align.implementations.WindowedDWT;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;

import java.util.ArrayList;
import java.util.List;

import static core.utils.HashingHelper.getRandomName;

public class DWTHelper {

    public static boolean existsCell(int i, int j, List<InsertOperation> ops){

        for(InsertOperation op: ops){
            if(op.getTrace1Index() == i && op.getTrace2Index() == j)
                return true;
        }

        return false;
    }



    public static List<InsertOperation> scalePath(List<InsertOperation> ops){

        List<InsertOperation> result = new ArrayList<>();

        //LogProvider.info(ops);
        //factor = 2*factor;


        for(int i = 0; i < ops.size(); i++){

            InsertOperation current = ops.get(i);
            int nI = current.getTrace1Index();
            int nJ = current.getTrace2Index();

            result.add(new InsertOperation(nI*2, nJ*2));
            result.add(new InsertOperation(nI*2, nJ*2 + 1));
            result.add(new InsertOperation(nI*2 + 1, nJ*2));
            result.add(new InsertOperation(nI*2 + 1, nJ*2 + 1));
        }


        /*InsertOperation last = result.get(result.size() - 1);

        if(last.getTrace1Index() != maxI ){
            // Fill to bottom corner

            int deltaX = maxI - last.getTrace1Index();

            for(int i = 1; i < deltaX; i++){
                result.add(new InsertOperation(last.getTrace1Index() + i, last.getTrace2Index()));
            }

        }

        if(last.getTrace2Index() != maxJ){
            int deltaX = maxJ - last.getTrace2Index();

            for(int i = 1; i < deltaX; i++){
                result.add(new InsertOperation( last.getTrace2Index(),last.getTrace2Index() + i));
            }

        }*/


        LogProvider.info(ops);
        LogProvider.info(result);
        return result;
    }

    public static List<InsertOperation> createWindow(List<InsertOperation> ops, int radius, int maxI, int maxJ){
        List<InsertOperation> result = new ArrayList<>();


            for(InsertOperation op: ops){

                result.add(op);
                for(int i = -radius; i <= radius; i++){

                    for(int j = -radius; j <= radius; j++) {
                        int nI = op.getTrace1Index() + i;
                        int nJ = op.getTrace2Index() + j;


                        if (nI >= 0 && nJ >= 0 && !existsCell(nI, nJ, result))
                            result.add(new InsertOperation(nI, nJ));
                    }

                }


        }


        LogProvider.info(result);
        return result;
    }

    public static WindowedDWT.WindowMap<Boolean>
    expandWindow(IArray<InsertOperation> ops, int radius, int lenT1, int lenT2){

        WindowedDWT.WindowMap<Boolean> grown = new WindowedDWT.WindowMap<Boolean>();
        WindowedDWT.WindowMap<Boolean> expansion = new WindowedDWT.WindowMap<Boolean>();

        TimeUtils utl = new TimeUtils();

        LogProvider.info("Expanding...");

        utl.reset();
        for(InsertOperation op: ops){

            expansion.set(op.getTrace1Index(), op.getTrace2Index(), true);

            for(int i = -radius; i <= radius; i++){
                for(int j = -radius; j <= radius; j++){

                    int nI = op.getTrace1Index() + i;
                    int nJ = op.getTrace2Index() + j;

                    //LogProvider.info("Size", expansion.size());
                    if(nI >= 0 && nJ >=0 && nI < lenT1 && nJ < lenT2 && (!expansion.existColumn(nI) || !expansion.existRow(nI, nJ)))
                        expansion.set(nI, nJ, true);

                }
            }

        }

        utl.time("Scaling");

        for(int i: expansion.getColumns()){
            for(int j: expansion.getRow(i)){

                grown.set(i*2, j*2, true);
                grown.set(i*2, j*2 + 1, true);
                grown.set(i*2 + 1, j*2 + 1, true);
                grown.set(i*2 + 1, j*2, true);
            }
        }

        utl.time("Growing");


        WindowedDWT.WindowMap<Boolean> result = new WindowedDWT.WindowMap<>();

        int startJ = 0;

        for(int i = 0; i < lenT1; i++){
            int newStartJ = -1;
            for(int j =startJ; j < lenT2; j++){
                if(grown.existColumn(i) && grown.existRow(i, j)){
                    result.set(i + 1, j + 1, true);

                    if(newStartJ == -1){
                        newStartJ = j;
                    }
                }
                else if (newStartJ != -1)
                    break;
            }

            startJ = newStartJ;
        }

        utl.reset();
        utl.time("Generating");
        return result;
    }

}
