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

![virtualbox]()

Distro resources on idle:

![htop]()

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

- Running it the first time:

```
alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar array

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:42:00.275+02:00  INFO 2486 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2486 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:42:00.277+02:00  INFO 2486 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:42:00.760+02:00  INFO 2486 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.756 seconds (process running for 1.271)
2024-01-31T15:42:01.386+02:00  INFO 2486 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 100000: collection average finish time 5.3795 μs with 10 elements at an average per item of 0.53795 μs
2024-01-31T15:42:03.887+02:00  INFO 2486 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 50000: collection average finish time 49.4108 μs with 100 elements at an average per item of 0.494108 μs
2024-01-31T15:42:06.307+02:00  INFO 2486 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 5000: collection average finish time 483.2548 μs with 1000 elements at an average per item of 0.4832548 μs
2024-01-31T15:42:06.547+02:00  INFO 2486 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 50: collection average finish time 4783.66 μs with 10000 elements at an average per item of 0.47836599999999996 μs
2024-01-31T15:42:07.518+02:00  INFO 2486 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 20: collection average finish time 48.0 ms with 100000 elements at an average per item of 4.8E-4 ms
2024-01-31T15:42:18.582+02:00  INFO 2486 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 20: collection average finish time 552.55 ms with 1000000 elements at an average per item of 5.5255E-4 ms
2024-01-31T15:43:11.842+02:00  INFO 2486 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 10: collection average finish time 5325.4 ms with 10000000 elements at an average per item of 5.3254E-4 ms
alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar  list

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:43:21.029+02:00  INFO 2517 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2517 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:43:21.032+02:00  INFO 2517 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:43:21.488+02:00  INFO 2517 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.715 seconds (process running for 1.033)
2024-01-31T15:43:22.072+02:00  INFO 2517 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 100000: collection average finish time 4.90151 μs with 10 elements at an average per item of 0.490151 μs
2024-01-31T15:43:24.584+02:00  INFO 2517 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 50000: collection average finish time 49.66132 μs with 100 elements at an average per item of 0.49661320000000003 μs
2024-01-31T15:43:27.029+02:00  INFO 2517 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 5000: collection average finish time 488.3112 μs with 1000 elements at an average per item of 0.4883112 μs
2024-01-31T15:43:27.279+02:00  INFO 2517 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 50: collection average finish time 4988.46 μs with 10000 elements at an average per item of 0.498846 μs
2024-01-31T15:43:28.264+02:00  INFO 2517 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 20: collection average finish time 48.7 ms with 100000 elements at an average per item of 4.87E-4 ms
2024-01-31T15:43:39.414+02:00  INFO 2517 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 20: collection average finish time 557.0 ms with 1000000 elements at an average per item of 5.57E-4 ms
2024-01-31T15:44:33.044+02:00  INFO 2517 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 10: collection average finish time 5362.5 ms with 10000000 elements at an average per item of 5.3625E-4 ms
alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar  stream

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:44:41.779+02:00  INFO 2557 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2557 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:44:41.781+02:00  INFO 2557 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:44:42.241+02:00  INFO 2557 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.716 seconds (process running for 1.084)
2024-01-31T15:44:42.852+02:00  INFO 2557 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 100000: collection average finish time 5.26071 μs with 10 elements at an average per item of 0.5260710000000001 μs
2024-01-31T15:44:45.368+02:00  INFO 2557 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 50000: collection average finish time 49.71438 μs with 100 elements at an average per item of 0.49714379999999997 μs
2024-01-31T15:44:47.808+02:00  INFO 2557 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 5000: collection average finish time 487.3034 μs with 1000 elements at an average per item of 0.4873034 μs
2024-01-31T15:44:48.053+02:00  INFO 2557 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 50: collection average finish time 4901.6 μs with 10000 elements at an average per item of 0.49016000000000004 μs
2024-01-31T15:44:49.060+02:00  INFO 2557 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 20: collection average finish time 49.8 ms with 100000 elements at an average per item of 4.98E-4 ms
2024-01-31T15:45:00.185+02:00  INFO 2557 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 20: collection average finish time 555.8 ms with 1000000 elements at an average per item of 5.558E-4 ms
2024-01-31T15:45:53.504+02:00  INFO 2557 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 10: collection average finish time 5331.3 ms with 10000000 elements at an average per item of 5.3313E-4 ms
alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar parallel

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:46:14.599+02:00  INFO 2590 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2590 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:46:14.601+02:00  INFO 2590 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:46:15.056+02:00  INFO 2590 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.709 seconds (process running for 1.062)
2024-01-31T15:46:16.922+02:00  INFO 2590 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 100000: collection average finish time 17.84742 μs with 10 elements at an average per item of 1.784742 μs
2024-01-31T15:46:19.255+02:00  INFO 2590 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 50000: collection average finish time 46.02784 μs with 100 elements at an average per item of 0.4602784 μs
2024-01-31T15:46:20.282+02:00  INFO 2590 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 5000: collection average finish time 204.6934 μs with 1000 elements at an average per item of 0.2046934 μs
2024-01-31T15:46:20.367+02:00  INFO 2590 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 50: collection average finish time 1690.88 μs with 10000 elements at an average per item of 0.16908800000000002 μs
2024-01-31T15:46:20.699+02:00  INFO 2590 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 20: collection average finish time 16.3 ms with 100000 elements at an average per item of 1.63E-4 ms
2024-01-31T15:46:25.074+02:00  INFO 2590 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 20: collection average finish time 218.25 ms with 1000000 elements at an average per item of 2.1825E-4 ms
2024-01-31T15:46:46.787+02:00  INFO 2590 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 10: collection average finish time 2170.8 ms with 10000000 elements at an average per item of 2.1708000000000003E-4 ms
```

Running it a second time:
```
alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar array
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:47:00.202+02:00  INFO 2625 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2625 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:47:00.204+02:00  INFO 2625 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:47:00.662+02:00  INFO 2625 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.712 seconds (process running for 1.06)
2024-01-31T15:47:01.307+02:00  INFO 2625 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 100000: collection average finish time 5.42108 μs with 10 elements at an average per item of 0.542108 μs
2024-01-31T15:47:03.890+02:00  INFO 2625 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 50000: collection average finish time 51.05166 μs with 100 elements at an average per item of 0.5105166 μs
2024-01-31T15:47:06.422+02:00  INFO 2625 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 5000: collection average finish time 505.7332 μs with 1000 elements at an average per item of 0.5057332 μs
2024-01-31T15:47:06.673+02:00  INFO 2625 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 50: collection average finish time 5015.08 μs with 10000 elements at an average per item of 0.501508 μs
2024-01-31T15:47:07.735+02:00  INFO 2625 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 20: collection average finish time 52.55 ms with 100000 elements at an average per item of 5.254999999999999E-4 ms
2024-01-31T15:47:19.024+02:00  INFO 2625 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 20: collection average finish time 563.85 ms with 1000000 elements at an average per item of 5.638500000000001E-4 ms
2024-01-31T15:48:13.081+02:00  INFO 2625 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 10: collection average finish time 5405.2 ms with 10000000 elements at an average per item of 5.4052E-4 ms

alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar  list

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:48:48.435+02:00  INFO 2665 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2665 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:48:48.437+02:00  INFO 2665 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:48:48.898+02:00  INFO 2665 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.713 seconds (process running for 1.032)
2024-01-31T15:48:49.533+02:00  INFO 2665 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 100000: collection average finish time 5.39492 μs with 10 elements at an average per item of 0.539492 μs
2024-01-31T15:48:52.106+02:00  INFO 2665 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 50000: collection average finish time 50.84128 μs with 100 elements at an average per item of 0.5084128 μs
2024-01-31T15:48:54.624+02:00  INFO 2665 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 5000: collection average finish time 502.8742 μs with 1000 elements at an average per item of 0.5028741999999999 μs
2024-01-31T15:48:54.876+02:00  INFO 2665 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 50: collection average finish time 5029.28 μs with 10000 elements at an average per item of 0.5029279999999999 μs
2024-01-31T15:48:55.895+02:00  INFO 2665 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 20: collection average finish time 50.4 ms with 100000 elements at an average per item of 5.04E-4 ms
2024-01-31T15:49:06.764+02:00  INFO 2665 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 20: collection average finish time 543.0 ms with 1000000 elements at an average per item of 5.43E-4 ms
2024-01-31T15:50:00.845+02:00  INFO 2665 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 10: collection average finish time 5407.5 ms with 10000000 elements at an average per item of 5.4075E-4 ms

alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar  stream

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:50:26.927+02:00  INFO 2697 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2697 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:50:26.929+02:00  INFO 2697 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:50:27.387+02:00  INFO 2697 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.72 seconds (process running for 1.118)
2024-01-31T15:50:27.990+02:00  INFO 2697 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 100000: collection average finish time 5.0321 μs with 10 elements at an average per item of 0.5032099999999999 μs
2024-01-31T15:50:30.572+02:00  INFO 2697 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 50000: collection average finish time 51.03054 μs with 100 elements at an average per item of 0.5103054 μs
2024-01-31T15:50:33.093+02:00  INFO 2697 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 5000: collection average finish time 503.4606 μs with 1000 elements at an average per item of 0.5034606 μs
2024-01-31T15:50:33.345+02:00  INFO 2697 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 50: collection average finish time 5038.74 μs with 10000 elements at an average per item of 0.5038739999999999 μs
2024-01-31T15:50:34.368+02:00  INFO 2697 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 20: collection average finish time 50.55 ms with 100000 elements at an average per item of 5.055E-4 ms
2024-01-31T15:50:45.783+02:00  INFO 2697 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 20: collection average finish time 570.2 ms with 1000000 elements at an average per item of 5.702000000000001E-4 ms
2024-01-31T15:51:38.911+02:00  INFO 2697 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 10: collection average finish time 5312.4 ms with 10000000 elements at an average per item of 5.3124E-4 ms

alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar parallel

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:51:45.390+02:00  INFO 2729 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2729 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:51:45.393+02:00  INFO 2729 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:51:45.853+02:00  INFO 2729 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.717 seconds (process running for 1.076)
2024-01-31T15:51:47.878+02:00  INFO 2729 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 100000: collection average finish time 19.41462 μs with 10 elements at an average per item of 1.941462 μs
2024-01-31T15:51:50.197+02:00  INFO 2729 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 50000: collection average finish time 45.74524 μs with 100 elements at an average per item of 0.45745240000000004 μs
2024-01-31T15:51:51.230+02:00  INFO 2729 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 5000: collection average finish time 205.9434 μs with 1000 elements at an average per item of 0.2059434 μs
2024-01-31T15:51:51.317+02:00  INFO 2729 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 50: collection average finish time 1729.28 μs with 10000 elements at an average per item of 0.172928 μs
2024-01-31T15:51:51.651+02:00  INFO 2729 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 20: collection average finish time 16.2 ms with 100000 elements at an average per item of 1.6199999999999998E-4 ms
2024-01-31T15:51:55.712+02:00  INFO 2729 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 20: collection average finish time 202.65 ms with 1000000 elements at an average per item of 2.0265E-4 ms
2024-01-31T15:52:16.687+02:00  INFO 2729 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 10: collection average finish time 2096.8 ms with 10000000 elements at an average per item of 2.0968E-4 ms
```

Running it a third time:

```
alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar array

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:52:24.946+02:00  INFO 2764 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2764 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:52:24.948+02:00  INFO 2764 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:52:25.397+02:00  INFO 2764 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.703 seconds (process running for 1.054)
2024-01-31T15:52:25.999+02:00  INFO 2764 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 100000: collection average finish time 5.05792 μs with 10 elements at an average per item of 0.505792 μs
2024-01-31T15:52:28.515+02:00  INFO 2764 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 50000: collection average finish time 49.71842 μs with 100 elements at an average per item of 0.4971842 μs
2024-01-31T15:52:31.034+02:00  INFO 2764 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 5000: collection average finish time 502.9662 μs with 1000 elements at an average per item of 0.5029662 μs
2024-01-31T15:52:31.285+02:00  INFO 2764 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 50: collection average finish time 5022.76 μs with 10000 elements at an average per item of 0.5022760000000001 μs
2024-01-31T15:52:32.305+02:00  INFO 2764 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 20: collection average finish time 50.55 ms with 100000 elements at an average per item of 5.055E-4 ms
2024-01-31T15:52:43.516+02:00  INFO 2764 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 20: collection average finish time 560.0 ms with 1000000 elements at an average per item of 5.6E-4 ms
2024-01-31T15:53:37.000+02:00  INFO 2764 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Element[] runs of 10: collection average finish time 5348.1 ms with 10000000 elements at an average per item of 5.3481E-4 ms

alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar  list

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:54:09.321+02:00  INFO 2804 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2804 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:54:09.323+02:00  INFO 2804 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:54:09.777+02:00  INFO 2804 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.707 seconds (process running for 1.091)
2024-01-31T15:54:10.399+02:00  INFO 2804 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 100000: collection average finish time 5.3506 μs with 10 elements at an average per item of 0.53506 μs
2024-01-31T15:54:12.887+02:00  INFO 2804 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 50000: collection average finish time 49.1639 μs with 100 elements at an average per item of 0.491639 μs
2024-01-31T15:54:15.369+02:00  INFO 2804 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 5000: collection average finish time 495.7138 μs with 1000 elements at an average per item of 0.4957138 μs
2024-01-31T15:54:15.620+02:00  INFO 2804 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 50: collection average finish time 5000.86 μs with 10000 elements at an average per item of 0.5000859999999999 μs
2024-01-31T15:54:16.607+02:00  INFO 2804 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 20: collection average finish time 48.85 ms with 100000 elements at an average per item of 4.885E-4 ms
2024-01-31T15:54:27.737+02:00  INFO 2804 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 20: collection average finish time 556.05 ms with 1000000 elements at an average per item of 5.5605E-4 ms
2024-01-31T15:55:20.152+02:00  INFO 2804 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ArrayList<Element> runs of 10: collection average finish time 5240.9 ms with 10000000 elements at an average per item of 5.2409E-4 ms

alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar  stream

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:55:28.921+02:00  INFO 2835 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2835 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:55:28.923+02:00  INFO 2835 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:55:29.377+02:00  INFO 2835 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.706 seconds (process running for 1.049)
2024-01-31T15:55:30.022+02:00  INFO 2835 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 100000: collection average finish time 5.53049 μs with 10 elements at an average per item of 0.553049 μs
2024-01-31T15:55:32.527+02:00  INFO 2835 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 50000: collection average finish time 49.51056 μs with 100 elements at an average per item of 0.4951056 μs
2024-01-31T15:55:34.978+02:00  INFO 2835 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 5000: collection average finish time 489.639 μs with 1000 elements at an average per item of 0.489639 μs
2024-01-31T15:55:35.227+02:00  INFO 2835 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 50: collection average finish time 4958.62 μs with 10000 elements at an average per item of 0.49586199999999997 μs
2024-01-31T15:55:36.211+02:00  INFO 2835 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 20: collection average finish time 48.6 ms with 100000 elements at an average per item of 4.86E-4 ms
2024-01-31T15:55:47.269+02:00  INFO 2835 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 20: collection average finish time 552.3 ms with 1000000 elements at an average per item of 5.522999999999999E-4 ms
2024-01-31T15:56:40.287+02:00  INFO 2835 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : Stream<Element> runs of 10: collection average finish time 5301.3 ms with 10000000 elements at an average per item of 5.3013E-4 ms

alpine-linux:~/java-iteration-performance$ java -jar /home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar parallel

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2024-01-31T15:56:56.949+02:00  INFO 2868 --- [           main] i.g.c.iteration.performance.Application  : Starting Application v0.0.1-SNAPSHOT using Java 17.0.10 with PID 2868 (/home/coen/java-iteration-performance/build/libs/iteration-performance-0.0.1-SNAPSHOT.jar started by coen in /home/coen/java-iteration-performance)
2024-01-31T15:56:56.951+02:00  INFO 2868 --- [           main] i.g.c.iteration.performance.Application  : No active profile set, falling back to 1 default profile: "default"
2024-01-31T15:56:57.419+02:00  INFO 2868 --- [           main] i.g.c.iteration.performance.Application  : Started Application in 0.723 seconds (process running for 1.076)
2024-01-31T15:56:59.276+02:00  INFO 2868 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 100000: collection average finish time 17.74439 μs with 10 elements at an average per item of 1.7744389999999999 μs
2024-01-31T15:57:01.561+02:00  INFO 2868 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 50000: collection average finish time 45.09636 μs with 100 elements at an average per item of 0.45096359999999996 μs
2024-01-31T15:57:02.571+02:00  INFO 2868 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 5000: collection average finish time 201.2644 μs with 1000 elements at an average per item of 0.20126439999999998 μs
2024-01-31T15:57:02.653+02:00  INFO 2868 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 50: collection average finish time 1637.2 μs with 10000 elements at an average per item of 0.16372 μs
2024-01-31T15:57:02.980+02:00  INFO 2868 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 20: collection average finish time 15.75 ms with 100000 elements at an average per item of 1.575E-4 ms
2024-01-31T15:57:06.972+02:00  INFO 2868 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 20: collection average finish time 199.1 ms with 1000000 elements at an average per item of 1.991E-4 ms
2024-01-31T15:57:27.586+02:00  INFO 2868 --- [           main] i.g.c.i.p.r.IterationPerformanceRunner   : ParallelStream<Element> runs of 10: collection average finish time 2061.0 ms with 10000000 elements at an average per item of 2.061E-4 ms

alpine-linux:~/java-iteration-performance$ 
```
