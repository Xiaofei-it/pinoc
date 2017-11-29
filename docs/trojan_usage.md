# Usage of Trojan

This chapter covers the usage of the Trojan library.

## Configuration

Before using Trojan, you should configure it with a Json string which specifies the modification of
methods.

The Json string is in the following format:

```
{targets:[
    {class: "...", method_name: "...", method_sig: "...", library: int},
    {class: "...", method_name: "...", method_sig: "...", library: int},
    {class: "...", method_name: "...", method_sig: "...", library: int}
],
libraries:[
"function main(className, methodName, methodSignature, target, parameters) \{\
    ...
\}",
"function main(className, methodName, methodSignature, target, parameters) \{\
    ...
\}"
]}
```

The array mapped by "targets" is an array of Json objects,
each of which specifies the method to modify by the name of the class, the name of the method,
the signature of the method and the index of a Zlang library containing the instructions to execute.

The array mapped by "libraries" is an array of strings,
each of which is a Zlang library mentioned above. Specifically, the Zlang library contains several Zlang
functions, one of which is `main(className, methodName, methodSignature, target, parameters)`,
which is the entrance of the instuctions to execute. The parameters of the `main` function are:

| Parameter | Meaning |
| :------: | :------:|
|  className | a string indicating the name of the class|
|  methodName | a string indicating the name of the method|
|  methodSignature| a string indicating the signature of the method|
|  target      | an object on which the method is invoked. It is `null` if the method is static.|
| parameters| an array of the parameters passed to the method being invoked|

If the `main` function has a return value, then the original method is replaced and the reture value
is regarded as the return value of the original method.

If the `main` function does not have a return value, then the remaining instructions of the original
method are still executed.

Note that if the original method does not have a return value but you want to replace it, then
you should still make your `main` function have a return value, which will be discarded after
the execution of the `main` function.

The following is an example:

```
{targets:[
    {class: "com/iqiyi/trojantest/MainActivity", method_name: "onPause", method_sig: "()V", library: 1},
    {class: "com/iqiyi/trojantest/MainActivity", method_name: "onResume", method_sig: "()V", library: 0},
    {class: "com/iqiyi/trojantest/MainActivity", method_name: "g1", method_sig: "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/Integer;IDLcom/iqiyi/trojantest/MainActivity;Ljava/lang/Boolean;)V", library: 0}
],
libraries:[
"function main(className, methodName, methodSignature, target, parameters) \{\
   _println(\"library 0: \" + className + \" \" + methodName + \" \" + methodSignature + \" \" + target);\
   len = _length(parameters);\
   for i = 0 to len - 1 step 1 \{\
      _println(\"library 0 p:\" + parameters[i]);\
   \}\
   _println(\"library 0 test_internal:\" + test_internal(\"Hehe\"));\
\}",
"function main(className, methodName, methodSignature, target, parameters) \{\
   _println(\"library 1: \" + className + \" \" + methodName + \" \" + methodSignature + \" \" + target);\
   len = _length(parameters);\
   for i = 0 to len - 1 step 1 \{\
      _println(\"library 1 p:\" + parameters[i]);\
   \}\
   _println(\"library 1 test_java:\" + test_java(\"Hehe\"));\
\}"
]}
```

## Add the Zlang dependency

You can add the Zlang dependency, which is a Zlang library containing Zlang functions,
which can be called from the functions of each of the libraries in the specified configuration.
Trojan will add the dependency to each of the libraries when it is built.

Therefore, you can put all of the fundamental functions into the Zlang dependency.

Add the Zlang dependency by the following:

```
Trojan.addDependency("...");
```

## Add the Java dependency

You can add the Java dependency, which is a Java library containing Java functions,
which can be called from the functions of each of the libraries in the specified configuration.
Trojan will add the dependency to each of the libraries when it is built.

Therefore, you can put into the Java dependency all of the fundamental functions which interact
with Java, such as network facilities and I/O facilities.

Add the Java dependency by the following:

```
Trojan.addJavaDependency(javaLibrary);
```