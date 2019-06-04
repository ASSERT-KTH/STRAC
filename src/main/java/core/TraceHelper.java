package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.data_structures.IArray;
import core.data_structures.memory.InMemoryArray;
import core.models.TraceMap;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public Integer updateBag(String sentence){

        this.sentencesCount++;
        if(!bag.containsKey(sentence)){
            bag.put(sentence, bag.keySet().size() + 1);
            inverseBag.put(bag.keySet().size(), sentence);

        }
        return this.bag.keySet().size();
    }

    public IArray<Integer> updateBag(Stream<String> sentences, String fileName){

        IArray<Integer> result = ServiceRegister.getProvider().allocateNewArray(fileName, Integer.class);

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


    public TraceMap mapTraceFileByLine(String fileName, boolean createTree) {

        LogProvider.LOGGER()
                .info("Processing " + fileName);

        IArray<Integer> trace ;

        try {

            trace = updateBag(Files.lines(Paths.get(fileName)), fileName);
            trace.close();

            core.LogProvider.LOGGER()
                    .info(String.format("Global bag info: total sentences %s different sentences %s", this.getSentecesCount(), this.getDifferentSentenceCount()));

            trace.close(); // Save caching

            return new TraceMap(trace, fileName, createTree);
        } catch (IOException e) {
            core.LogProvider.info("Error", e.getMessage());
            return new TraceMap(ServiceRegister.getProvider().allocateNewArray(Integer.class), fileName + ' ' + e.getMessage(), false);
        }


    }

    public List<TraceMap> mapTraceSetByFileLine(List<String> files, boolean createTree){


        return files.stream()
                .map(t -> this.mapTraceFileByLine(t, createTree))
                .collect(Collectors.toList());

    }



    public List<TraceMap> mapTraceSetByFileLine(List<String> files){


        return files.stream()
                .map(t -> this.mapTraceFileByLine(t, true))
                .collect(Collectors.toList());

    }

    public List<TraceMap> mapTraceSetByFileLine(String[] files){
        return this.mapTraceSetByFileLine(Arrays.asList(files));
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
