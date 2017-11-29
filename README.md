# Trojan

Trojan is a novel library for dynamic classloader-free modification of an Android app.

The current version of the Trojan library supports:
 
1. the code injection at the entrance to a Java method,

2. the code replacement of a whole Java method,

3. the addition of a new Java method.

## Principle

// TODO add a picture

When an Android app is built, the Trojan plugin modifies all of the Java methods.
After the modification, each method (the original method) becomes its variant.

At runtime, when a method is invoked, it is the variant of the original one that is actually invoked.
The variant passes the information about the invocation to Trojan,
which decides whether to replace or modify the original method.

To avoid the trouble caused by the Java classloader, Trojan does not adopt the Java classloader.
Thus the replacement or the modification of the original method is not written in Java.
Instead it is written in Zlang, a flexible programming language.
It is easy to convert the Java instructions into the Zlang instructions.

If Trojan decides to replace or modify the original method, it compiles the instructions of
the replacement or the modification by the Zlang compiler, after which the output of the compilation
is passed to the Zlang executor for execution.

See [Principle of Trojan](docs/trojan_principle.md) for more information.

## Comparision with other techniques

Since Trojan can replace or modify Java methods, we can adopt it for hotfix or
dynamic statistical sampling.

### Hotfix

Compared with the other techniques for hotfix, Trojan has the following advantages:

1. Classloader-free. The instructions within the hotfix are written in Zlang instead of Java.
Thus the Java classloader is not needed. In contrast, the other techniques need to use a Java
classloader to load the instructions written in Java.

2. High compatibility. The Trojan library has higher compatibility than the other techniques,
because the Zlang compiler and executor run on the usual JVM,
while the other techniques may perform some operations under the JVM, which may not work on some devices.

### Dynamic statistical sampling 

As an app developer, you often need to perform the statistical sampling for method invocation
by adding sampling instructions at the entrance of the corresponding methods.
For example, you may need to perform sampling for the `onClick` method of a particular
`View`, or the `onCreate` method of a particular `Activity`. 
However, these sampling instructions are static and thus there are two disadvantages:

1. The methods to be sampled should be specified at compile time. After the app is released,
you cannot perform the sampling for another method which is not specified at compile time.
 
2. The sampling instructions should be specified at compile time. After the app is released,
you cannot modify them.

Therefore, the sampling should be dynamic rather than static. Thus developers need to perform
dynamic statistical sampling.

Nowadays, there are not so many techniques for dynamic statistical sampling. Current techniques
can only perform sampling for some methods of `View`s or `Activity`s. Compared with them, Trojan
has the following advantages:

1. Any method of any class can be sampled. On contrast, current techniques can only sample
some particular methods of some particular classes.

2. The sampling instructions can be specified dynamically. On contrast, the instructions allowed by
current techniques are limited.

## Deployment

To use the Trojan library, add the following dependency:

```
compile 'com.iqiyi.ishow.support:trojan:0.0.67'
```

Also, the Trojan plugin is needed at compile time:

```
buildscript {
    repositories {
        jcenter()
        maven {
            url "http://maven.mbd.qiyi.domain/nexus/content/repositories/mbd-show/"
        }
    }
    dependencies {
        classpath 'com.iqiyi.ishow.support:trojan-plugin:0.0.135'
    }
}
```

To learn Zlang, please refer to [Zlang](docs/zlang/zlang.md).

To learn Trojan, please refer to [Usage of Trojan](docs/trojan_usage.md).

## License

Copyright (C) 2017 iQIYI.com

The binaries and source code of the Trojan library and the Trojan plugin can be used according to the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).