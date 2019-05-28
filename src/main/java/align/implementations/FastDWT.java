package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.LogProvider;
import core.utils.ArrayHelper;
import core.utils.DWTHelper;
import core.utils.TimeUtils;

import java.util.List;

public class FastDWT extends Aligner {


    private int radius = 2;
    private ICellComparer comparer;

    private DWT standard;
    private WindowedDWT windowed;


    public FastDWT(int radius, ICellComparer comparer){
        this.radius = radius;
        this.comparer = comparer;

        this.standard = new DWT(comparer);
        this.windowed = new WindowedDWT(comparer);
    }

    @Override
    public String getName() {
        return "FastDWT";
    }

    @Override
    public AlignDistance align(List<Integer> trace1, List<Integer> trace2) {
        int minTimeSize = 2 + this.radius;


        if(trace1.size() <= minTimeSize || trace2.size() <= minTimeSize){
            return this.standard.align(trace1, trace2); // O(n)
        }
        else{

            TimeUtils utl = new TimeUtils();

            List<Integer> reduced1 = ArrayHelper.reduceSize(trace1, trace1.size()/2
                    , ArrayHelper::getMostFequentRepresentation); // O(n)

            List<Integer> reduced2 = ArrayHelper.reduceSize(trace2
                    , trace2.size()/2, ArrayHelper::getMostFequentRepresentation); // O(n)



            AlignDistance distance = this.align(reduced1, reduced2); //O(n/2)

            LogProvider.info("Expanding");
            utl.reset();

            List<InsertOperation> growUp = DWTHelper.scalePath(distance.getInsertions(), 2, trace1.size(), trace2.size()); // O(n)


            utl.time();


            List<InsertOperation> window = DWTHelper.createWindow(growUp,
                    radius, trace1.size(), trace2.size()); // O(n)

            LogProvider.info(trace1.size(), trace2.size());

            utl.time();


            //LogProvider.info();
            //LogProvider.info(trace1);
            //LogProvider.info(trace2);
            //LogProvider.info(growUp);


            LogProvider.info(trace1.size(), trace2.size());

            return this.windowed.align(trace1, trace2, window);
        }

    }
}
