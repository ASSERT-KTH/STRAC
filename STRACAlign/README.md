# STRAC-align (Scalable Trace Comparison aligning) [![Build Status](https://travis-ci.org/Jacarte/STRAC.svg?branch=master)](https://travis-ci.org/Jacarte/STRAC)

Given two V8 bytecode traces and a distance function between trace events, STRAC-align computes and provides the best alignment. The key insight is to split access between memory and disk. STRAC-align can identify semantically equivalent web pages and is capable of processing huge V8 bytecode traces whose order of magnitude matches today's web.


## Getting Started
- Download release files
- Execute  ```java -jar STRAC-align.jar [payload.json]```

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
  "separator": "[\r\n]", // Event separator in the log file. Use Java regular expression to define a better separator
  "clean": ["@", "[\r\t\n ]"], // Clean log event using Java regular expressions in this property
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

#### How STRAC process files

STRAC processes files using a protocol discriminator in the following order:
- Network fetching: ```https://something.something```
- File path: ```/file.txt```
- Command standard output: ```chrome --js-flags="--print-bytecode" http://www.google.com```

If the provided file cannot pass any of the previous providers, a runtime exception is invoked

#### Practical payload example

This json file will execute FastDTW algorith with radius 100 in two chrome execution output. 

```json
{
  "files": [
    "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --headless --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\" http://www.google.com",
    "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --headless --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  http://www.github.com"],
  "pairs": [] ,
  "method": {
    "name": "FastDTW",
    "params": [100.0]
  },
  "outputAlignment": true,
  "distanceFunctionName": "dBin",
  "separator": "[\r\n]",
  "clean": [
    "( )*\\d+ [ES]>",
    "0x\\w+ @",
    "\\w+ : ",
    " [A-Z](.*)"],
  "include": {
    "pattern": "^([0-9a-f]{2})",
    "group": 0
  },
  "comparison": {
    "eq": 0,
    "diff":5,
    "gap": 1
  }

}
```



### Using STRAC-align from code

### How to extend STRAC-align

#### Define new Aligner

Explain about STRAC uses reflection to register new aligners

#### Define new event distance

Explain about STRAC uses event distances



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

