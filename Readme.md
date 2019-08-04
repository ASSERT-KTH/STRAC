# STRAC (Scalable Trace Comparison project) [![Build Status](https://travis-ci.org/Jacarte/STRAC.svg?branch=master)](https://travis-ci.org/Jacarte/STRAC)

STRAC, a scalable and extensible tool tailored to compare bytecode traces generated by the V8 JavaScript engine. Given two V8 bytecode traces and a distance function between trace events, STRAC computes and provides the best alignment. The key insight is to split access between memory and disk. STRAC can identify semantically equivalent web pages and is capable of processing huge V8 bytecode traces whose order of magnitude matches today's web.


## Getting Started
- Download release files
    - java -jar STRAC.jar [payload.json]

### Payload json format


```javascript
    
{
  "files": ["file1", "file2", ..., "filex"],
  "pairs": [[0, 1], [2, 3]] , // pairs to be compared, empty array invoke pairwise comparisson in all possible combinations
  "method": {
    "name": "FastDTW", // Align method
    "params": [100.0] // Parameters
  },
  "outputAlignment": true, // output aligned trace
  "distanceFunctionName": "dSen", // built in distance function
  "separator": "\r\n", // event separator in the log file
  "clean": ["@", "[\r\t\n ]"], // Clean log event using Java regular expressions pressent in the array
  "include": {
    "pattern": "(\\d)",
    "group": 0
  }, // select first matching regular expression after applyting clean filters
  "comparison": { // In case of built in distance function is not declared, STRAC will use a function based on this three parameters 
    eq: 0,
    diff: 2,
    gap: 1
  }
  
}

```

### Prerequisites

- java >=11

## Deployment

### Releases

[Lastest release](https://github.com/Jacarte/STRAC/releases/tag/0.11)

## Authors

Give a list of authors

* Javier Cabrera

## Acknowledgments

* Inspired from <https://gist.github.com/PurpleBooth/109311bb0361f32d87a2>

