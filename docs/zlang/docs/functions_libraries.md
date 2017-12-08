# Functions and Libraries

This chapter covers the functions and libraries of the Zlang programming language.

## Definitions

A *Zlang function* is a function written in Zlang, containing zero or more Zlang statements.
A statement within a Zlang function can call Zlang functions (including the Zlang function itself),
and a Zlang function can also be called by a statement within Zlang functions (including the Zlang function itself).

A *Java function* is a function provided by a Java class implementing the `JavaFunction` interface.
It can can be called by a statement within Zlang functions.

Note that the Java function here is different from the usual Java method.
Mentioned later will be how to write a Java function.

Also note that a statement within a Zlang function can call a Zlang function or a Java function,
but a statement within a Java function can call *neither* any Zlang functions *nor* any Java functions.
Under some circumstances, the Java function can call some functions,
which, however, is not recommended.

A *Java library* is a library consisting of Java functions.
A Java library is used to build a Zlang library (which will be defined later) at Java runtime,
and it provides the Zlang functions of the Zlang library
with the Java functions of the Java library to call.

To build a Java library at Java runtime, create an instance of the `JavaLibrary.Builder` class,
use the corresponding methods provided by the `JavaLibrary.Builder` class to add the Java functions,
and finally invoke the `build()` method to obtain an instance of a Java library.

A *Zlang library* is a library consisting of Zlang functions, other Zlang libraries and/or Java libraries.
To build a Zlang library at Java runtime, create an instance of the `Library.Builder` class,
use the corresponding methods provided by the `Library.Builder` class to add the Zlang functions
and other Zlang/Java libraries,
and finally invoke the `build()` method to obtain an instance of a Zlang library.

In a Zlang library *Z*, a statement within Zlang function can call any Zlang function of *Z*,
any Java function of any Java library of *Z*, and any Zlang/Java function of any Zlang library of *Z*.

## Structure of a Zlang function

The following is the structure of a Zlang function:

```
function function_name(parameter_list) {
  statement
}
```

`function_name` is a identifier, which starts with a letter or an underscore,
followed by a sequence of letters, underscores or digits.
However, it cannot start with a digit.

`parameter_list` is a comma-separated list of identifiers, each of which is a parameter of the function.
You do not need to declare the types of parameters.

The following is some examples:

```
function pi() {
  return 3.14;
}

function min(a, b) {
  if (a < b) {
    return a;
  } else {
    return b;
  }
}
```

## Function overloading

Zlang supports function overloading.

The following is two examples which find the maximum number among several numbers:

```
function max(a, b) {
  if (a > b) {
    return a;
  } else {
    return b;
  }
}

function max(a, b, c) {
  return max(a, max(b,c));
}
```

## Interactions between Zlang functions

A statement with a Zlang function can call the Zlang function itself, any previous Zlang functions, or any latter Zlang functions:

See an example:

```
function max(a, b, c) {
  return max(a, max(b,c));
}

function max(a, b) {
  if (a > b) {
    return a;
  } else {
    return b;
  }
}
```

See another example:
```
function fib(n) {
  if (n == 0 || n == 1) {
    return 1;
  } else {
    return fib(n - 1) + fib(n - 2);
  }
}
```

## Call a Java function

Suppose on an Android device, a statement within a Zlang function want to print an Android log.

Since Zlang does not provide any function for printing an Android log, we need to write a custom Java function.

First let's write a Java function in Java:

```
public class Log1 implements JavaFunction {
  @Override
  public boolean isVarArgs() {
    return false;
  }
  
  @Override
  public int getParameterNumber() {
    return 1;
  }
  
  @Override
  public String getFunctionName() {
    return "log1";
  }
  
  @Override
  public Object call(Object[] input) {
    return android.util.Log.d("ERIC_ZHAO", (String) input[0]);
  }
}
```

Then we can use the Java function `log1`:

```
function test1(a) {
  if (a < 0) {
    log1("a < 0");
  }
  /* Do something else. */
}
```

You may also write a Java function which takes a variable number of parameters:

```
public class Log2 implements JavaFunction {
  @Override
  public boolean isVarArgs() {
    return true;
  }
  
  @Override
  public int getParameterNumber() {
    // Return the minimum number of parameters.
    return 1;
  }
  
  @Override
  public String getFunctionName() {
    return "log2";
  }
  
  @Override
  public Object call(Object[] input) {
    if (input.length == 1) {
      return android.util.Log.d("ERIC_ZHAO", (String) input[0]);
    } else {
      return android.util.Log.d((String) input[0], (String) input[1]);
    }
  }
}
```

Then we can use the Java function `log2`:

```
function test2(a) {
  if (a < 0) {
    log2("a < 0");
  } else {
    log2("EricZhao", "a >= 0");
  }
  /* Do something else. */
}
```

The name of the custom Java functions should start with letters.
Any Java functions whose name starts with the underscore is regarded as the internal Java functions,
provided by Zlang. See [InternalJavaFunction](internal_java_functions.md) for the internal Java functions
provided by Zlang.

## Build a Java library at Java runtime

To build a Java library which contains the above `log1` and `log2` functions:

```
JavaLibrary javaLibrary = new JavaLibrary.Builder()
                             .addJavaFunction(new Log1())
                             .addJavaFunction(new Log2())
                             .build();
```

## Build a Zlang library at Java runtime

To build a Zlang library which contains the above `test1` and `test2` functions:

```
Library library = new Library.Builder()
                    .addJavaDependency(javaLibrary)
                    .addFunctions(
                         "function test1(a) {\n"
                       + "  if (a < 0) {\n"
                       + "    log1("a < 0");\n"
                       + "  }\n"
                       + "}\n"
                       + "function test2(a) {\n"
                       + "  if (a < 0) {\n"
                       + "    log2("a < 0");\n"
                       + "  } else {\n"
                       + :    log2("EricZhao", "a >= 0");\n"
                       + "  }\n"
                       + "}")
                    .build();
```

## Call a Zlang function at Java runtime

To call a Zlang function:

```
library.execute("test1", new Object[]{3});
library.execute("test2", new Object[]{3});
```

Note that if `execute` calls a Zlang function which has a return value, `execute` will also return
such return value as its own return value.