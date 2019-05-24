package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.models.TraceMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraceHelper {

    private Map<String, Integer> bag;
    private Map<Integer, String> inverseBag;

    private int sentencesCount;


    public TraceHelper(){
        bag = new HashMap<>();
        inverseBag = new HashMap<>();
    }

    public Map<String, Integer> getBag(){
        return this.bag;
    }

    public Map<Integer, String> getInverseBag(){
        return this.inverseBag;
    }

    public int getSentecesCount(){
        return this.sentencesCount;
    }

    public int getDifferentSentenceCount(){
        return this.bag.keySet().size();
    }

    public List<Integer> updateBag(Stream<String> sentences){

        List<Integer> result = new ArrayList<>();

        for (Iterator<String> it = sentences.iterator(); it.hasNext(); ) {
            String sentence = it.next();
            this.sentencesCount++;
            if(!bag.containsKey(sentence)){
                bag.put(sentence, bag.keySet().size() + 1);
                inverseBag.put(bag.keySet().size(), sentence);

            }

            result.add(bag.get(sentence));
        }

        return result;
    }


    public TraceMap mapTraceFileByLine(String fileName) {

        LogProvider.LOGGER()
                .info("Processing " + fileName);

        List<Integer> trace ;

        try {
            trace = updateBag(Files.lines(Paths.get(fileName)));

            core.LogProvider.LOGGER()
                    .info(String.format("Global bag info: total sentences %s different sentences %s", this.getSentecesCount(), this.getDifferentSentenceCount()));

            return new TraceMap(trace, fileName);
        } catch (IOException e) {
            core.LogProvider.info("Error", e.getMessage());
            return new TraceMap(new ArrayList<>(), fileName + ' ' + e.getMessage());
        }


    }

    public List<TraceMap> mapTraceSetByFileLine(List<String> files){


        return files.stream()
                .map(this::mapTraceFileByLine)
                .collect(Collectors.toList());

    }


    public void save(String filePath) throws IOException {

        FileWriter writer = new FileWriter(filePath);

        writer.write(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create().toJson(this));

        writer.close();

        LogProvider.info("Saving sentences map", filePath);

    }

    public static TraceHelper load(String filePath) throws FileNotFoundException {

        LogProvider.info("Loading sentences map", filePath);

        return new Gson().fromJson(new FileReader(filePath), TraceHelper.class);
    }

}
