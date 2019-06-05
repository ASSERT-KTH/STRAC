## Token series toolkit

### Dynamic time warping

Time warping algorithm ares implemented with MappedByteBuffer data structure bringing the capability of processing large files in an efficent way.

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

#### Use cli

- Call java Align.main **config.json**

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

We also implement a tiny ngram query tool, with comparisson and ngram set exporting capability. The ngram sequence is hashing to a two vector structure to achieve a fast and light way to compare and store ngrams, we are taking in count that the collision probability between this hashes is low due to alphabet size in the posible token series. The whole query data struct is stored in a SegmentTree, then getting the sequence hash of an interval is retrieved in the query SegmentTree node operation.

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

ISet s1 = generator.getNGramSet(1000, traces.get(i).trace)
ISet s2 = generator.getNGramSet(1000, traces.get(j).trace)


// Compute Jaccard distance

```
