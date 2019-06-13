
### NGram tools


**We are implementing distance measures based on [A survey of Binary Similarity Measures](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.352.6123&rep=rep1&type=pdf) paper**

#### Sets operations DSL (seti language)

This DSL provides a ligthweight method to make numeric and sets operations over sets. Seti's semantic is really simple.Seti has only two global variables (s1, s2), mapping to set1 and set2 to be operated and any numeric operation. 
Besides that seti provide basic Math numeric methods (listed below). Every expression in seti returns a double.

#### Built in math method
 -  ```pow(a, b)``` Calculates a power b
 - ```sqrt(a)``` Calculates square root of a
 - ```log(number)```
 - ```max(a, b, ...)``` Calculates max number between the arguments
 - ```min(a, b, ...)``` Calculates min number between the arguments
 - ```abs(a)``` Calculates the absolute value of a
 - ```acos(a)```
 - ```atan(a)```
 - ```asin(a)```
 - ```sum(a, b, ...)``` Calculates the sum of all arguments
 - ```mul(a, b, ...)``` Calculates the multiplication of all arguments
##### Seti grammar
```

set: 's1' | 's2'

setOperation -> set (('U' | '&' | '<') set)*;

setLength  -> | setOperation |;

expression -> factor (('+' | '-') expression)*;

factor	   ->	operand (('*' | '/') factor)*;

operand	   ->	REAL | setLength | '(' expression ')' | funcall;

funcall    ->   name expression+;

program	   -> expression;
```

#### Similarities and distances using seti

- Jaccard distance ``` |s1 & s2|/| s1 U s2 | ```
- Dice distance ``` |s1 & s2|/(| s1 | + | s2 |) ```

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
