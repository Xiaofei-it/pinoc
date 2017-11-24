# Trojan

Trojan is a novel library for dynamical classloader-free modification of an Android app.

The current version of the Trojan library supports:
 
1. the code injection at the entrance to a Java method,

2. the code replacement of a whole Java method,

3. the addition of a new Java method.

## Principle

### Code injection at compile time

When building an Android app, the Trojan plugin modifies all of, or only the
specified, Java methods by injecting a particular code snippet at the entrance
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

### Modification at runtime

At runtime, if the method should be modified, you should make Trojan read a configuration
which specifies the modification.
Then when the method is being invoked, `Trojan.onEnterMethod()` will execute the
corresponding instructions according to the configuration.

If the method should not be modified, `Trojan.onEnterMethod()` will simply return `Library.NO_RETURN_VALUE`
and the instructions within the original method will be executed.

### Instructions written in Zlang

The general techniques always use a Java classloader to load a class and execute the corresponding
instructions with the class. However, the classloader causes the security problems and thus is not
allowed by some app stores.

// TODO
## Usage

```
compile 'com.iqiyi.ishow.support:trojan:0.0.67'
```

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