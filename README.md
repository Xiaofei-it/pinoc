# Trojan

Trojan is a novel library for dynamic classloader-free modification of an Android app.

The current version of the Trojan library supports:
 
1. the code injection at the entrance to a Java method,

2. the code replacement of a whole Java method,

3. the addition of a new Java method.

## Principle

### Code injection at compile time

When building an Android app, the Trojan plugin modifies all of the
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
        Object var = Trojan.onEnterMethod("com/iqiyi/trojantest/Test", "f", "(II)Ljava/lang/String;", this, new Object[]{a, b});
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

The injected code snippet invokes a static method called `onEneterMethod` of the `Trojan` class.
`onEnterMethod` takes as parameters the name of the class, the name and signature of the method being invoked,
the object on which the method is being invoked, and the parameters of the method being invoked.

Inside `onEnterMethod`, Trojan decides whether to execute some instructions and what to return after
the execution, according to a configuration, which specifies:

1. at the entrances of which methods, what instructions should be injected;

2. which methods should be replaced, and their replacements;

### Modification at runtime

At runtime, when Trojan is initialized, it reads a configuration which specified the modification of the app.

As mentioned above, when a method is being invoked, `Trojan.onEnterMethod` receives the corresponding
parameters. Then Trojan does the following:

1. If the specified instructions should be injected at the entrance of the method,
Trojan executes the injected instructions and returns `Library.NO_RETURN_VALUE`.
Then the remaining instructions within the original method are executed.

2. If the method should be replaced, Trojan executes the instructions of its replacement and returns
the return value of the replacement to the original method. Then the original method returns it directly
and the remaining instructions are not executed. 

3. If the method should neither be modified nor replaced, `Trojan.onEnterMethod()` will simply
return `Library.NO_RETURN_VALUE`. Then the instructions of original method are executed.

### Instructions written in Zlang

The traditional techniques always adopt a Java classloader to load a class and execute the corresponding
instructions within the class. However, the classloader causes the security problems and thus is not
allowed by some app stores.

To avoid the above problems, Trojan does not adopt the tradition classloader-based techniques, but
adopt a novel technique which is classloader-free. Specifically, Trojan executes the instructions
written not in Java, but in the Zlang programming language,
a flexible dynamically-typed programming language which runs on the JVM
and supports access to Java objects and interaction with Java at runtime.
It is easy to convert Java instructions into Zlang instructions.

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

To learn Trojan, please refer to [Trojan](docs/trojan.md).

## License

Copyright (C) 2017 iQIYI.com

The binaries and source code of the Trojan library and the Trojan plugin can be used according to the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).