package interpreter;

import align.AlignDistance;
import align.Aligner;
import align.Cell;
import align.ICellComparer;
import align.implementations.IImplementationInfo;
import com.google.gson.Gson;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IReadArray;
import core.models.AlignResultDto;
import core.models.TraceMap;
import interpreter.dto.Alignment;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.utils.HashingHelper.getRandomName;

public class AlignInterpreter {

    VelocityEngine ve;

    public AlignInterpreter(VelocityEngine engine){
        this.ve = engine;
    }


    public AlignInterpreter(){
        this.ve = null;
    }



    public interface IOnAlign{
        void action(AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize);
    }


    public void execute(Alignment dto) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        execute(dto,  null);
    }

    public void execute(Alignment dto, IOnAlign action) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {


        if(dto.distanceFunctionName == null){

            LogProvider.info("Distance function not provided. We set a default one based on dto comparison data");
            dto.distanceFunctionName = "default";

            ServiceRegister.registerFunction("default", new ICellComparer() {
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


        TraceHelper helper = new TraceHelper();

        LogProvider.info("Parsing traces");

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, dto.separator == null ? "\r\n" : dto.separator, t -> {
            try {
                return new FileInputStream(t);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, false);

        Aligner align = ServiceRegister.getAligner(dto.method.name, dto.method.params.toArray(), ServiceRegister.getComparer(dto.distanceFunctionName));


        AlignResultDto resultDto = new AlignResultDto();


        if(dto.pairs.size() == 0){
            LogProvider.info("No specific pairs, two vs two tournament");

            List<int[]> pairs = new ArrayList<>();

            for(int i = 0; i < traces.size(); i++){
                for(int j  = i + 1; j < traces.size(); j++){
                    pairs.add(new int[] {i, j});
                }
            }

            dto.pairs = pairs;
        }

        for(int[] pair: dto.pairs){
            TraceMap tr1 = traces.get(pair[0]);
            TraceMap tr2 = traces.get(pair[1]);

            AlignDistance distance = align.align(tr1.plainTrace, tr2.plainTrace);
            distance.getInsertions().close();

            helper.getInverseBag().put(-1, "-");
            helper.getInverseBag().put(0, "");

            //writeTraceFile(tr1.plainTrace, helper, "t1.txt");
            //writeTraceFile(tr2.plainTrace, helper, "t2.txt");



            try{

            if(dto.outputAlignment){

                String file1 = String.format("%s/align.%s.%s", dto.outputDir, tr1.traceFileName, tr2.traceFileName);
                String file2 = String.format("%s/align.%s.%s", dto.outputDir, tr2.traceFileName, tr1.traceFileName);


                long max = distance.operationsCount;

                IArray<Integer> trace1Alignment
                        = ServiceRegister.getProvider().allocateIntegerArray(getRandomName(), max,
                        ServiceRegister.getProvider().selectMethod(Integer.SIZE*max));

                IArray<Integer> trace2Alignment
                        = ServiceRegister.getProvider().allocateIntegerArray(getRandomName(), max,
                        ServiceRegister.getProvider().selectMethod(Integer.SIZE*max));


                Cell i1 = null;

                tr1.plainTrace.close();
                tr2.plainTrace.close();

                long p1 = 0;
                long p2 = 0;

                Cell i2 = null;
                for(long i = distance.operationsCount; i > 0 ; i--){

                    i2 = distance.getInsertions().read(i - 1);
                    i1 = distance.getInsertions().read(i);
                    //LogProvider.info(i2);

                    //System.out.print(i2 + "-");

                    if(i1 == null){
                        //LogProvider.info("Null operation", i2);
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


                double total = 0;
                double mismatch = 0;
                double gaps1 = 0;
                double gaps2 = 0;

                double totalNonGaps = 0;

                trace1Alignment.reset();
                trace2Alignment.reset();



                for(int i = 0; i < trace1Alignment.size(); i++){

                    try{
                        int t1 = trace1Alignment.read(i);
                        int t2 = trace2Alignment.read(i);

                        //double val = dto.comparison.diff;

                        if(t1 == t2 && t1 != -1)
                            total++;
                        if(t1 != t2 && t1 != -1 && t2 != -1)
                            mismatch++;
                        if(t1 == -1)
                            gaps1++;
                        if(t2 == -1)
                            gaps2++;
                        //if(t1 != -1 || t2 != -1)
                        totalNonGaps++;

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        System.err.println(i + " ");
                        throw new RuntimeException(e.getMessage());
                    }

                }



                LogProvider.info("DTW Distance", distance.getDistance());
                LogProvider.info("Distance (How many coincidences percent)", total/(totalNonGaps), total, totalNonGaps);

                if(action != null)
                    action.action(distance, total, mismatch, gaps1, gaps2, trace1Alignment.size());

                total = total/totalNonGaps;


                if(!Double.isNaN(total)) {
                    resultDto.set(pair[0], pair[1], total);
                    resultDto.setFunctioNMap(pair[0], pair[1], distance.getDistance());
                    resultDto.results.add(total);
                }

                resultDto.fileMap.put(pair[0], tr1.traceFile);
                resultDto.fileMap.put(pair[1], tr2.traceFile);
                resultDto.method = dto.method;

                // Write file


                if(dto.outputAlignment) {
                    try {
                        LogProvider.info("Writing align result to file");

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

                if(dto.exportHTML && ve != null){//dto.exportHTML){
                    LogProvider.info("Exporting to html preview");
                    exportHTML(trace1Alignment, trace2Alignment, helper,
                            tr1.traceFile,
                            tr2.traceFile,
                            total,
                            String.format("%s/%s_%s.html", dto.outputDir, tr1.traceFileName, tr2.traceFileName));
                }

                if(dto.exportImage){
                    LogProvider.info("Exporting to image");
                    exportImage(
                            String.format("%s/%s_%s.png",dto.outputDir, pair[0], pair[1]),
                            trace1Alignment, trace2Alignment);
                }

                trace1Alignment.close();
                trace2Alignment.close();
                trace1Alignment.dispose();
                trace2Alignment.dispose();
                distance.getInsertions().dispose();
            }

            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        if(dto.outputAlignmentMap != null){
            LogProvider.info("Exporting  json distances");
            FileWriter writer = new FileWriter(String.format("%s/%s", dto.outputDir,  dto.outputAlignmentMap));
            writer.write(new Gson().toJson(resultDto));
            writer.close();
        }

        for(TraceMap map: traces){
            map.plainTrace.dispose();
        }

    }

    void exportHTML(IReadArray<Integer> align1,
                                  IReadArray<Integer> align2, TraceHelper helper,
                                  String trace1Name,
                                  String trace2Name,
                                  double distance,
                                  String fileName) throws IOException {

        FileWriter writer = new FileWriter(fileName);

        // Writing header

        int width = 1000;
        int traceSize = 20;
        int itemWidth = 500;

        writer.write(String.format("<!DOCTYPE html>\n" +
                "  <html lang=\"en\">\n" +
                "  <head>\n" +
                "\t  <meta charset=\"UTF-8\">\n" +
                "\t  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "\t  <title>Traces alignment</title>\n" +
                "\t  <style>\n" +
                "\t  \t.row{\n" +
                "\t\t\t  display: flex;\n" +
                "\t\t\t  flex-direction: 'row';\n" +
                "\t\t  }\n" +
                "\t\t  .trace{\n" +
                "\t\t\t  flex: 10\n" +
                "\t\t  }\n" +
                "\t\t  .status{\n" +
                "\t\t\t  width: 10px;\n" +
                "\t\t\t  background-color: rgb(10,10,10);\n" +
                "\t\t\t  margin-right: 20px;\n" +
                "\t\t\t  margin-left: 20px;\n" +
                "\t\t  }\n" +
                "\n" +
                "\t\t  .status.eq{\n" +
                "\t\t\t  background-color: green;\n" +
                "\t\t  }\n" +
                "\t\t  .status.diff{\n" +
                "\t\t\t  background-color: red;\n" +
                "\t\t  }\n" +
                "\t\t  .status.gap{\n" +
                "\t\t\t  background-color: rgb(200,200,200)\n" +
                "\t\t  }\n" +
                "\t\t  .trace1{\n" +
                "\t\t\t  text-align: right;\n" +
                "\t\t  }\n" +
                "\t\t  .trace2{\n" +
                "\t\t\t  text-align: left;\n" +
                "\t\t  }\n" +
                "\n" +
                "\t\t  .trace.gap{\n" +
                "\t\t\t  color:transparent;\n" +
                "\t\t  }\n" +
                "\n" +
                "\t  </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "\t<div class='div-container'>"));


        align1.reset();
        align2.reset();

        for(int i = 0; i < align1.size(); i++){

            String cl = "diff";

            int t1 = align1.read(i);
            int t2 = align2.read(i);

            if(t1 == t2)
                cl = "eq";
            else if(t1 == - 1 || t2 == -1){
                cl = "gap";
            }

            String text1 = helper.getInverseBag().get(t1);
            String text2 = helper.getInverseBag().get(t2);

            text1 = StringEscapeUtils.escapeHtml4(text1);
            text2 = StringEscapeUtils.escapeHtml4(text2);

            writer.write(
                    getTemplate("item_template.html",
                            new KeyValuePair("text1", text1),
                            new KeyValuePair("text2", text2),
                            new KeyValuePair("cl", cl))
            );
        }

        /*writer.write(getTemplate("info_template.html",
                new KeyValuePair("file1", trace1Name),
                new KeyValuePair("file2", trace2Name),
                new KeyValuePair("distance", distance)));*/

        // Write tail
        writer.write("</div>\n" +
                "  </body>\n" +
                "  </html>");

        writer.close();

        /*
         *
         */
    }

    public class KeyValuePair{
        public String key;
        public Object value;

        public KeyValuePair(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }


    String getTemplate(String name, KeyValuePair ... dict){


        Template t = ve.getTemplate(name);
        VelocityContext context = new VelocityContext();


        for(KeyValuePair pair: dict)
            context.put(pair.key, pair.value);


        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        /* show the World */
        return writer.toString();

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
