package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.data_structures.IArray;
import core.data_structures.memory.InMemoryArray;
import core.models.TraceMap;
import core.utils.HashingHelper;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;
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

    public IArray<Integer> updateBag(Stream<String> sentences,
                                     Stream<String> patch,
                                     String fileName){


        int count = (int)patch.count();

        IArray<Integer> result = ServiceRegister.getProvider().allocateIntegerArray(
                null,
                count,
                        ServiceRegister.getProvider().selectMethod(4*count
                        ));

        long position = 0;

        for (Iterator<String> it = sentences.iterator(); it.hasNext(); ) {
            String sentence = it.next();
            this.sentencesCount++;
            if(!bag.containsKey(sentence)){
                bag.put(sentence, bag.keySet().size() + 1);
                inverseBag.put(bag.keySet().size(), sentence);

            }

            result.set(position++,bag.get(sentence));
        }

        return result;
    }

    public long countSentences(String separator, InputStream stream){
        Scanner sc = new Scanner(stream, "UTF-8").useDelimiter(Pattern.compile(separator));

        long count = 0;

        while (sc.hasNext()) {
            sc.next();
            count++;
        }

        return count;
    }

    public TraceMap mapTraceFileByLine(String fileName, String separator, InputStream stream, boolean keepSentences) {

        LogProvider.LOGGER()
                .info("Processing " + fileName);


        long count = countSentences(separator, stream);

        try {
            stream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IArray<Integer> trace = ServiceRegister.getProvider().allocateIntegerArray(null, count,
                ServiceRegister.getProvider().selectMethod(count));


        Scanner sc = new Scanner(stream, "UTF-8").useDelimiter(Pattern.compile(separator));


        List<String> sentences = new ArrayList<>();
        long index = 0;

        while (sc.hasNext()) {
            String line = sc.next();

            if(keepSentences)
                sentences.add(line);

            trace.set(index++, updateBag(line));

        }


        LogProvider.info("New trace added size: ", index);

        core.LogProvider.LOGGER()
                .info(String.format("Global bag info: total sentences %s different sentences %s", this.getSentecesCount(), this.getDifferentSentenceCount()));


        return new TraceMap(trace, fileName, sentences.toArray(new String[0]));

    }

    public List<TraceMap> mapTraceSetByFileLine(List<String> files, String separator, IStreamProvider provider, boolean keepSentences){


        return files.stream()
                .map(t -> this.mapTraceFileByLine(t, separator, provider.getStream(t), keepSentences))
                .collect(Collectors.toList());

    }

    public interface IStreamProvider{
        InputStream getStream(String filename);
    }


    public List<TraceMap> mapTraceSetByFileLine(List<String> files, IStreamProvider provider){


        return files.stream()
                .map(t -> this.mapTraceFileByLine(t, "\r\n", provider.getStream(t), false))
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
