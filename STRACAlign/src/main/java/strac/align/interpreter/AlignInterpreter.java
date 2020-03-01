package strac.align.interpreter;

import strac.align.align.AlignDistance;
import strac.align.align.Aligner;
import strac.align.align.Cell;
import strac.align.align.ICellComparer;
import com.google.gson.Gson;
import strac.align.interpreter.dto.UpdateDTO;
import strac.align.models.SimplePairResultDto;
import strac.align.scripts.Align;
import strac.core.LogProvider;
import strac.core.StreamProviderFactory;
import strac.core.TraceHelper;
import strac.core.data_structures.IArray;
import strac.core.data_structures.IReadArray;
import strac.align.models.AlignResultDto;
import strac.core.models.TraceMap;
import strac.align.interpreter.dto.Alignment;
import org.apache.commons.text.StringEscapeUtils;
import strac.align.utils.AlignServiceProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static strac.core.utils.HashingHelper.getRandomName;

public class AlignInterpreter {


    public AlignInterpreter(){

    }



    public interface IOnAlign{
        void action(AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize);
    }


    public void execute(Alignment dto) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        execute(dto,  null, StreamProviderFactory.getInstance());
    }

    public void execute(Alignment dto, IOnAlign action) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        execute(dto, action,StreamProviderFactory.getInstance());
    }


    public SimplePairResultDto executeSimplePair(final Alignment dto, int trace1Index, int trace2Index,final TraceHelper helper, final Aligner align, final List<TraceMap> traces){

        LogProvider.info(String.format("Executing...%s %s", trace1Index, trace2Index));

        // lock trace files

        //locks[trace1Index].lock();
        //locks[trace2Index].lock();

        SimplePairResultDto result = new SimplePairResultDto();

        TraceMap tr1 = traces.get(trace1Index);
        TraceMap tr2 = traces.get(trace2Index);

        AlignDistance distance = align.align(tr1.plainTrace, tr2.plainTrace);
        distance.getInsertions().close();

        result.distance = distance;
        result.tr1 = tr1;
        result.tr2 = tr2;
        result.trace1Index = trace1Index;
        result.trace2Index = trace2Index;

        helper.getInverseBag().put(-1, "-");
        helper.getInverseBag().put(0, "");

        try{

            if(dto.outputAlignment){


                String file1 = String.format("%s/strac.align.%s.%s", dto.outputDir, tr1.traceFileName, tr2.traceFileName);
                String file2 = String.format("%s/strac.align.%s.%s", dto.outputDir, tr2.traceFileName, tr1.traceFileName);


                long max = distance.operationsCount;

                IArray<Integer> trace1Alignment
                        = AlignServiceProvider.getInstance().getProvider().allocateIntegerArray(getRandomName(), max,
                        AlignServiceProvider.getInstance().getProvider().selectMethod(Integer.SIZE*max));

                IArray<Integer> trace2Alignment
                        = AlignServiceProvider.getInstance().getProvider().allocateIntegerArray(getRandomName(), max,
                        AlignServiceProvider.getInstance().getProvider().selectMethod(Integer.SIZE*max));


                Cell i1 = null;

                tr1.plainTrace.close();
                tr2.plainTrace.close();

                long p1 = 0;
                long p2 = 0;

                Cell i2 = null;
                for(long i = distance.operationsCount; i > 0 ; i--){

                    i2 = distance.getInsertions().read(i - 1);
                    i1 = distance.getInsertions().read(i);

                    if(i1 == null){
                        continue;
                    }

                    try {
                        //LogProvider.info(i1);

                        if(i2.getTrace1Index() > i1.getTrace1Index() && i2.getTrace2Index() > i1.getTrace2Index()){
                            trace1Alignment.set(p1,tr1.plainTrace.read(i1.getTrace1Index()));
                            trace2Alignment.set(p1,tr2.plainTrace.read(i1.getTrace2Index()));
                            p1++;
                        }
                        else if(i2.getTrace2Index() > i1.getTrace2Index()){
                            trace1Alignment.set(p1,-1);
                            trace2Alignment.set(p1,tr2.plainTrace.read(i1.getTrace2Index()));
                            p1++;
                        }
                        else if(i2.getTrace1Index() > i1.getTrace1Index() ) {
                            trace2Alignment.set(p1,-1);
                            trace1Alignment.set(p1, tr1.plainTrace.read(i1.getTrace1Index() ));
                            p1++;
                        }
                        else{
                            //throw new RuntimeException("Error");
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage());
                    }
                }

                LogProvider.info("DTW Distance", distance.getDistance());

                if(dto.outputAlignment) {


                    try {
                        LogProvider.info("Writing strac.align result to file");

                        trace1Alignment.writeTo(new FileWriter(file1), t -> {
                            if(t != null)
                                return helper.getInverseBag().get(t) + "\n";

                            return "";
                        });
                        trace2Alignment.writeTo(new FileWriter(file2), t -> {
                            if(t != null)
                                return helper.getInverseBag().get(t) + "\n";

                            return "";
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(dto.exportImage){
                    LogProvider.info("Exporting to image");
                    exportImage(
                            String.format("%s/%s_%s.png",dto.outputDir, tr1.traceFileName, tr2.traceFileName),
                            trace1Alignment, trace2Alignment);
                }

                trace1Alignment.close();
                trace2Alignment.close();
                trace1Alignment.dispose();
                trace2Alignment.dispose();
                distance.getInsertions().dispose();
            }

            //locks[trace1Index].unlock();
            //locks[trace2Index].unlock();

            return result;
        }
        catch (Exception e){

            //locks[trace1Index].unlock();
            //locks[trace2Index].unlock();

            System.err.println(String.format("i: %s, j: %s", trace1Index, trace2Index));
            e.printStackTrace();
        }

        return null;
    }

    ExecutorService executor = null;//creating a pool of x threads

    public void execute(final Alignment dto, final IOnAlign action, TraceHelper.IStreamProvider provider) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {

        //LogProvider.setCallbacker(msg -> {
            // WebsocketHandler.getInstance().sendLog(msg);
        //});

        if(dto.distanceFunctionName == null){

            LogProvider.info("Distance function not provided. We set a default one based on dto comparison data");
            dto.distanceFunctionName = "default";

            AlignServiceProvider.registerFunction("default", new ICellComparer() {
                @Override
                public int compare(int a, int b) {
                    return a != b ? dto.comparison.diff: dto.comparison.eq;
                }

                @Override
                public int gapCost(int position, TRACE_DISCRIMINATOR discriminator) {
                    return dto.comparison.gap;
                }
            });
        }


        final TraceHelper helper = new TraceHelper();

        LogProvider.info("Parsing traces");

        final List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, dto.separator == null ? "\r\n" : dto.separator, dto.clean == null? new String[0]: dto.clean, dto.include, provider , false, false);

        final Aligner align = AlignServiceProvider.getAligner(dto.method.name, dto.method.params.toArray(), AlignServiceProvider.getComparer(dto.distanceFunctionName));


        final AlignResultDto resultDto = new AlignResultDto();

        new UpdateDTO(dto, resultDto, 0);


        if(dto.pairs.size() == 0){
            LogProvider.info("No specific pairs, two vs two tournament");

            List<int[]> pairs = new ArrayList<int[]>();

            for(int i = 0; i < traces.size(); i++){
                for(int j  = i + 1; j < traces.size(); j++){
                    pairs.add(new int[] {i, j});
                }
            }

            dto.pairs = pairs;
        }

        LogProvider.info("Going to thread pool for pairwise comparison");

        if(dto.outputDir == null) {
            dto.outputDir = "out";

        }

        if(!new File(dto.outputDir).exists())
            new File(dto.outputDir).mkdir();

        executor = Executors.newFixedThreadPool(dto.threadPoolCount);

        CompletionService<SimplePairResultDto> completionService =
                new ExecutorCompletionService<>(executor);


        for(final int[] pair: dto.pairs){
            int i = pair[0], j = pair[1];

            completionService.submit(() -> executeSimplePair(dto, i, j,  helper, align, traces));
        }

        resultDto.method = dto.method;

        int received = 0;

        String pairs = "";


        // Send update to WEBSOCKET channel

        while(received != dto.pairs.size()){

            try {
                Future<SimplePairResultDto> f = completionService.take();

                received++;

                System.out.print(String.format("\r%s/%s", received, dto.pairs.size()));

                SimplePairResultDto single = f.get();
                UpdateDTO.instance.overallProgres++;

                if(single != null){
                    //LogProvider.info(String.format("%s (%s %s) D: %s",received, single.trace1Index, single.trace2Index, single.distance.getDistance()));

                    resultDto.setFunctioNMap(single.trace1Index, single.trace2Index, single.distance.getDistance());

                    resultDto.fileMap.put(single.trace1Index, single.tr1.traceFile);
                    resultDto.fileMap.put(single.trace2Index, single.tr2.traceFile);

                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


        UpdateDTO.instance.overallProgres = received;

        System.out.println(pairs);

        if(dto.outputAlignmentMap != null){
            LogProvider.info("Exporting  json distances");
            FileWriter writer = new FileWriter(String.format("%s/%s", dto.outputDir,  dto.outputAlignmentMap));
            writer.write(new Gson().toJson(resultDto));
            writer.close();
        }

        for(TraceMap map: traces){
            map.plainTrace.dispose();
        }

        executor.shutdownNow();

    }

    public class KeyValuePair{
        public String key;
        public Object value;

        public KeyValuePair(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }

    void exportImage(String fileName,
                                   IReadArray<Integer> trace1,
                                   IReadArray<Integer> trace2) throws IOException {

        trace1.reset();
        trace2.reset();

        int scale = 1;
        File writer = new File(fileName);
        int pieceSize =8*scale;

        int width = (int)Math.ceil(Math.sqrt(trace1.size())) + 1;


        BufferedImage img = new BufferedImage(width*pieceSize, width*pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        int row = 0;
        int col = 0;

        for(int i = 0; i < trace1.size(); i++){

            int t1 = trace1.read(i);
            int t2 = trace2.read(i);

            String color = "#e74c3c";

            if(t1 == t2)
                color = "#2ecc71";
            else if(t1 == - 1 || t2 == -1){
                color = "#ecf0f1";
            }


            col = i%width;
            row = i/width;

            g.setColor(Color.decode(color));
            g.fillRect(col*pieceSize, row*pieceSize, pieceSize, pieceSize);

            if(t1 == -1){
                g.setColor(Color.decode("#0000ff"));
                g.fillRect(col*pieceSize, row*pieceSize, pieceSize, pieceSize/2);
            }
            else if(t2 == -1){
                g.setColor(Color.decode("#0000ff"));
                g.fillRect(col*pieceSize, row*pieceSize + pieceSize/2, pieceSize, pieceSize/2);
            }


            g.setColor(Color.decode("#000000"));
            g.drawRect(col*pieceSize, row*pieceSize, pieceSize - 1, pieceSize  - 1);

        }

        ImageIO.write(img, "png", writer);

    }

}
