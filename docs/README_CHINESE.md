# Pinoc

***注意*** 此版本只是中文翻译版，不一定会实时更新。如有能力，请阅读英文原版。

Pinoc是一种Android新型无需使用类加载器的动态代码修改方案。

Pinoc可以在Java方法入口处进行代码注入，对整个方法进行替换，也可以新增Java方法。

## 特性

1. 提供一种无需ClassLoader进行类加载进行热修复方案。

2. 提供一种新型的动态事件跟踪方案。

3. 兼容性强，能在任何基于Java虚拟机的平台上运行。

4. 实时生效，一旦读取配置文件便可以立即替换或修改对应方法。

## 设计原理

<img src="pics/pinoc_structure.png" width="1046" height="577"/>

在app构建过程中，Pinoc插件将app中的每个Java方法替换成它的变种方法。每个方法（原始方法）都被替换为它的变种方法。

在app运行时，当一个方法被调用，实际上是调用了原始方法的变种方法。变种方法负责调用原始方法，但在这之前，变种方法首先将原始方法的调用信息传给Pinoc，Pinoc根据配置文件决定是否替换或修改原始方法，这个配置文件可能是从服务器下发的。

为避免Java类加载器产生的一些麻烦，Pinoc不采用Java的类加载器来执行方法的替换体或者修改体。所以方法的替换体或者修改体不是用Java语言编写的。它们是用Zlang编写的，Zlang是一种运行在Java虚拟机的动态灵活的编程语言，支持Java对象的调用，在运行时可以与Java环境交互。开发者可以轻易地将Java代码转化为Zlang代码。

所以在运行时，如果Pinoc决定修改或替换某个方法，它将方法的替代体或者修改体用Zlang编译器编译成Zlang字节码，传入Zlang执行器执行。这样替换体或者修改体就被执行了，原方法也被替换或修改了。

查看Pinoc的更多信息：[Pinoc 设计原理](pinoc_principle.md)

## 性能

Pinoc在内存和CPU消耗方面具有极佳的表现，其对运行的APP不会造成任何性能影响。

对于方法执行时间，尽管Pinoc代码段直接插桩到每个方法的入口，但是大部分的代码段只检索一个` ConcurrentHashMap `并立即返回，所以时间开销是非常低的。

为了对比，我们测量了几个方法的执行时间和对应的变种方法的执行时间。我们主要收集执行某个特定方法100000次所花费的时间，并以此以获得执行这100000次方法的平均耗时。

结果如下表所示：

|  A method | 执行原方法(纳秒) | 执行Pinoc变种(纳秒)| Pinoc耗时(纳秒)|
| :------: | :------:| :------:| :------:|
|  Empty method | 46.46094| 737.08698 | 690.62604 |
|  Complex method | 36771.06509| 37713.69061 | 942.62552 |
| More complex method |74522.70934 | 75466.49896| 943.78962 |

如上表所述，第二列表示第一列方法运行耗时，第三列表示第一列Pinoc变种方法运行耗时，第四列表示Pinoc的整体耗时。表中数据单位是**纳秒**。另外，执行注入的代码段所花费的时间**小于1微秒**，这是一个非常低的时间开销。

## 技术对比

Pinoc可以进行Java方法替换和修改，故可以将其应用于热修复或者动态事件跟踪。

### 热修复

对比其他热修复方案，Pinoc具有如下优势：

1. 无需类加载器。Pinoc未采用Java的类加载器加载热修复代码。

2. 兼容性强。Pinoc可运行在任何基于Java虚拟机的平台上。

3. 立即生效。一旦读取了指定的配置文件，Pinoc可以立即对代码进行替换和修改。

### 动态事件跟踪

对比现行的技术方案，Pinoc优势如下：

1. 任何类的任何方法都可以被跟踪。

2. 跟踪的指令可以动态配置，而且可以执行Android允许的几乎所有操作。

查看Pinoc的更多信息：[Pinoc 设计原理](pinoc_principle.md)

## 用法和Demo

方法的替换体和修改体使用Zlang编写，Zlang是一种极易学习和使用的语言。

学习Zlang，请参考：[Zlang](https://github.com/Xiaofei-it/Zlang)。保证你可以在**一个小时以内**掌握并使用它。

对于Pinoc的使用，请参考：[Usage of Pinoc](pinoc_usage.md)。

了解完Pinoc的使用方式之后，可参考demo：[Demo of Pinoc](pinoc_demo.md)，学习Pinoc的实践方式。

## 部署

使用Pinoc，请在`build.gradle`中添加如下代码：

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

另外，你可以暂时禁用Pinoc，只需在项目或者模块的的`gradle.properties`中，添加如下配置：

```
pinoc-plugin.enabled=false // default is true
```

## 许可证

Copyright (C) 2017 iQIYI.com

The binaries and source code of the Pinoc library and the Pinoc plugin can be used according to the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).