package strac.align.utils;

import strac.align.align.Aligner;
import strac.align.align.ICellComparer;
import strac.align.align.annotations.EventDistance;
import strac.align.align.event_distance.DInst;
import strac.align.align.implementations.DTW;
import strac.align.align.implementations.Evolutive;
import strac.align.align.implementations.FastDTW;
import strac.align.align.implementations.WindowedDTW;
import strac.core.LogProvider;
import strac.core.utils.IServiceProvider;
import strac.core.utils.ServiceRegister;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AlignServiceProvider extends ServiceRegister {

    static Map<String, Class<? extends Aligner>> aligners;
    static Map<String, ICellComparer> functionDistance;

    static boolean registered = false;


    public static void setup() throws IOException, ClassNotFoundException {

        //Registering native aligners
        LogProvider.info(DTW.class, DInst.class, FastDTW.class, WindowedDTW.class, Evolutive.class);

        functionDistance = new HashMap<>();
        aligners = new HashMap<>();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();


        Reflections reflections = new Reflections("my.project");

        Arrays.stream(loader.getDefinedPackages())
                .flatMap(t -> new Reflections(t.getName()).getTypesAnnotatedWith(strac.align.align.annotations.Aligner.class).stream())
                .forEach(t -> {

                    if(!aligners.containsKey(t.getDeclaredAnnotation(strac.align.align.annotations.Aligner.class).name())) {
                        LogProvider.info("Registering aligner...", t);
                        aligners.put(t.getDeclaredAnnotation(strac.align.align.annotations.Aligner.class).name(), (Class<? extends Aligner>) t);
                    }
                });



        Arrays.stream(loader.getDefinedPackages())
                .flatMap(t -> new Reflections(t.getName()).getTypesAnnotatedWith(strac.align.align.annotations.EventDistance.class).stream())
                .forEach(t -> {

                    LogProvider.info("Registering distance...", t);
                    try {

                        if(!functionDistance.containsKey((t.getDeclaredAnnotation(EventDistance.class)).name())) {
                            functionDistance.put((t.getDeclaredAnnotation(EventDistance.class)).name(),
                                    (ICellComparer) t.getDeclaredConstructors()[0].newInstance()
                            );
                        }
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });


        registered = true;
    }

    public static void registerFunction(String name, ICellComparer comparer){

        if(functionDistance == null)
            functionDistance = new HashMap<>();

        functionDistance.put(name, comparer);
    }

    static Class loadClass(ClassLoader loader, String name, String pkg){
        try {
            return loader.loadClass(String.format("%s.%s", pkg, name));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error");
        }
    }

    public static Aligner getAligner(String name, Object[] constructorParameters, ICellComparer comparer)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {

        if(!aligners.containsKey(name))
            throw new RuntimeException("There is no aligner named " + name);

        Object[] parameters = new Object[constructorParameters.length + 1];
        parameters[0] = comparer;

        for(int i = 0; i < constructorParameters.length; i++)
            parameters[i + 1] = constructorParameters[i];

        LogProvider.info("Initializing aligner. Parameter types", comparer,
                String.join(",", Arrays.stream(constructorParameters).map(t -> t.getClass().toString()).collect(Collectors.toList())));

        LogProvider.info("Constructor types",
                String.join(",", Arrays.stream(aligners.get(name).getConstructors()[0].getParameterTypes()).map(t -> t.toString()).collect(Collectors.toList())));

        return (Aligner) aligners.get(name).getConstructors()[0].newInstance(parameters);
    }

    public static ICellComparer getComparer(String name){

        if(!functionDistance.containsKey(name))
            throw new RuntimeException("There is no event comparer " + name);

        return functionDistance.get(name);
    }

    static AlignServiceProvider provider;
    static IAlignAllocator allocator;

    public static AlignServiceProvider getInstance(){
        if(provider == null)
            provider = new AlignServiceProvider();

        return provider;
    }

    public IAlignAllocator getAllocator(){
        return (IAlignAllocator) getProvider();
    }

    @Override
    public IServiceProvider getProvider() {

        if(allocator == null){
            allocator = new AlignAllocator();
        }

        return allocator;
    }
}
