package core;

import core.stream_providers.CommandStdInputProvider;
import core.stream_providers.FileInputProvider;
import core.stream_providers.NetworkInputProvider;
import core.stream_providers.ResourceInputProvider;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StreamProviderFactory implements TraceHelper.IStreamProvider {


    static List<TraceHelper.IStreamProvider> providers = new ArrayList<>();

    static StreamProviderFactory instance;

    private StreamProviderFactory(){

    }

    public static StreamProviderFactory getInstance(){
        if(instance == null) {
            instance = new StreamProviderFactory();

            providers.add(new NetworkInputProvider());
            providers.add(new FileInputProvider());
            providers.add(new ResourceInputProvider());
            providers.add(new CommandStdInputProvider());
        }

        return instance;
    }

    @Override
    public InputStream getStream(String filename) {
        for(TraceHelper.IStreamProvider provider: providers)
            if(provider.validate(filename)) {
                LogProvider.info("Taking", provider.getClass(), "instance as provider");
                return provider.getStream(filename);
            }

        throw new RuntimeException(String.format("There is no valid stream provider for : %s", filename));
    }

    @Override
    public boolean validate(String filename) {
        for(TraceHelper.IStreamProvider provider: providers)
            if(provider.validate(filename))
                return true;

        return false;
    }
}
