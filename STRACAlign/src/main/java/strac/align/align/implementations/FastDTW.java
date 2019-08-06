package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;
import strac.core.LogProvider;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IReadArray;
import strac.core.utils.ArrayHelper;
import strac.align.utils.AlignServiceProvider;
import strac.align.utils.DWTHelper;
import strac.core.utils.TimeUtils;


@strac.align.align.annotations.Aligner(name="FastDTW")
public class FastDTW extends Aligner {


    private int radius = 2;

    private DTW standard;
    private WindowedDTW windowed;


    public FastDTW(ICellComparer comparer, Double radius){
        super(comparer);
        this.radius = radius.intValue();
        this.comparer = comparer;

        this.standard = new DTW(comparer);
        this.windowed = new WindowedDTW(comparer);
    }

    @Override
    public String getName() {
        return "FastDTW";
    }

    @Override
    public AlignDistance align(IReadArray<Integer> trace1, IReadArray<Integer> trace2) {
        int minTimeSize = 2 + this.radius;
        LogProvider.info("Starting...", trace1.size(), trace2.size());

        if(trace1.size() <= minTimeSize || trace2.size() <= minTimeSize){
            return this.standard.align(trace1, trace2); // O(n)
        }
        else{

            TimeUtils utl = new TimeUtils();

            long halfSize1 = trace1.size()/2;
            long halfSize2 = trace2.size()/2;

            IArray<Integer> reduced1 = AlignServiceProvider.getInstance().getProvider().allocateIntegerArray
                    (null, halfSize1,
                            AlignServiceProvider.getInstance().getProvider().selectMethod(4*halfSize1)
                            );

            ArrayHelper.reduceByHalf(trace1, reduced1,
                    ArrayHelper::getMostFequentRepresentation); // O(n)

            IArray<Integer> reduced2 = AlignServiceProvider.getInstance().getProvider().allocateIntegerArray(
                    null, halfSize2,
                    AlignServiceProvider.getInstance().getProvider().selectMethod(4*halfSize2));

            ArrayHelper.reduceByHalf(trace2, reduced2,
                    ArrayHelper::getMostFequentRepresentation); // O(n)




            AlignDistance distance = this.align(reduced1, reduced2); //O(n/2)

            LogProvider.info("Growing to",  trace1.size(), trace2.size() , "from", halfSize1 + 1, halfSize2 + 1);


            utl.reset();
            reduced1.dispose();
            reduced2.dispose();
            utl.time("Disposing");
            utl.reset();

            WindowedDTW.Window window = DWTHelper.expandWindow(distance.getInsertions(), radius, trace1.size(), trace2.size() ,
                    distance.operationsCount, distance.minI, distance.minJ); // O(n)


            utl.time("Expanding total");
            //LogProvider.info();
            //LogProvider.info(trace1);
            //LogProvider.info(trace2);
            //LogProvider.info(growUp);

            distance.getInsertions().dispose();


            utl.reset();
            AlignDistance result = this.windowed.align(trace1, trace2, window);
            utl.time("Calculating");

            window.dispose();

            return result;
        }

    }
}