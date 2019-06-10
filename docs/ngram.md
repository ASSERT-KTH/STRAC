
### NGram tools


**We are implementing distance measures based on [A survey of Binary Similarity Measures](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.352.6123&rep=rep1&type=pdf) paper**

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


// Computing Jaccard distance
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
