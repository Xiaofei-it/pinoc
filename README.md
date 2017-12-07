# Pinoc

Pinoc is a novel library for dynamic classloader-free modification of an Android app.

The current version of the Pinoc library supports:
 
1. the code injection at the entrance to a Java method,

2. the code replacement of a whole Java method,

3. the addition of a new Java method.

## Features

1. Provides a novel technique for hotfix deployment without the need of a Java classloader.

2. High compatibility. Pinoc can run at all JVM-based platforms.

3. Real-time effect. Pinoc will replace or modify the methods immediately
once it has read the specified configuration.

4. Provides a novel technique for dynamic event tracking.

## Principle

<img src="docs/pics/pinoc_structure.png" width="1200" height="1000"/>

When an Android app is built, the Pinoc plugin replaces each Java method in your app with
its variant. Specifically, after the replacement each method (the original method) is replaced with its variant.

At runtime, when a method is invoked, it is the variant of the original method that is actually invoked.
The variant passes the information about the invocation to Pinoc,
which decides whether to replace or modify the original method.

To avoid the trouble caused by the Java classloader, Pinoc does not adopt the Java classloader.
Thus the replacement or the modification of the original method is not written in Java.

Instead it is written in Zlang, a flexible dynamically-typed programming language running on the
JVM and supporting access to Java objects and interaction with Java at runtime.
It is easy to convert a Java method or statement into a Zlang function or statement.

If Pinoc decides to replace or modify the original method, it compiles the instructions of
the replacement or the modification by the Zlang compiler, after which the output of the compilation
is passed to the Zlang executor for execution.

See [Principle of Pinoc](docs/pinoc_principle.md) for more information.

## Application Scenarios

<img src="docs/pics/pinoc_application.png" width="800" height="500"/>

Through this Library, we can not only implement the dynamic repair of the code, but also make the relevant data collection and delivery through the injection of the code.

By the way of code injection, PM can drive the collection and collection of application in a real sense.

## Comparision with other techniques

Since Pinoc can replace or modify Java methods, we may adopt it for hotfix deployment or dynamic event tracking.

### Hotfix

Compared with the other techniques for hotfix, Pinoc has the following advantages:

1. Classloader-free. The instructions within the hotfix are written in Zlang instead of Java.
The Java classloader is thus not needed.
Therefore, your app will pass the security check at some app stores which forbid developers from
using a Java classloader for hotfix deployment.
In contrast, the other techniques have to use a Java classloader to load the instructions written in Java.

2. High compatibility. The Pinoc library has higher compatibility than the other techniques,
because the Zlang compiler and executor run on the usual JVM,
whereas the other techniques may perform some operations under the JVM,
which may not work on some devices.

3. Real-time effects. The Pinoc library will replace or modify the methods immediately
once it has read the specified configuration, whereas the replacement or
the modification provided by other techniques always comes into effect the next time
your app is launched.

### Dynamic event tracking

As an app developer, you always have to track events,
which are user interactions with content that can be tracked, such as click events and touch events.

You often track events by adding the tracking instructions at the entrance of the corresponding methods.
For example, you may need to perform tracking for the `onClick` method of a particular
`View`, or the `onCreate` method of a particular `Activity`.
However, these tracking instructions are static and thus unchangeable after the app is built
and released. Consequently there are two disadvantages:

1. The methods to be tracked should be specified at compile time. After the app is
built and released, you are unable to track another method which is not specified at compile time.
 
2. The tracking instructions should be specified at compile time. After the app is
built and released, you are unable to modify them.

Therefore, event tracking should be dynamic rather than static, and developers have to perform
dynamic event tracking.

Nowadays, there are not so many techniques for dynamic event tracking.
Even worse, almost all of the bare current techniques
can only track some methods of `View`s or `Activity`s. Compared with them, Pinoc
has the following advantages:

1. Any method of any class can be tracked. On contrast, current techniques can only track
some particular methods of some particular classes.

2. The tracking instructions can be specified dynamically and may perform almost
all the operations allowed by Android. On contrast, the instructions allowed by
current techniques are limited.

## Demo

A demo is provided, which you may refer to. See [Demo](docs/pinoc_demo.md) for the details.

## Deployment

To use the Pinoc library, add the following in your `build.gradle`:

```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.iqiyi:pinoc-plugin:0.2.1' 
    }
}
apply plugin: "pinoc"
```

Also, you may disable Pinoc temporarily by adding the following
in `gradle.properties` of your project or modules:

```
pinoc-plugin.enabled=false // default is true
```

To learn Zlang, please refer to [Zlang](docs/zlang/zlang.md).

To learn Pinoc, please refer to [Usage of Pinoc](docs/pinoc_usage.md).

To learn a demo, please refer to [Demo of Pinoc](docs/pinoc_demo.md).

## License

Copyright (C) 2017 iQIYI.com

The binaries and source code of the Pinoc library and the Pinoc plugin can be used according to the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).