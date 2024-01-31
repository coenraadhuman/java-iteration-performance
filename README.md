# java-iteration-performance

After reading up a bit regarding loops vs streams and the performance impact of iterating over items I decided to do a little study on it myself.

_The general expectation in terms of the outcomes for the study:_

Small Collections:
- Looping over Arrays should be the quickest due to how they are stored in memory.
- Looping over Lists come in second due to how the data structure works pointing to the next element which can be anywhere in memory.
- Sequential Streams should be third due to the overhead of creating the Stream from the source to iterate over each element.
- Parallel Streams should be the slowest due to the overhead of creating the Stream and utilising multiple threads from the source to iterate over each element.

Large Collections:
- Parallel Streams should be the most performant here.

### External Resources

Here is some of the material I read:

- [Java Performance Improvement - Java 8+ Streams vs Loops and Lists vs Arrays](https://medium.com/levi-niners-crafts/java-performance-improvement-java-8-streams-vs-loops-and-lists-vs-arrays-e824136832d6)
- [Java Streams: The Good, The Bad, and The Ugly](https://medium.com/@harshgajjar7110/java-streams-the-good-the-bad-and-the-ugly-6f6b54526619)
- [A Performance Comparison of Java
Streams and Imperative Loops](https://www.diva-portal.org/smash/get/diva2:1783234/FULLTEXT01.pdf)

### Testing Machine

I created a VM on VirtualBox with [Alpine Linux](https://dl-cdn.alpinelinux.org/alpine/v3.19/releases/x86_64/alpine-standard-3.19.1-x86_64.iso) installed as an isolated environment in attempt to avoid external factors with the tests.

VirtualBox configuration:

![virtualbox](./.images/Screenshot%20from%202024-01-31%2015-48-39.png)

Distro resources on idle:

![htop](./.images/Screenshot%20from%202024-01-31%2015-41-10.png)

### The Results

#### _Processed Output:_

- Running it the first time:

| Collection Size   | 10          | 100         | 1000        | 10000      | 100000  | 1000000   | 10000000  |
|-------------------|-------------|-------------|-------------|------------|---------|-----------|-----------|
| Runs              | 100000      | 50000       | 5000        | 50         | 20      | 20        | 10        |
| -                 | -           | -           | -           | -          | -       | -         | -         |
| Arrays            | 5.379500 μs | 49.41080 μs | 483.2548 μs | 4783.66 μs | 48.0 ms | 552.55 ms | 5325.4 ms |
| Lists             | 4.901510 μs | 49.66132 μs | 488.3112 μs | 4988.46 μs | 48.7 ms | 557.00 ms | 5362.5 ms |
| Sequential Stream | 5.260710 μs | 49.71438 μs | 487.3034 μs | 4901.60 μs | 49.8 ms | 555.80 ms | 5331.3 ms |
| Parallel Stream   | 17.84742 μs | 46.02784 μs | 204.6934 μs | 1690.88 μs | 16.3 ms | 218.25 ms | 2170.8 ms |

Interestingly enough we can see that the Arrays didn't perform as expected. My suspicion here is with how the JVM and its JIT.

- Running it a second time:

| Collection Size   | 10          | 100         | 1000        | 10000      | 100000   | 1000000   | 10000000  |
|-------------------|-------------|-------------|-------------|------------|----------|-----------|-----------|
| Runs              | 100000      | 50000       | 5000        | 50         | 20       | 20        | 10        |
| -                 | -           | -           | -           | -          | -        | -         | -         |
| Arrays            | 5.421080 μs | 51.05166 μs | 505.7332 μs | 5015.08 μs | 52.55 ms | 563.85 ms | 5405.2 ms |
| Lists             | 5.394920 μs | 50.84128 μs | 502.8742 μs | 5029.28 μs | 50.40 ms | 543.00 ms | 5407.5 ms |
| Sequential Stream | 5.032100 μs | 51.03054 μs | 503.4606 μs | 5038.74 μs | 50.55 ms | 570.20 ms | 5312.4 ms |
| Parallel Stream   | 19.41462 μs | 45.74524 μs | 205.9434 μs | 1729.28 μs | 16.20 ms | 202.65 ms | 2096.8 ms |

We can see here that the List now very nearly had the same performance as the Arrays, I bit surprised by the Sequential Stream.

- Running it a third time:

| Collection Size   | 10          | 100         | 1000        | 10000      | 100000   | 1000000   | 10000000  |
|-------------------|-------------|-------------|-------------|------------|----------|-----------|-----------|
| Runs              | 100000      | 50000       | 5000        | 50         | 20       | 20        | 10        |
| -                 | -           | -           | -           | -          | -        | -         | -         |
| Arrays            | 5.057920 μs | 49.71842 μs | 502.9662 μs | 5022.76 μs | 50.55 ms | 560.00 ms | 5348.1 ms |
| Lists             | 5.350600 μs | 49.16390 μs | 495.7138 μs | 5000.86 μs | 48.85 ms | 556.05 ms | 5240.9 ms |
| Sequential Stream | 5.530490 μs | 49.51056 μs | 489.6390 μs | 4958.62 μs | 48.60 ms | 552.30 ms | 5301.3 ms |
| Parallel Stream   | 17.74439 μs | 45.09636 μs | 201.2644 μs | 1637.20 μs | 15.75 ms | 199.10 ms | 2061.0 ms |

Running it a third time I only started seeing the results that I expected from my readings and understanding of how it all works.

#### _Raw Output:_

See the following Markdown file for the output: [RAW_RESULTS.md](./RAW_RESULTS.md)

### Conclusion

- After reviewing the results the expected outcome was matched.
- I think though that given how closely the Arrays, Lists and Sequentials Stream perform, that just using Streams with an utility to switch between the Sequentional or a Parallel Stream makes the most sense given that you will have **better code scalibility**.