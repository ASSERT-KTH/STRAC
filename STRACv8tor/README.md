# STRAC (Scalable Trace Comparison v8 bytecode coverage) v8tor [![Build Status](https://travis-ci.org/Jacarte/STRAC.svg?branch=master)](https://travis-ci.org/Jacarte/STRAC)


V8tor executes an specific chrome version, compiled with flag V8_TRACE_IGNITION set on true. This Chromium version writes the traces of executed bytecode to standard output as ```--print-bytecode``` builtin option does. [TODO ref]

In all bytecode log types, V8 provides information to correctly map between JS and bytecode representation. Both bytecode instructions, static declared and execution traces, have the memory address assigned by the interpreter in the trace log, for example, ```34123 E> 0x123a124 @... ``` and ```-> 0x123a124 @ ...``` are static and execution bytecode traces respectively. 


The main idea is to map execution trace addresses with static declared ones. 

```
foreach (instruction of static)
    visited[instruction.address] = 0
foreach(instruccion of execution)
    visited[instruction.address]++
   
covered = visited.filter(t.count > 0)
total = visited.keys.size

coverage = covered/total

```

### Output example

```
Static sentences: 28768
Covered sentences: 19989
Weird sentences: 0
Coverage: 0.6948345383759733
```

## Getting Started
- Download release files
- Execute  ```java -jar STRACv8tor.jar --url http://google.com --bin chrome --time 120```


### Prerequisites

- java >=11

## Deployment

### Releases

[Lastest release](https://github.com/Jacarte/STRAC/releases/download/0.21/STRAC.zip)

## Authors

Give a list of authors

* Javier Cabrera

## Acknowledgments

* Inspired from <https://gist.github.com/PurpleBooth/109311bb0361f32d87a2>

