# STRAC (Scalable Trace Comparison project) [![Build Status](https://travis-ci.org/KTH/STRAC.svg?branch=master)](https://travis-ci.org/KTH/STRAC)

STRAC, a tools collection tailored to compare traces.

# Included implementations

### Prerequisites

- java >= 11

#### To execute with SIMD

We included a vectorized version of DTW, unfotunately, JAVA doesn't support vectorizaiton yet. It is an experimental feature for jdk 15.
You can avoid this feature changing the jdk target version, `export JAVA_TARGET=11`, also you need to remove the vecctorized implementation 
`rm STRACAlign/src/main/java/strac/align/align/implementations/SIMDDTW.java`



Use project Panama https://jdk.java.net/panama/
To run this project with the vectorized implementation, you will need a specific development build of OpenJDK, containing the OpenJDK jdk.incubator.vector module. You will need to build it from source.

Detailed build instructions are available at http://hg.openjdk.java.net/panama/dev/ in doc/building.md.

You will need a regular Java 11 installation, a Mercurial client, a usual build environment, and on Windows, Cygwin and the Microsoft Visual C++ Build Tools 2017.

In a shell (on Windows, in a Cygwin shell), run:
```
hg clone http://hg.openjdk.java.net/panama/dev/
cd dev
hg checkout vectorIntrinsics  # the branch we need is vectorIntrinsics
bash configure --disable-warnings-as-errors
make images
```

If everything worked properly, the compiled JDK will be available at build/*/images/jdk (for example, the java tool is available at build/*/images/jdk/bin/java).

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

