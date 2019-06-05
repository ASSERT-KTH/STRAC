## Token series toolkit

This tool is based in two milestones or subtools, one for ngrams processing and another for time warping processing.The algorithms are implemented with a MappedByteBuffer data structure abstraction,bringing the capability of processing large files in an efficent way. 

All files are processed line by line, asigning a different integer to a different String line (That is the alphabet actually). For example: 
```
malloc   ------ 1
free     ------ 2
malloc   ------ 1
malloc   ------ 1
```

In the case of ngram query tool, we have comparisson and ngram-set exporting capabilities. The ngram sequence is <a href="https://github.com/Jacarte/bufferedDTW/blob/master/src/main/java/core/utils/HashingHelper.java">hashing</a> into a integer tuple of size 2 to achieve a fast and lite way to compare and store ngrams, we are taking in count that the collision probability between these hashes is low due to alphabet size in the posible token series. The whole query data struct is stored in a SegmentTree, then, getting the sequence hash of an interval is retrieved in the query SegmentTree node operation.

```
    [0:4] -> hx
     /        \
    /          \
 [0:1] -> h1   [2: 3] -> h2     [4:5] -> h1  
  /\            /\                       / \
1   2          3  4                     1   2 

```

### Dynamic time warping

#### Included algorithms
- DTW
- FastDTW
- WindowedDTW

#### Use source code

1 - Register collection allocator

```java
ServiceRegister.registerProvider
```

2 - Map files
```java
TraceHelper helper = new TraceHelper()
List<TraceMap> traces = helper.mapTraceSetByFileLine(new String[] { "absolute_path1.txt", "absolute_path2.txt"}, false);
        
```

3 - Call align algorithm

```java

FastDTW dtw = new FastDTW(radius, (x, y) -> /*two values comparer*/)

AligDistance distance = dtw.align(traces.get(i).plainTrace,
traces.get(j).plainTrace)

```

#### Use from console

- Call ```java Align.main **config.json**```

```json
{
  "files": [
    "t1.bytecode.txt",
    "t2.bytecode.txt"
  ],
  "pairs": [

  ],
  "method":
  {
    "name": "FastDTW",
    "params": [100]
  },
  "comparison": {
    "eq": 0,
    "diff": 2,
    "gap": 0
  },
  "outputDir": "reports",
  "outputAlignment": true,
  "outputAlignmentMap": "map.json",
  "exportHTML": false,
  "exportImage": true
}

```

### NGram tools


#### Use in code

1 - Register collection allocator

```java
ServiceRegister.registerProvider
```

2 - Map files
```java
TraceHelper helper = new TraceHelper()
List<TraceMap> traces = helper.mapTraceSetByFileLine(new String[] { "absolute_path1.txt", "absolute_path2.txt"}, true);

// The segment tree for one trace is stored in the **trace** property.

```



3 - Invoke a set generator that works with the trace trees

```java

Generator generator = new StringKeyGenerator(t -> t[0] + " " + t[1]);

...
 
ISet nGramSetOfSize1000 = generator.getNGramSet(1000, traces.get(i).trace)


```

4 - Work with sets

```java

ISet s1 = generator.getNGramSet(1000, traces.get(i).trace).keySet();
ISet s2 = generator.getNGramSet(1000, traces.get(j).trace).keySet();


// Computing Jaccard distance for example
...
return 1 - 1.0*s1.intersect(s2).size()/(s1.union(s2).size());

```

### Use from console

- Call ```java Main.main **config.json**```

```json
{
  "files": [

    "file1",
    "file2"
  ],

  "method": {
    "name": "Jaccard",
    "params": [1000],
    "names": ["size"]
  },
  "exportComparisson": "out.json",
  "printComparisson": true,
  "exportSegmentTrees": true,
  "exportBag": "bag.json",
  "exportNgram": [10, 30, 1000, 200000]
}

```
