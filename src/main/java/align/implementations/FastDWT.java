package align.implementations;

import align.AlignDistance;
import align.Aligner;
import align.ICellComparer;
import align.InsertOperation;
import core.LogProvider;
import core.ServiceRegister;
import core.data_structures.IArray;
import core.data_structures.IReadArray;
import core.utils.ArrayHelper;
import core.utils.DWTHelper;
import core.utils.TimeUtils;

import java.util.List;

import static core.utils.HashingHelper.getRandomName;

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
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {
        int minTimeSize = 2 + this.radius;

        if(trace1.size() <= minTimeSize || trace2.size() <= minTimeSize){
            return this.standard.align(trace1, trace2); // O(n)
        }
        else{

            TimeUtils utl = new TimeUtils();

            IArray<Integer> reduced1 = ServiceRegister.getProvider().allocateNewArray(getRandomName(), Integer.class);
            ArrayHelper.reduceByHalf(trace1, reduced1,
                    ArrayHelper::getMostFequentRepresentation); // O(n)

            IArray<Integer> reduced2 = ServiceRegister.getProvider().allocateNewArray(getRandomName(), Integer.class);
            ArrayHelper.reduceByHalf(trace2, reduced2,
                    ArrayHelper::getMostFequentRepresentation); // O(n)


            AlignDistance distance = this.align(reduced1, reduced2); //O(n/2)

            LogProvider.info("Growing to",  trace1.size(), trace2.size() , "from", reduced1.size(), reduced2.size());


            utl.reset();
            reduced1.dispose();
            reduced2.dispose();
            utl.time("Disposing");
            utl.reset();

            WindowedDWT.WindowMap<WindowedDWT.CellInfo> window = DWTHelper.expandWindow(distance.getInsertions(), radius, trace1.size(),trace2.size()); // O(n)


            utl.time("Expanding total");
            //LogProvider.info();
            //LogProvider.info(trace1);
            //LogProvider.info(trace2);
            //LogProvider.info(growUp);

            distance.getInsertions().dispose();

            utl.reset();
            AlignDistance result = this.windowed.align(trace1, trace2, window);
            utl.time("Calculating");

            return result;
        }

    }
}
