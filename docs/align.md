
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
    "t2.bytecode.txt",
    "t3.bytecode.txt",
    "t4.bytecode.txt"
  ],
  "pairs": [
        [0, 1],
        [0, 3]
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
