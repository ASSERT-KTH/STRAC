package core;

import align.Aligner;
import align.Cell;
import align.ICellComparer;
import align.annotations.EventDistance;
import align.implementations.WindowedDTW;
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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

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


    public static void setup(){

        functionDistance = new HashMap<>();
        aligners = new HashMap<>();

        ClassLoader classLoader = Aligner.class.getClassLoader();

        Arrays
                .stream(classLoader.getDefinedPackages())
        .map(t -> new Tuple<>(t.getName(), t.getName().replace(".",  "/")))
        .filter( t-> !t.item1.equals(""))
        .map(t -> new Tuple<>(t.item1, new File(classLoader.getResource(t.item2).getFile())))
        .filter(t -> t.item2.exists())
        .flatMap( t -> Arrays.stream(
                t.item2.list()).map(f ->
                new Tuple<>(t.item1, f)
                ))
        .filter( t -> t.item2.endsWith(".class"))
        .map(t -> new Tuple<>(t.item1, t.item2.substring(0, t.item2.length() - 6)))
        .map(t -> loadClass(classLoader, t.item2, t.item1))
        .filter( c -> c.getDeclaredAnnotation(align.annotations.Aligner.class) != null
        || c.getDeclaredAnnotation(align.annotations.EventDistance.class) != null)
        .forEach(t -> {

            LogProvider.info("Registering class", t.getName());

            EventDistance distanceAnnotation = (EventDistance) t.getDeclaredAnnotation(EventDistance.class);
            align.annotations.Aligner alignerAnnotation  = (align.annotations.Aligner) t.getDeclaredAnnotation(align.annotations.Aligner.class);


            if(distanceAnnotation != null){
                try {
                    functionDistance.put(distanceAnnotation.name(), (ICellComparer) t.getConstructors()[0].newInstance());

                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            if (alignerAnnotation != null) {
                aligners.put(alignerAnnotation.name(), t);
            }
        })
                ;

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
        if(!registered)
            setup();

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
