# Zlang

Zlang is a flexible dynamically-typed programming language which runs on the JVM, and supports access
to Java objects and interaction with Java at runtime.

## Features

1. Easy to learn and use.

2. Flexible and dynamically typed.

3. Supports interaction with Java at runtime and thus provides an classloader-free alternative for hotfix.

## Preview

Zlang is a flexible programming language which run on the JVM. It is easy to learn and use.

### Basic usage

The following is an example which prints "Hello World!" on the console:

```
function print_hello_world() {
  println("Hello World!");
}
```

The following is a more complex example which calculates the sum of the integers from `a` to `b`:

```
function sum(a, b) {
  result = 0;
  for i = a to b step 1 {
    result = result + i;
  }
  return result;
}

function print_sum(a, b) {
  println(sum(a, b));
}
```

The following example illustrates how to calculate the factorial of `n` recursively:

```
function factorial(n) {
  if (n == 0) {
    return 1;
  } else {
    return n * factorial(n - 1);
  }
}
```

Another example illustrates how to find the maximum number in a two-dimensional array:

```
function max(arr) {
  len1 = _length(arr);
  /* Get the minimum integer. */
  result = _get_static_field("java.lang.Integer", "MIN_VALUE");
  for i = 0 to len1 - 1 step 1 {
    len2 = _length(arr[i]);
    for j = 0 to len2 - 1 step 1 {
      if (arr[i][j] > result) {
        result = arr[i][j];
      }
    }
  }
  return result;
}
```

### Java object access

Zlang supports interaction with Java at runtime. The following example prints the current time:

```
function print_time() {
  calendar = _invoke_static_method("java.util.Calendar", "getInstance");
  format = _new_instance("java.text.SimpleDateFormat", "yyyy-MM-dd");
  println(_invoke_method(format, "format", _invoke_method(calendar, "getTime")));
}
```

Let's take a look at the corresponding Java statements:
```
  Calendar calendar = Calendar.getInstance();
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  System.out.println(format.format(calendar.getTime()));
```

They are quite similar, aren't they?

Since Zlang supports the access to Java objects, we can also do something interesting.

For instance, we have a Java class named `Foo`:

```
public class Foo {
  private int f;
  public void fun() {
    Library library = new Library.Builder()
                        .addFunctions(ZlangFunctions.getFunction())
                        .build();
    library.execute("dynamical_fun", new Object[]{this});
  }
}
```

Now we can write any code in `ZlangFunctions` to dynamically control the behavior of `fun` at runtime.

We can, for instance, write the following to modify the value of `f` and print it.

```
function dynamical_fun(this) {
  f = _get_field(this, "f");
  _set_field(this, "f", f + 1);
  println("The value changes from " + f + " to " + (f + 1));
}
```

## Using Zlang at Java runtime

### Downloading

Before using Zlang at Java runtime, you should compile the binaries or the source code
of the Zlang interpreter into your project.

0.0.7

### Building a Zlang library

Build a Zlang library, which contains Zlang functions to call.

```
Library library = new Library.Builder()
                    .addFunctions("function f(a) {return a + 1;} function g(a) {return a + 2;}")
                    .addFunctions("function h(a) {return a + 3;}")
                    .build();
```

### Calling a Zlang function

Pass the parameters to call a Zlang function in a library:

```
int a = (int) library.execute("f", new Object[]{3});
System.out.println(a);
```

### Dependencies

When building a Zlang library, you may add another pre-built Zlang library, which contains functions
for the functions in the new library to call.

Also, you may add a Java library, which contains Java functions for the functions in the new Zlang
library to call. A Java library is built similarly to a Zlang library, except that a `Library.Builder`
is replaced with a `JavaLibrary.Builder`.

## Learning more

## License

Copyright (C) 2011-2017 Xiaofei

The binaries and source code of the Zlang Project can be used according to the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).