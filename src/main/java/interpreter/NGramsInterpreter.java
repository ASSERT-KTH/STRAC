package interpreter;

import com.google.gson.Gson;
import core.LogProvider;
import core.ServiceRegister;
import core.TraceHelper;
import core.data_structures.IDict;
import core.models.ComparisonDto;
import core.models.NGramSetDto;
import core.models.TraceMap;
import interpreter.dto.Payload;
import ngram.Generator;
import ngram.generators.comparers.DSLExpressionComparer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NGramsInterpreter {


    public void execute(Payload payload) throws IOException {
        TraceHelper helper = new TraceHelper();

        List<TraceMap> traces = helper.mapTraceSetByFileLine(payload.files);

        Generator generatpr = ServiceRegister.getProvider().getGenerator();
        ComparisonDto dto = new ComparisonDto(traces.size(), traces.size());


        if (payload.exportBag != null) {
            LogProvider.info("Exporting bag...");

            FileWriter writer = new FileWriter(String.format("%s/%s", payload.outputDir, payload.exportBag));

            writer.write(new Gson().toJson(helper));

            writer.close();
        }


        if (payload.exportNgram != null) {
            LogProvider.info("Exporting ngram...");

            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(traces.size());

            for (TraceMap tm : traces) {

                Payload finalPayload = payload;

                executor.submit(() -> {
                    for (int size : finalPayload.exportNgram) {
                        int i = 0;

                        try {
                            String[] chunks = tm.traceFile.split("/");

                            LogProvider.info("Exporting...", size, chunks[chunks.length - 1]);

                            FileWriter writer = new FileWriter(String.format("%s/%s.%s.gram.json", finalPayload.outputDir, size, chunks[chunks.length - 1]));

                            IDict dict = generatpr.getNGramSet(size, tm.plainTrace);

                            NGramSetDto ngramOutDto = new NGramSetDto();
                            ngramOutDto.set = dict;
                            ngramOutDto.bagPath = String.format("%s/%s", finalPayload.outputDir, finalPayload.exportBag);
                            ngramOutDto.keyCount = dict.size();
                            ngramOutDto.sentenceCount = tm.plainTrace.size();
                            ngramOutDto.n = size;
                            ngramOutDto.path = tm.traceFile;

                            writer.write(new Gson().toJson(ngramOutDto));

                            writer.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    return null;
                });


            }


            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {

            }
        }

        if (payload.comparisonExpression != null) {
            // Generator g = new StringKeyGenerator(t -> String.format("%s %s", t[0], t[1]));

            DSLExpressionComparer cmp = new DSLExpressionComparer(payload.comparisonExpression);

            for (int i = 0; i < traces.size(); i++) {

                if (payload.printComparisson)
                    System.out.print(traces.get(i).traceFile + " ");

                dto.traces.add(traces.get(i).traceFile);

                for (int j = i + 1; j < traces.size(); j++) {

                    cmp.setTraces(traces.get(i), traces.get(j));

                    try {
                        double distance = cmp.compare(payload.n);

                        if (payload.printComparisson)
                            System.out.print(distance + " ");

                        dto.set(i, j, distance);
                        dto.set(j, i, distance);
                        dto.set(i, i, 0);
                    } catch (Exception e) {
                        if (payload.printComparisson)
                            System.out.print("unreachable");

                        throw new RuntimeException(e);

                    }
                }

                if (payload.printComparisson)
                    System.out.println();
            }
        }

        if (payload.comparisonExpression != null && payload.exportComparisson != null) {

            FileWriter writer = new FileWriter(String.format("%s/%s", payload.outputDir, payload.exportComparisson));

            writer.write(new Gson().toJson(dto));

            writer.close();
        }
    }

}
