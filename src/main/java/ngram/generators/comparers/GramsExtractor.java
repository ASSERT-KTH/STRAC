package ngram.generators.comparers;

import com.google.gson.Gson;
import core.ServiceRegister;
import core.data_structures.IDict;
import core.models.TraceMap;
import ngram.Generator;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GramsExtractor extends Comparer {

    public double compare(Double size, ArrayList<String> files) throws IOException {

        Generator g = ServiceRegister.getProvider().getGenerator();

        int sizeI = size.intValue();

        // Getting tr1 chunks
        List<String> dict1 = new ArrayList<>();
        List<String> dict2 = new ArrayList<>();

        processTrace(sizeI, dict1, tr1);
        processTrace(sizeI, dict2, tr2);

        if(files != null && files.size() > 1) {
            exportDictionary(dict1, files.get(0));
            exportDictionary(dict2, files.get(1));
        }

        return -1;
    }


    private void exportDictionary(List dict, String fileName) throws IOException {

        FileWriter writer = new FileWriter(String.format("%s.seq.json", fileName));

        writer.write(new Gson().toJson(dict));

        writer.close();
    }


    private void processTrace(int sizeI, List<String> dict1, TraceMap tr1) {
        for(int i = 0; i <= tr1.trace.getSize() - sizeI; i ++){
            BigInteger[] hash = tr1.trace.query(i, i + sizeI, ServiceRegister.getProvider().getHashCreator());

            if(hash != null && hash.length > 1) {
                String hashString = hash[0] + " " + hash[1];

                dict1.add(hashString);
            }
        }
    }


}
