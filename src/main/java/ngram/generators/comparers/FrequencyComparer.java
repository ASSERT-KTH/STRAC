package ngram.generators.comparers;

import com.google.gson.Gson;
import core.ServiceRegister;
import core.data_structures.IDict;
import core.data_structures.segment_tree.SegmentTree;
import core.models.TraceMap;
import ngram.Generator;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FrequencyComparer extends Comparer {

    public double compare(Double size, ArrayList<String> files) throws IOException {

        Generator g = ServiceRegister.getProvider().getGenerator();

        int sizeI = size.intValue();

        // Getting tr1 chunks
        IDict<String, Integer> dict1 = ServiceRegister.getProvider().allocateNewDictionary();
        IDict<String, Integer> dict2 = ServiceRegister.getProvider().allocateNewDictionary();

        processTrace(sizeI, dict1, dict2, tr1);
        processTrace(sizeI, dict2, dict1, tr2);

        if(files != null && files.size() > 1) {
            exportDictionary(dict1, files.get(0));
            exportDictionary(dict2, files.get(1));
        }

        return -1;
    }


    private void exportDictionary(IDict dict, String fileName) throws IOException {

        FileWriter writer = new FileWriter(String.format("%s.freq.json", fileName));

        writer.write(new Gson().toJson(dict));

        writer.close();
    }


    private void processTrace(int sizeI, IDict<String, Integer> dict1, IDict<String, Integer> dict2, TraceMap tr1) {
        for(int i = 0; i < tr1.trace.getSize(); i += sizeI){
            BigInteger[] hash = tr1.trace.query(i, i + sizeI, ServiceRegister.getProvider().getHashCreator());

            if(hash != null && hash.length > 1) {
                String hashString = hash[0] + " " + hash[1];

                if (!dict1.contains(hashString))
                    dict1.set(hashString, 0);


                dict1.set(hashString, dict1.get(hashString) + 1);
                dict2.set(hashString, 0);
            }
        }
    }


}
