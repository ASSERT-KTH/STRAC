package scripts;

import align.AlignDistance;
import align.Aligner;
import align.InsertOperation;
import align.implementations.DWT;
import align.implementations.FastDWT;
import align.implementations.IImplementationInfo;
import align.implementations.WindowedDWT;
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
import core.data_structures.memory.InMemorySet;
import core.data_structures.postgreSQL.PostgreArray;
import core.data_structures.postgreSQL.PostgreInterface;
import core.models.AlignResultDto;
import core.models.TraceMap;
import interpreter.dto.Alignment;
import ngram.Generator;
import ngram.hash_keys.IHashCreator;

import java.io.*;
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
                return new PostgreArray<T>(getRandomName(), clazz);
            }

            @Override
            public <T> IArray<T> allocateNewArray(int size, Class<T> clazz) {
                return new PostgreArray<T>(getRandomName(), clazz);
            }

            @Override
            public <T> IArray<T> allocateNewArray(String id, Class<T> clazz) {
                return new PostgreArray<T>(id, clazz);
            }

            @Override
            public <T> IArray<T> allocateNewArray(T[] items,Class<T> clazz) {
                return new PostgreArray<T>(getRandomName(), clazz);
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
        comparers.put("DTW", (objs) -> new DWT((x, y) -> x == y? 2: -1));
        comparers.put("FastDTW", (objs) -> new FastDWT(((Double)objs[0]).intValue()
                , (x, y) -> x == y? 2: -1));

    }

    static Map<String, IImplementationInfo> comparers;

    public static void main(String[] args) throws IOException, SQLException {

        setup();

        Alignment dto = new Gson().fromJson(new FileReader(args[0]), Alignment.class);

        PostgreInterface.setup(dto.dbHost, dto.dbPort, dto.dbName, dto.user, dto.password, false);

        TraceHelper helper = new TraceHelper();

        if(!comparers.containsKey(dto.method.name)){
            LogProvider.info(comparers.keySet());
            throw  new RuntimeException("Method nos allowed");
        }

        LogProvider.info("Parsing traces");

        List<TraceMap> traces = helper.mapTraceSetByFileLine(dto.files, false);
        Aligner align = comparers.get(dto.method.name).getAligner(dto.method.params);


        AlignResultDto resultDto = new AlignResultDto();

        for(int[] pair: dto.pairs){
            TraceMap tr1 = traces.get(pair[0]);
            TraceMap tr2 = traces.get(pair[1]);

            AlignDistance distance = align.align(tr1.plainTrace, tr2.plainTrace);

            helper.getInverseBag().put(-1, "-");

            writeTraceFile(tr1.plainTrace, helper, "t1.txt");
            writeTraceFile(tr2.plainTrace, helper, "t2.txt");

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

                for(int i= distance.getInsertions().size() - 1; i >= 0; i--){
                    InsertOperation i2 = distance.getInsertions().read(i);

                    int[] direction = new int[] {
                            i2.getTrace1Index() - i1.getTrace1Index(),
                            i2.getTrace2Index() - i1.getTrace2Index()
                    };

                    if(direction[0] != 0 && direction[1] != 0){
                        trace1Alignment.add(tr1.plainTrace.read(i2.getTrace1Index()));
                        trace2Alignment.add(tr2.plainTrace.read(i2.getTrace2Index()));

                    }
                    if(direction[0] == 0){
                        trace1Alignment.add(-1);
                        trace2Alignment.add(tr2.plainTrace.read(i2.getTrace2Index()));
                    }

                    if(direction[1] == 0){
                        trace2Alignment.add(-1);
                        trace1Alignment.add(tr1.plainTrace.read(i2.getTrace1Index()));
                    }

                    i1 = i2;
                }

                trace1Alignment.close();
                trace2Alignment.close();

                // Comparing the two traces

                double total = 0;

                for(int i = 0; i < trace1Alignment.size(); i++){

                    int t1 = trace1Alignment.read(i);
                    int t2 = trace2Alignment.read(i);

                    double val = dto.comparison.diff;

                    if(t1 == t2)
                        val = dto.comparison.eq;

                    total += val;

                }

                total = total/(dto.comparison.diff*trace1Alignment.size());

                LogProvider.info("Distance", total);

                resultDto.set(pair[0], pair[1], total);
                resultDto.fileMap.put(pair[0], tr1.traceFile);
                resultDto.fileMap.put(pair[1], tr2.traceFile);

                // Write file


                if(dto.outputAlignment) {
                    LogProvider.info("Writing align result to file");
                    writeTraceFile(trace1Alignment, helper, file1);
                    writeTraceFile(trace2Alignment, helper, file2);
                }

                if(dto.exportHTML){
                    LogProvider.info("Exporting to html preview");
                    exportHTML(trace1Alignment, trace2Alignment, helper,
                            String.format("%s_%s.html", pair[0], pair[1]));
                }


                trace1Alignment.dispose();
                trace2Alignment.dispose();
                distance.getInsertions().dispose();
            }

        }
        if(dto.outputAlignmentMap != null){
            LogProvider.info("Exporting  json distances");
            FileWriter writer = new FileWriter(dto.outputAlignmentMap);
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

    public static void exportHTML(IReadArray<Integer> align1, IReadArray<Integer> align2, TraceHelper helper, String fileName) throws IOException {

        FileWriter writer = new FileWriter(fileName);

        // Writing header

        writer.write("<!DOCTYPE html>\n" +
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
                "\t<div class='div-container'>");


        for(int i = 0; i < align1.size(); i++){

            String cl = "diff";

            int t1 = align1.read(i);
            int t2 = align2.read(i);

            if(t1 == t2)
                cl = "eq";

            if(t1 == - 1 || t2 == -1){
                cl = "gap";
            }

            writer.write(String.format("<div class='row'>\n" +
                    "\t\t\t\t<div class='trace trace1 %s'>\n" +
                    "\t\t\t\t\t%s\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t\t<div class=\"status %s\"></div>\n" +
                    "\t\t\t\t<div class='trace trace2 %s'>\n" +
                    "\t\t\t\t\t%s\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t</div>",
                    cl,
                    helper.getInverseBag().get(t1),
                    cl,
                    cl,
                    helper.getInverseBag().get(t2)
                    ));
        }

        // Write tail
        writer.write("</div>\n" +
                "  </body>\n" +
                "  </html>");

        writer.close();

    }

}
