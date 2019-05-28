package core.utils;

import align.InsertOperation;
import core.LogProvider;

import java.util.ArrayList;
import java.util.List;

public class DWTHelper {

    public static boolean existsCell(int i, int j, List<InsertOperation> ops){

        for(InsertOperation op: ops){
            if(op.getTrace1Index() == i && op.getTrace2Index() == j)
                return true;
        }

        return false;
    }



    public static List<InsertOperation> scalePath(List<InsertOperation> ops, int factor, int maxI, int maxJ){

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

}
