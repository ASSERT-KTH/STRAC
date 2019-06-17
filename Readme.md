## Token series toolkit
[![Build Status](https://travis-ci.org/Jacarte/bufferedDTW.svg?branch=master)](https://travis-ci.org/Jacarte/bufferedDTW)

This tool is based in two principal concepts, one for ngrams processing and another for time warping processing.The algorithms are implemented with a MappedByteBuffer data structure abstraction,bringing the capability of processing large files in an efficent way. 

All files are processed line by line, asigning a different integer to a different String line , which is the alphabet. For example: 
```
malloc   ------ 1
free     ------ 2
malloc   ------ 1
malloc   ------ 1
```


- [Ngram tool](docs/ngram.md)

- [Align tool](docs/align.md)