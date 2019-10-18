# STRAC (Scalable Trace Comparison project) [![Build Status](https://travis-ci.org/Jacarte/STRAC.svg?branch=master)](https://travis-ci.org/Jacarte/STRAC)

STRAC, a tools collection tailored to compare traces.

- [STRAC-core](https://github.com/Jacarte/STRACcore/tree/ecd8ea446b310bdb068feffa3f1f5a8ba43ef41a) Provides a kernel for trace collection from http requests, command execution standard output and flat files

- [STRAC-align](/STRACAlign) State of the art DTW implementations, it uses the techniques described in [TODO paper rer]

- [STRAC-v8tor](/STRACv8tor) Using a V8 TRACE_IGNITION compiled version, we provide a tool which shows covered JavaScript using bytecode declaration and execution trace.

### Prerequisites

- java >=11

## Deployment

### Releases

[Lastest release](https://github.com/Jacarte/STRAC/releases/download/0.21/STRAC.zip)

## Authors

* Javier Cabrera
* Martin Monperrus
* Benoit Baudry

## Research paper

```
@inproceedings{CabreraArteaga:2019:SCJ:3358504.3361228,
 author = {Cabrera Arteaga, Javier and Monperrus, Martin and Baudry, Benoit},
 title = {Scalable Comparison of JavaScript V8 Bytecode Traces},
 booktitle = {Proceedings of the 11th ACM SIGPLAN International Workshop on Virtual Machines and Intermediate Languages},
 series = {VMIL 2019},
 year = {2019},
 isbn = {978-1-4503-6987-9},
 location = {Athens, Greece},
 pages = {22--31},
 numpages = {10},
 url = {http://doi.acm.org/10.1145/3358504.3361228},
 doi = {10.1145/3358504.3361228},
 acmid = {3361228},
 publisher = {ACM},
 address = {New York, NY, USA},
 keywords = {Bytecode, JavaScript, Sequence alignment, Similarity measurement, V8},
} 

```

## Acknowledgments

* Inspired from <https://gist.github.com/PurpleBooth/109311bb0361f32d87a2>

