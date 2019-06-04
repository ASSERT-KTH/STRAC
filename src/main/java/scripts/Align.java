package scripts;

import align.AlignDistance;
import align.Aligner;
import align.InsertOperation;
import align.implementations.DWT;
import align.implementations.FastDWT;
import align.implementations.IImplementationInfo;
import com.google.gson.Gson;
import core.IServiceProvider;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IArray;
import core.data_structures.IDict;
import core.data_structures.IReadArray;
import core.data_structures.ISet;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryDict;
import core.data_structures.postgreSQL.PostgreArray;
import core.data_structures.postgreSQL.PostgreInterface;
import core.models.AlignResultDto;
import core.models.TraceMap;
import interpreter.dto.Alignment;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.utils.HashingHelper.getRandomName;

public class Align {

    public static void setup(){
        ServiceRegister.registerProvider(new IServiceProvider() {
            @Override
            public <T> IArray<T> allocateNewArray(Class<T> clazz) {
                return new InMemoryArray<T>();
            }

            @Override
            public <T> IArray<T> allocateNewArray(int size, Class<T> clazz) {
                return new InMemoryArray<T>();
            }

            @Override
            public <T> IArray<T> allocateNewArray(String id, Class<T> clazz) {

                return new InMemoryArray<>();
            }

            @Override
            public <T> IArray<T> allocateNewArray(T[] items,Class<T> clazz) {
                return new InMemoryArray<>();
            }

            @Override
            public <TKey, TValue> IDict<TKey, TValue> allocateNewDictionary() {
                return new InMemoryDict<TKey, TValue>();
            }

            @Override
            public <T, R> IHashCreator<T, R> getHashCreator() {
                return null;
            }

            @Override
            public <T> ISet<T> allocateNewSet() {
                return null;
            }

            @Override
            public Generator getGenerator() {
                return null;
            }
        });

        comparers = new HashMap<>();
        comparers.put("DTW", (objs) -> new DWT((x, y) -> x == y? 1: -1));
        comparers.put("FastDTW", (objs) -> new FastDWT(((Double)objs[0]).intValue()
                , (x, y) -> x == y? 2: -1));

        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("templates").getFile());

        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, file.getAbsolutePath());
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute" );
        ve.init();
    }

    static Map<String, IImplementationInfo> comparers;

    public static void main(String[] args) throws IOException, SQLException {

        setup();

        Alignment dto = new Gson().fromJson(new FileReader(args[0]), Alignment.class);

        //PostgreInterface.setup(dto.dbHost, dto.dbPort, dto.dbName, dto.user, dto.password, false);

        TraceHelper helper = new TraceHelper();

        if(!comparers.containsKey(dto.method.name)){
            LogProvider.info(comparers.keySet());
            throw  new RuntimeException("Method nos allowed");
        }

        LogProvider.info("Parsing traces");

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, false);
        Aligner align = comparers.get(dto.method.name).getAligner(dto.method.params);


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

            //writeTraceFile(tr1.plainTrace, helper, "t1.txt");
            //writeTraceFile(tr2.plainTrace, helper, "t2.txt");



            if(dto.outputAlignment){

                String file1 = String.format("%s_%s%s.align", tr1.traceFile, pair[0], pair[1]);
                String file2 = String.format("%s_%s%s.align", tr2.traceFile, pair[0], pair[1]);

                IArray<Integer> trace1Alignment = ServiceRegister.getProvider().allocateNewArray(
                        file1, Integer.class
                );
                IArray<Integer> trace2Alignment = ServiceRegister.getProvider().allocateNewArray(
                        file2, Integer.class
                );


                InsertOperation i1 = new InsertOperation(0, 0);

                tr1.plainTrace.close();
                tr2.plainTrace.close();

                for(int i= distance.getInsertions().size() - 1; i >=0 ; i--){

                   InsertOperation i2 = distance.getInsertions().read(i);
                   //LogProvider.info(i2);

                    InsertOperation s = i2;
                    int[] direction = new int[] {
                            i2.getTrace1Index() - i1.getTrace1Index(),
                            i2.getTrace2Index() - i1.getTrace2Index()
                    };

                    if(direction[0] > 1){
                        LogProvider.info("Warning", direction[0], direction[1], i);
                    }

                    try {
                        if (direction[0] > 0 && direction[1] > 0) {
                            trace1Alignment.add(tr1.plainTrace.read(s.getTrace1Index()));
                            trace2Alignment.add(tr2.plainTrace.read(s.getTrace2Index()));

                        }
                        if (direction[0] == 0) {
                            trace1Alignment.add(-1);
                            trace2Alignment.add(tr2.plainTrace.read(s.getTrace2Index()));
                        }

                        if (direction[1] == 0) {
                            trace2Alignment.add(-1);
                            trace1Alignment.add(tr1.plainTrace.read(s.getTrace1Index()));
                        }
                    }
                    catch (Exception e){
                        throw new RuntimeException(e.getMessage());
                    }

                    i1 = i2;
                }

                trace1Alignment.close();
                trace2Alignment.close();

                // Comparing the two traces

                double total = 0;

                for(int i = 0; i < trace1Alignment.size(); i++){

                    try{
                        int t1 = trace1Alignment.read(i);
                        int t2 = trace2Alignment.read(i);

                        double val = dto.comparison.diff;

                        if(t1 == -1 || t2 == -1)
                            val = dto.comparison.gap;
                        else if(t1 == t2)
                            val = dto.comparison.eq;

                        total += val;
                    }
                    catch (Exception e){
                        throw new RuntimeException(e.getMessage());
                    }

                }

                total = total/(dto.comparison.diff*trace1Alignment.size());

                LogProvider.info("DTW Distance", distance.getDistance());
                LogProvider.info("Distance", total);

                resultDto.set(pair[0], pair[1], total);
                resultDto.setFunctioNMap(pair[0], pair[1], distance.getDistance());

                resultDto.fileMap.put(pair[0], tr1.traceFile);
                resultDto.fileMap.put(pair[1], tr2.traceFile);
                resultDto.results.add(total);
                resultDto.method = dto.method;

                // Write file


                if(dto.outputAlignment) {
                    LogProvider.info("Writing align result to file");
                    writeTraceFile(trace1Alignment, helper, file1);
                    writeTraceFile(trace2Alignment, helper, file2);
                }

                if(dto.exportHTML){
                    LogProvider.info("Exporting to svg preview");
                    exportHTML(trace1Alignment, trace2Alignment, helper,
                            tr1.traceFile,
                            tr2.traceFile,
                            total,
                            String.format("%s_%s.svg", dto.outputDir, pair[0], pair[1]));
                }

                if(dto.exportImage){
                    LogProvider.info("Exporting to image");
                    exportImage(
                            String.format("%s/%s_%s.png",dto.outputDir, pair[0], pair[1]),
                            trace1Alignment, trace2Alignment);
                }


                trace1Alignment.dispose();
                trace2Alignment.dispose();
                distance.getInsertions().dispose();
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

    public static void writeTraceFile(IReadArray<Integer> align, TraceHelper helper, String filename) throws IOException {

        FileWriter w1 = new FileWriter(filename);

        for(int x= 0; x < align.size(); x++){
            int i = align.read(x);

            w1.write(helper.getInverseBag().get(i) + (i < align.size() - 1 ? "\n": ""));
        }

        w1.close();

    }

    public static void exportHTML(IReadArray<Integer> align1,
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

        writer.write(String.format("<?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width='%spx' height='%spx'>", width, traceSize*align1.size()));



        for(int i = 0; i < align1.size(); i++){

            String cl = "#e74c3c";

            int t1 = align1.read(i);
            int t2 = align2.read(i);

            if(t1 == t2)
                cl = "#2ecc71";
            else if(t1 == - 1 || t2 == -1){
                cl = "#ecf0f1";
            }

            String text1 = helper.getInverseBag().get(t1);
            String text2 = helper.getInverseBag().get(t2);

            text1 = StringEscapeUtils.escapeHtml4(text1);
            text2 = StringEscapeUtils.escapeHtml4(text2);

            writer.write(
                    getTemplate("item_template.html",
                            new KeyValuePair("width", itemWidth),
                            new KeyValuePair("height", traceSize),
                            new KeyValuePair("x", width/2),
                            new KeyValuePair("y", traceSize*i),
                            new KeyValuePair("text1", text1),
                            new KeyValuePair("text2", text2),
                            new KeyValuePair("totalWidth", width),
                            new KeyValuePair("fill", cl))
            );
        }

        writer.write(getTemplate("info_template.html",
                new KeyValuePair("file1", trace1Name),
                new KeyValuePair("file2", trace2Name),
                new KeyValuePair("distance", distance)));

        // Write tail
        writer.write("</svg>");

        writer.close();

        /*
        *
        */
    }

    public static class KeyValuePair{
        public String key;
        public Object value;

        public KeyValuePair(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }

    static VelocityEngine ve;

    public static String getTemplate(String name, KeyValuePair ... dict){


        Template t = ve.getTemplate(name);
        VelocityContext context = new VelocityContext();


        for(KeyValuePair pair: dict)
            context.put(pair.key, pair.value);


        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        /* show the World */
        return writer.toString();

    }

    public static void exportImage(String fileName,
                                   IReadArray<Integer> trace1,
                                   IReadArray<Integer> trace2) throws IOException {

        int scale = 1;
        File writer = new File(fileName);
        int pieceSize =40*scale;

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
            g.drawRect(col*pieceSize, row*pieceSize, pieceSize, pieceSize);
            g.fillRect(col*pieceSize, row*pieceSize, pieceSize, pieceSize);
        }

        ImageIO.write(img, "png", writer);

    }

}
