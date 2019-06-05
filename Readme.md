## Token series toolkit

This tool is based in two principal concepts, one for ngrams processing and another for time warping processing.The algorithms are implemented with a MappedByteBuffer data structure abstraction,bringing the capability of processing large files in an efficent way. 

All files are processed line by line, asigning a different integer to a different String line , which is the alphabet. For example: 
```
malloc   ------ 1
free     ------ 2
malloc   ------ 1
malloc   ------ 1
```

In the case of ngram query tool, we have comparisson and ngram-set exporting capabilities. The ngram sequence is <a href="https://github.com/Jacarte/bufferedDTW/blob/master/src/main/java/core/utils/HashingHelper.java">hashing</a> into a integer tuple of size 2 to achieve a fast-lite way to compare and store ngrams, we are taking in count that there is no collision probability between these hashes due to alphabet size in the possible token series. The whole query data structure is stored in a SegmentTree, then, getting the sequence hash of an interval is retrieved in the query SegmentTree node operation.

```
    [0:4] -> hx
     /        \
    /          \
 [0:1] -> h1   [2: 3] -> h2     [4:5] -> h1  
  /\            /\                       / \
1   2          3  4                     1   2 

```

- [Ngram tool](docs/ngram.md)

- [Align tool](docs/align.md)