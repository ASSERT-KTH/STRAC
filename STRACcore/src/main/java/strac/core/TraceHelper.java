package strac.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import strac.core.data_structures.IArray;
import strac.core.dto.FileContentDto;
import strac.core.models.TraceMap;
import strac.core.stream_providers.CommandStdInputProvider;
import strac.core.utils.ServiceRegister;

import java.io.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

            return this.bag.keySet().size();
        }

        return bag.get(sentence);
    }


    public long countSentences(String separator, String[] remove,  InputStream stream, IHasNext hasNextIterator, INextProvider sentenceProvider){

        Scanner sc = sentenceProvider.setupScanner(new Scanner(stream, "UTF-8"), separator);

        long count = 0;

        while (hasNextIterator.hasNext(separator, sc)) {
            String line = sentenceProvider.getNext(separator, sc);

            for(String pattern: remove)
                line = line.replaceAll(pattern, "");

            line = line.trim();

            if(!line.equals(""))
                count++;
        }

        return count;
    }

    public TraceMap mapTraceFileByLine(String fileName, String separator, String[] remove, FileContentDto.Include include, IStreamProvider provider,
                                       boolean keepSentences, IHasNext hasNextIterator, INextProvider sentenceProvider) {

        LogProvider.LOGGER()
                .info("Processing " + fileName);

        InputStream str = provider.getStream(fileName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream copy1;
        InputStream copy2;

        // Cloning input stream
        try {
            str.transferTo(baos);

            copy1 = new ByteArrayInputStream(baos.toByteArray());
            copy2 = new ByteArrayInputStream(baos.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        long count = countSentences(separator, remove, copy1, hasNextIterator, sentenceProvider);


        int[] trace = ServiceRegister.getInstance().getProvider().allocateIntegerArray(null, count,
                ServiceRegister.getInstance().getProvider().selectMethod(count));


        Scanner sc = sentenceProvider.setupScanner(new Scanner(copy2, "UTF-8"), separator);


        List<String> sentences = new ArrayList<>();
        long index = 0;

        while (hasNextIterator.hasNext(separator, sc)) {
            String line = sentenceProvider.getNext(separator, sc);


            for(String pattern: remove)
                line = line.replaceAll(pattern, "");

            line = line.trim();

            if(include != null) {

                Pattern p = Pattern.compile(include.pattern);

                Matcher m = p.matcher(line);

                MatchResult r = m.toMatchResult();
                while(m.find()){
                    line = m.group(include.group);
                    break;
                }
            }


            // System.out.println(line);
            if(keepSentences && !line.equals(""))
                sentences.add(line);


            if(!line.equals(""))
                trace[(int)index++] = updateBag(line);

        }


        LogProvider.info("New trace added size: ", index);

        strac.core.LogProvider.LOGGER()
                .info(String.format("Global bag info: total sentences %s different sentences %s", this.getSentecesCount(), this.getDifferentSentenceCount()));


        return new TraceMap(trace, fileName, sentences.toArray(new String[0]));

    }

    public List<TraceMap> mapTraceSetByFileLine(List<String> files, String separator,  IStreamProvider provider,
                                                boolean keepSentences, boolean complement) {

        return mapTraceSetByFileLine(files, separator, new String[0], null, provider, keepSentences, complement);

    }

    public List<TraceMap> mapTraceSetByFileLine(List<String> files, String separator, String[] remove, FileContentDto.Include include, IStreamProvider provider,
                                                boolean keepSentences, boolean complement){


        IHasNext separatorProvider = (pattern, sc) -> sc.hasNext();

        INextProvider sentenceProvider = new INextProvider() {
            @Override
            public String getNext(String pattern, Scanner sc) {
                return sc.next();
            }

            @Override
            public Scanner setupScanner(Scanner sc, String pattern) {
                return sc.useDelimiter(Pattern.compile(separator));
            }
        };

        return files.stream()
                .map(t -> this.mapTraceFileByLine(t, separator, remove, include, provider, keepSentences, separatorProvider, sentenceProvider))
                .collect(Collectors.toList());

    }

    public interface IHasNext{
        boolean hasNext(String pattern, Scanner sc);
    }

    public interface INextProvider{

        String getNext(String pattern, Scanner sc);

        Scanner setupScanner(Scanner sc, String pattern);
    }

    public interface IStreamProvider{

        InputStream getStream(String filename);

        boolean validate(String filename);
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
