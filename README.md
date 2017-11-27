# Android Etalon - DaCapo
Etalon-DaCapo is a port of a set of [DaCapo Java Benchmark ](http://www.dacapobench.org/) to Android devices.
Since some Java libraries are not fully supported on Android and some others packages are completely left out, we ported only four applications:

		1. lusearch
		2. xalan
		3. pmd
		4. hsqldb (sqlite)

hsqldb is modified to use Android sqlite library based on the interface described in
[dmytrodanylyk](https://github.com/dmytrodanylyk/android-concurrent-database).

The port is intended to be generic, and configurable to serve as a tool for Mobile benchmarking by the system developers.

![DaCapo Screenshot](screen_shot.png)

## How this is useful?
Unlike all the other mobile benchmarks, this benchmark suite focuses on high level performance of the runtime (Virtual Machine).
The ability to execute deterministic workload without environmental effect (GPS, WiFI, etc..) allows the system developers to analyse the main system performance.

### Running
From command line execute:

~~~shell
./runapp.sh
~~~

The script takes care of copying the data folder to the external storage of the device as well as installing the apk file.

## Related Publications

* **One Process to Reap Them All: Garbage Collection as-a-Service**. Ahmed Hussein, Mathias Payer, Antony L. Hosking, and Chris Vick. (2017). *In proceedings of the 13th ACM SIGPLAN/SIGOPS International Conference on Virtual Execution Environments (VEE’17). Xi’an, China*. doi:[10.1145/3050748.3050754](https://doi.org/10.1145/3140607.3050754).
* **Impact of GC Design on Power and Performance for Android**. Ahmed Hussein, Mathias Payer, Antony Hosking, and Christopher A. Vick. (2015).  *In ACM International Systems and Storage Conference*. doi:[10.1145/2757667.2757674](https://doi.org/10.1145/2757667.2757674).
* **Don’t Race the Memory Bus: Taming the GC Leadfoot**. Ahmed Hussein, Antony L. Hosking, Mathias Payer, and Christopher A. Vick. (2015).  *In ACM SIGPLAN International Symposium on Memory Management*. doi:[10.1145/2754169.2754182](https://doi.org/10.1145/2887746.2754182)

## License

```
Copyright 2011, 2018 Purdue University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
