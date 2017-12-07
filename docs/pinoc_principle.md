# Principle of Pinoc

This chapter covers the principle of the Pinoc library.

<img src="docs/pics/pinoc_structure.png" width="1200" height="1000"/>

When an Android app is built, the Pinoc plugin replaces each Java method in your app with
its variant. Specifically, after the replacement each method (the original method) is replaced with its variant.

At runtime, when a method is invoked, it is the variant of the original method that is actually invoked.
The variant passes the information about the invocation to Pinoc,
which decides whether to replace or modify the original method,
according to a configuration file, which may be downloaded from a server.

To avoid the trouble caused by the Java classloader, Pinoc does not adopt the Java classloader.
Thus the replacement or the modification of the original method is not written in Java.

Instead it is written in Zlang, a flexible dynamically-typed programming language running on the
JVM and supporting access to Java objects and interaction with Java at runtime.
It is easy to convert a Java method or statement into a Zlang function or statement.

If Pinoc decides to replace or modify the original method, it compiles the instructions of
the replacement or the modification by the Zlang compiler, after which the output of the compilation
is passed to the Zlang executor for execution.

## Code injection at compile time

When building an Android app, the Pinoc plugin modifies all of the
Java methods by injecting a particular code snippet at the entrance
of each method to be modified.

Take the following as an example:

```
package com.iqiyi.trojantest;

public class Test {
    public String f(int a, int b) {
        if (a > b) {
            return "a";
        } else {
            return "b";
        }
    }
}
```

By code injection, the method becomes the following:

```
package com.iqiyi.trojantest;

public class Test {
    public String f(int a, int b) {
        Object var = Pinoc.onEnterMethod("com/iqiyi/trojantest/Test", "f", "(II)Ljava/lang/String;", this, new Object[]{a, b});
        if(var != Library.NO_RETURN_VALUE) {
            if(var == null) {
                return null;
            }
            if(var instanceof String) {
                return (String)var;
            }
        }
        if (a > b) {
            return "a";
        } else {
            return "b";
        }
    }
}
```

The injected code snippet invokes a static method called `onEneterMethod` of the `Pinoc` class.
`onEnterMethod` receives the information about the invocation, including the name of the class,
the name and the signature of the method being invoked, the object on which the method is being invoked,
and the parameters of the method being invoked.

Inside `onEnterMethod`, Pinoc decides whether to execute some instructions and what to return after
the execution, according to a configuration, which specifies:

1. at the entrances of which methods, what instructions should be injected;

2. which methods should be replaced, and their replacements;

## Modification at runtime

At runtime, when Pinoc is initialized, it reads a configuration which specified the modification of the app.

As mentioned above, when a method is being invoked, `Pinoc.onEnterMethod` receives the corresponding
information about the invocation. Then Pinoc does the following:

1. If the specified instructions should be injected at the entrance of the method,
Pinoc executes the injected instructions and returns `Library.NO_RETURN_VALUE`.
Then the remaining instructions within the original method are executed.

2. If the method should be replaced, Pinoc executes the instructions of its replacement and returns
the return value of the replacement to the original method. Then the original method returns it directly
and the remaining instructions are not executed. 

3. If the method should neither be modified nor replaced, `Pinoc.onEnterMethod()` will simply
return `Library.NO_RETURN_VALUE`. Then the instructions of original method are executed.

Note that Pinoc has a high performance in memory and CPU usage, so it will not
affect the memory and CPU usage of your app.

## Instructions written in Zlang

The traditional techniques always adopt a Java classloader to load a class and execute the corresponding
instructions within the class. However, the classloader causes the security problems and thus is not
allowed by some app stores.

To avoid the above problems, Pinoc does not adopt the traditional classloader-based techniques, but
adopt a novel technique which is classloader-free. Specifically, Pinoc executes the instructions
written not in Java, but in the Zlang programming language,
a flexible dynamically-typed programming language which runs on the JVM
and supports access to Java objects and interaction with Java at runtime.
It is easy to convert Java instructions into Zlang instructions.

## Comparision with other techniques

Since Pinoc can replace and modify Java methods, we may adopt it for hotfix deployment and dynamic event tracking.

### Hotfix deployment

Compared with the other techniques for hotfix deployment, Pinoc has the following advantages:

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
