package strac.align.align.implementations;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.ICellComparer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Javier Cabrera-Arteaga on 2020-05-04
 */
@strac.align.align.annotations.Aligner(name="Evolutive")
public class Evolutive extends Aligner {


    public Evolutive(ICellComparer comparer) {
        super(comparer);
    }

    @Override
    public String getName() {
        return "Evolutive";
    }

    public class GapInfo{
        int discriminator;
        int position;


        public GapInfo(int discriminator, int position){
            this.discriminator = discriminator;
            this.position = position;
        }

        @Override
        public String toString() {
            return "GapInfo{" +
                    "discriminator=" + discriminator +
                    ", position=" + position +
                    '}';
        }
    }

    public class Subject{
        public ArrayList<GapInfo> gaps1;
        public ArrayList<GapInfo> gaps2;

        int max1;
        int max2;

        public double fitness;

        public Subject(ArrayList<GapInfo> gaps1, ArrayList<GapInfo> gaps2, int max1, int max2){
            this.gaps1 = gaps1;
            this.gaps2 = gaps2;
            this.max1 = max1;
            this.max2 = max2;
        }

        @Override
        public String toString() {
            return "Subject{" +
                    "fitness=" + fitness +
                    '}';
        }
    }

    public  ArrayList<Subject> mutate(Subject root, int initialSolutions, int[] trace1, int[] trace2){

        ArrayList<Subject> pop = new ArrayList<>();

        while(initialSolutions> 0){
            pop.add(selfMutate(root, trace1, trace2));
            initialSolutions--;
        }

        return pop;
    }

    Random r = new Random();
    double changeP = 0.1;

    public Subject recombine(Subject a, Subject b){
        return a;
    }

    public Subject selfMutate(Subject s, int[] trace1, int[] trace2){

        Subject n = new Subject(new ArrayList<>(), new ArrayList<>(), s.max1, s.max2);

        for(int i = 0; i < s.gaps1.size(); i++){
            GapInfo inf = s.gaps1.get(i);

            double p1 = r.nextDouble();

            if(p1 <= changeP){
                int newIndex = r.nextInt(s.max1);
                n.gaps1.add(new GapInfo(0, newIndex));
            }else{
                n.gaps1.add(inf);
            }
        }
        for(int i = 0; i < s.gaps2.size(); i++){
            GapInfo inf = s.gaps2.get(i);

            double p1 = r.nextDouble();

            if(p1 <= changeP){
                int newIndex = r.nextInt(s.max2);
                n.gaps2.add(new GapInfo(0, newIndex));
            }else{
                n.gaps2.add(inf);
            }
        }

        evaluate(n, trace1, trace2);
        return n;
    }

    public boolean gapAt(ArrayList<GapInfo> gap, int index){

        for (GapInfo gapInfo : gap)
            if (gapInfo.position == index)
                return true;
        return false;
    }

    public ArrayList<Subject> mutate(ArrayList<Subject> population){
        return population;
    }

    public void evaluate(Subject subject, int[] trace1, int[] trace2){

        double fitness = 0.0;

        int[] tr2 = new int[trace2.length + subject.gaps2.size()];
        int[] tr1 = new int[trace1.length + subject.gaps1.size()];

        int traceIndex = 0;
        int i = 0;

        while(traceIndex < trace1.length){
            if(gapAt(subject.gaps1, traceIndex))
                tr1[i]  = -1; //gap
            else {
                tr1[i] = trace1[traceIndex];
            }
            i++;
            traceIndex++;

        }

        i = 0;
        traceIndex = 0;
        while(traceIndex < trace2.length){
            if(gapAt(subject.gaps2, traceIndex))
                tr2[i]  = -1; //gap
            else {
                tr2[i] = trace2[traceIndex];
            }
            i++;
            traceIndex++;
        }

        for(i = 0; i < tr2.length; i++){
            if(tr1[i] == -1)
                fitness += comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.X);
            else if(tr2[i] == -1)
                fitness += comparer.gapCost(i, ICellComparer.TRACE_DISCRIMINATOR.Y);
            else
                fitness += comparer.compare(tr1[i], tr2[i]);
        }

        subject.fitness = fitness;
    }

    @Override
    public AlignDistance align(int[] trace1, int[] trace2) {

        int initialGaps = 0;
        //int discriminator = 0;
        int additiionalGaps = 10;

        //int size1 = trace1.length;
        //int size2 = trace2.length;


        ArrayList<GapInfo> gaps1 = new ArrayList<>(additiionalGaps);
        ArrayList<GapInfo> gaps2 = new ArrayList<>(additiionalGaps);

        if(trace1.length > trace2.length){
            initialGaps = trace1.length - trace2.length;

            //size2 = trace2.length + initialGaps+ additiionalGaps;
            //size1 = trace1.length + additiionalGaps;


            for(int i = 0; i < initialGaps + additiionalGaps; i++)
                gaps2.add(new GapInfo(1, trace2.length + i ));

            for(int i = 0; i < additiionalGaps; i++)
                gaps1.add(new GapInfo(0, trace1.length + i ));
        }
        else{
            initialGaps = trace2.length - trace1.length;

            //size2 = trace2.length + additiionalGaps;
            //size1 = trace1.length + additiionalGaps + initialGaps;

            for(int i = 0; i < initialGaps + additiionalGaps; i++)
                gaps1.add(new GapInfo(0, trace1.length + i ));

            for(int i = 0; i < additiionalGaps; i++)
                gaps2.add(new GapInfo(1, trace2.length + i ));
        }


        Subject root = new Subject(gaps1, gaps2, trace1.length, trace2.length);


        ArrayList<Subject> population = mutate(root, 100, trace1, trace2);


        int iterations = 10;

        while(iterations-- > 0){

            population = mutate(population);
        }

        /*
        int[] trace1Ga = new int[size1];
        int[] trace2Ga = new int[size2];

        // Copy orginal to traces
        System.arraycopy(trace1, 0, trace1Ga, 0, size1);
        System.arraycopy(trace2, 0, trace2Ga, 0, size2);
        // Initial solution is padding*/


        // Initialize gaps information

        return null;
    }
}