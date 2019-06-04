## Time warping toolkit

Time warping algorithms implemented with MappedByteBuffer data structure bringing the capability of processing large files in an efficent way 

### Included algorithms
- DTW
- FastDTW
- WindowedDTW

### Use source code

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

### Use cli

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

### Output example

```
2019-06-04 23:03:59 INFO  LogProvider:33 - DTW Distance 119170.0
2019-06-04 23:03:59 INFO  LogProvider:33 - Distance 0.0
2019-06-04 23:03:59 INFO  LogProvider:33 - Writing align result to file
2019-06-04 23:03:59 INFO  LogProvider:33 - Exporting to image
2019-06-04 23:04:04 INFO  LogProvider:33 - Exporting  json distances
2019-06-04 23:04:04 INFO  LogProvider:33 - Disposing map files
```
