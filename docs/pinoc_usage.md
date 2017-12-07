# Usage of Pinoc

This chapter covers the usage of the Pinoc library.

## Configuration

Pinoc needs a configuration to specify the relacement or the modification of methods.
The configuration is a Json string, which is always downloaded from a server.

Therefore, remember to download a configuration from your server.
Once the configuration is downloaded, configure Pinoc with the following:

```
Pinoc.config("...");
```

The parameter passed to `Pinoc.config` is a Json string, which is in the following format:

```
{targets:[
    {class: "...", method_name: "...", method_sig: "...", library: int, thread_mode: int},
    {class: "...", method_name: "...", method_sig: "...", library: int, thread_mode: int},
    {class: "...", method_name: "...", method_sig: "...", library: int, thread_mode: int}
],
libraries:[
"function main(className, methodName, methodSignature, this, parameters) \{\
    ...
\}",
"function main(className, methodName, methodSignature, this, parameters) \{\
    ...
\}"
]}
```

### Targets

The array mapped by `targets` above is an array of Json objects, each of which specifies a method
to modify and its modification, in that the explicit key-value pairs of each Json object consist of:

1. the name of the declaring class of this method,

2. the name and the signature of this method,

3. the index of a Zlang library containing some instructions to execute inside this method, and

4. a thread mode which specifies these instructions' threading at its runtime.

### Libraries

The array mapped by `libraries` is an array of strings, each of which is a Zlang library mentioned above.
Specifically, the Zlang library contains several Zlang functions, one of which
is `main(className, methodName, methodSignature, this, parameters)`,
which is the entrance of the instuctions to execute.
The parameters of the `main` function are:

| Parameter | Meaning |
| :------: | :------:|
|  className | a string indicating the name of the class|
|  methodName | a string indicating the name of the method|
|  methodSignature| a string indicating the signature of the method|
|  thiz      | an object on which the method is invoked. It is `null` if the method is static.|
| parameters| an array of the parameters passed to the method being invoked|

If the `main` function has a return value, then the original method is replaced and the reture value
is regarded as the return value of the original method.

If the `main` function does not have a return value, then the remaining instructions of the original
method are still executed.

Note that if the original method does not have a return value but you have to replace it, then
you should still make your `main` function have a return value, which, however, will be discarded after
the execution of the `main` function.
Otherwise, Pinoc will regard the `main` function as a modification rather than a replacement,
and the original method will still be executed.

### Thread mode

The value mapped by `thread_mode` is an explicit integer to indicate run-time threading.
Pinoc now supports three type of thread modes:

1. Current thread (thread_mode = 0, which is the default value);

2. The UI thread of an Android app (thread_mode = 1);

3. A new thread from a cached thread pool (thread_mode = 2).

If the specified method should be **replaced**
(in which case the function to replace the original method has a return value),
then the replacement will succeed only in either of the following circumstances:

1. The `thread_mode` is defined as 0 (which means the function to replace the original method still runs on the current thread);

2. The `thread_mode` is defined as 1 (which means the function to replace the original method is expected to run on the UI thread),
and this original method is running exactly on the UI thread.

Except for the above two cases, the instructions within the original method will still be executed,
while the function to replace the original method is executed on the specified thread.

### An example

The following is an example:

```
{targets:[
    {class: "com/iqiyi/trojantest/MainActivity", method_name: "onPause", method_sig: "()V", library: 1},
    {class: "com/iqiyi/trojantest/MainActivity", method_name: "onResume", method_sig: "()V", library: 0},
    {class: "com/iqiyi/trojantest/MainActivity", method_name: "g1", method_sig: "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/Integer;IDLcom/iqiyi/trojantest/MainActivity;Ljava/lang/Boolean;)V", library: 0}
],
libraries:[
"function main(className, methodName, methodSignature, this, parameters) \{\
   _println(\"library 0: \" + className + \" \" + methodName + \" \" + methodSignature + \" \" + this);\
   len = _length(parameters);\
   for i = 0 to len - 1 step 1 \{\
      _println(\"library 0 p:\" + parameters[i]);\
   \}\
   _println(\"library 0 test_internal:\" + test_internal(\"Hehe\"));\
\}",
"function main(className, methodName, methodSignature, this, parameters) \{\
   _println(\"library 1: \" + className + \" \" + methodName + \" \" + methodSignature + \" \" + this);\
   len = _length(parameters);\
   for i = 0 to len - 1 step 1 \{\
      _println(\"library 1 p:\" + parameters[i]);\
   \}\
   _println(\"library 1 test_java:\" + test_java(\"Hehe\"));\
\}"
]}
```

### Configuration dispatch

Note that when you are using Pinoc and the app is being built,
Pinoc does ***not*** know the configuration at all!
Even after the app is released, Pinoc does not know the configuration yet.

The app is responsible for downloading the configuration from a server
and configuring Pinoc with the downloaded configuration.
Only after that will Pinoc know the configuration and know which methods
should be replaced or modified.

## Add the Zlang dependency

Where you configure Pinoc with `Pinoc.config`,
you can add the Zlang dependency, which is a Zlang library containing Zlang functions,
which can be called from the functions of each of the libraries in the specified configuration.

Pinoc will add the dependency to each of the libraries, whose instructions are located
in the Json string which will be downloaded from a server in the future, when this library is built.

Therefore, you can put all of the fundamental functions into the Zlang dependency.

Add the Zlang dependency by the following:

```
Pinoc.addDependency("...");
```

## Add the Java dependency

Also, where you configure Pinuo with `Pinuo.config`,
you can add the Java dependency, which is a Java library containing Java functions,
which can be called from the statements within the Zlang functions of each of the libraries
in the specified configuration.

Pinoc will add the dependency to each of the libraries, whose instructions are located
in the Json string which will be downloaded from a server in the future, when this library is built.

Therefore, a good practice is to put into the Java dependency
all of the fundamental functions which interact with Java,
such as network facilities and I/O facilities.

Add the Java dependency by the following:

```
Pinoc.addJavaDependency(javaLibrary);
```

For the information about how to build a `JavaLibrary`, please refer to the demo or the `app` module
of this project.

## Demo

Refer to [Demo](pinoc_demo.md) to read a demo.

You may refer to the source code in the `app` module of this project for more details.
