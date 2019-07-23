package core;

import align.Aligner;
import align.Cell;
import align.ICellComparer;
import align.annotations.EventDistance;
import align.event_distance.DInst;
import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.WindowedDTW;
import com.google.common.reflect.ClassPath;
import core.data_structures.IArray;
import core.data_structures.IDisposable;
import core.data_structures.IMultidimensionalArray;
import core.data_structures.Tuple;
import core.data_structures.buffered.DoubleCostMatrix;
import core.data_structures.buffered.BufferedCollectionInteger;
import core.data_structures.buffered.BufferedCollectionLong;
import core.data_structures.buffered.BufferedWarpPath;
import core.data_structures.memory.InMemoryArray;
import core.data_structures.memory.InMemoryLongArray;
import core.data_structures.memory.InMemoryMultidimensional;
import core.data_structures.memory.InMemoryWarpPath;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static core.utils.HashingHelper.getRandomName;

public class ServiceRegister {

    static IServiceProvider _provider;

    public static void registerProvider(IServiceProvider provider){
        _provider = provider;
    }

    static List<IDisposable> openedArrays = new ArrayList<>();

    static Map<String, Class<? extends Aligner>> aligners;
    static Map<String, ICellComparer> functionDistance;

    static boolean registered = false;


    public static void setup() throws IOException, ClassNotFoundException {

        //Registering native aligners
        LogProvider.info(DTW.class, DInst.class, FastDTW.class, WindowedDTW.class);

        functionDistance = new HashMap<>();
        aligners = new HashMap<>();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();


        Reflections reflections = new Reflections("my.project");

        Arrays.stream(loader.getDefinedPackages())
                .flatMap(t -> new Reflections(t.getName()).getTypesAnnotatedWith(align.annotations.Aligner.class).stream())
                .forEach(t -> {

                    LogProvider.info("Registering aligner...", t);
                    aligners.put(t.getDeclaredAnnotation(align.annotations.Aligner.class).name(), (Class<? extends Aligner>)t);
                });



        Arrays.stream(loader.getDefinedPackages())
                .flatMap(t -> new Reflections(t.getName()).getTypesAnnotatedWith(align.annotations.EventDistance.class).stream())
                .forEach(t -> {

                    LogProvider.info("Registering distance...", t);
                    try {
                        functionDistance.put((t.getDeclaredAnnotation(EventDistance.class)).name(),
                                (ICellComparer) t.getDeclaredConstructors()[0].newInstance()
                                );
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

    public static IServiceProvider getProvider(){

        /*IArray<Integer> allocateIntegerArray(String id, long size, ALLOCATION_METHOD method);

        IArray<Long> (String id, long size, ALLOCATION_METHOD method);

        IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrix(long maxI, long maxJ, ALLOCATION_METHOD method);
*/

        if(_provider == null){
            _provider = new IServiceProvider() {
                @Override
                public ALLOCATION_METHOD selectMethod(long size) {

                    if(size < 1L << 29)
                        return ALLOCATION_METHOD.MEMORY;

                    return ALLOCATION_METHOD.EXTERNAL;
                }

                @Override
                public IArray<Integer> allocateIntegerArray(String id, long size, ALLOCATION_METHOD method) {

                    if(method == ALLOCATION_METHOD.MEMORY){
                        return new InMemoryArray(id, (int)size);
                    }

                    IArray<Integer> result = new BufferedCollectionInteger(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public IArray<Cell> allocateWarpPath(String id, long size, ALLOCATION_METHOD method) {
                    if(method == ALLOCATION_METHOD.MEMORY){
                        return new InMemoryWarpPath(null, (int)size);
                    }

                    IArray<Cell> result = new BufferedWarpPath(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public IArray<Long> allocateLonArray(String id, long size, ALLOCATION_METHOD method) {
                    if(method == ALLOCATION_METHOD.MEMORY){
                        return new InMemoryLongArray(id, (int)size);
                    }

                    IArray<Long> result = new BufferedCollectionLong(id==null? getRandomName(): id, size, Integer.MAX_VALUE/2);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrix(long maxI, long maxJ, ALLOCATION_METHOD method) {

                    if(method == ALLOCATION_METHOD.MEMORY)
                        return new InMemoryMultidimensional( maxI, maxJ);

                    DoubleCostMatrix result = new DoubleCostMatrix(getRandomName(), maxI, maxJ);

                    openedArrays.add(result);

                    return result;
                }

                @Override
                public IMultidimensionalArray<Double> allocateDoubleBidimensionalMatrixWindow(long maxI, long maxJ, ALLOCATION_METHOD method, WindowedDTW.Window window) {
                    if(method == ALLOCATION_METHOD.MEMORY)
                        return new InMemoryMultidimensional( maxI, maxJ);

                    DoubleCostMatrix result = new DoubleCostMatrix(getRandomName(), maxI, maxJ, window);

                    openedArrays.add(result);

                    return result;
                }


            };
        }
        return _provider;
    }

    public static void dispose(){

        for(IDisposable arr: openedArrays)
            arr.dispose();
    }

}
