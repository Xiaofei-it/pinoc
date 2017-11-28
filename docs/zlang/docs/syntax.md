# Syntax

This chapter covers the syntax of the Zlang programming language.

## Comments

A comment starts with `/*` and can be found at any position in the line.
The characters following `/*` will be considered part of the comment, including new line characters,
up to the first `*/` closing the comment.

## Identifiers

An identifier starts with a letter or an underscore, followed by a sequence of letters, underscores or digits.
However, it cannot start with a digit.

Thus, `_fun`, `fun` and `fun1` are legal identifiers, but `1fun` is not.

## Numbers

Zlang supports all kinds of numbers which Java supports.
However, you can only write `int` and `double` in Zlang.
If you want to write a `long`, a `float`, a `BigInteger`, etc., you should do the following:

```
aLong = _new_instance("java.lang.Long", "18L");
aFloat = _new_instance("java.lang.Float", "0.5f");
aBigInteger = _new_instance("java.math.BigInteger", "890");
```

## Characters

A Zlang character is a single character surrounded by single quotes, such as `'a'`, `'1'`, `'@'`, etc.

You can escape special characters, such as `\`, `'` and `"` with the the backslash character `\`.

At runtime, a Zlang character is regarded as a Java character,
and thus can be passed to a Java function as its `char` or `java.lang.Character` parameter.

## Strings

A Zlang string is a series of characters surrounded by double quotes.

At runtime, a Zlang string is regarded as a Java string,
and thus can be passed to a Java function as its `java.lang.String` parameter.

## Booleans

A boolean is a special data type that is used to represent truth values: `true` and `false`.

`true` and `false` are the only two primitive boolean values.
More complex boolean expressions can be represented using logical operators.


## Arrays

A Zlang array is regarded as a Java array.

To create a Zlang array:

```
/* One-dimensional array */
intArray = _new_array("int", 3);

/* Two-dimensional array */
integerArray = _new_array("java.lang.Integer", 3, 4);

/* Three-dimensional array, and you can also create an array which has as many dimensions as you want. */
doubleArray = _new_array("double", 3, 4, 3);

/* Also, you can create an array and initialize it at the same time. */
stringArray = _array_of("This", "is", "an", "example", ".");
```

To access an element:

```
intArray[0] = 3;
intArray[1] = intArray[0] + 5;
integerArray[2][3] = 4;
```

To get the length of an array:

```
length= _length(intArray);
```

## Operators

### Arithmetic operators

Zlang supports the usual familiar arithmetic operators.

#### Binary arithmetic operators

| Operator | Purpose |
| :------: | :------:|
|  `+`       | Addition|
|  `-`       | Subtraction|
|  `*`       | Multiplication|
|  `/`       | Division|

#### Unary arithmetic operators

The `+` and `-` operators are also available as unary operators.

```
a = -1;
b = +1;
```

### Relational operators

Relational operators allow comparisons between objects, to know if two objects are the same or different,
or if one is greater than, less than, or equal to the other.

The following operators are available:

| Operator | Purpose |
| :------: | :------:|
|  `==`      | Equal    |
|  `!=`      | Not equal|
|  `<`    | Less than|
|  `<=`   | Less than or equal|
|  `>`    | Greater than|
|  `>=`   | Greater than or equal|

### Logical operators

Zlang provides three logical operators for boolean expressions:

| Operator | Purpose |
| :------: | :------:|
|  `&&`      | Logical conjunction|
|  &#124;&#124;      | Logical disjunction|
|  `!`       | Logical negation|

#### Short-circuiting

The logical conjunction operator (`&&`) supports short-circuiting:
If the left operand is false, then the right operand will not be evaluated and the result will be false.

The following tests whether the string is "abc":

```
function isAbc(string) {
  return string != null && _equal(string, "abc");
}
```

In the above example, `_equal(string, "abc")` can be regarded as `string.equals("abc")` in Java.
Thus if `string` is `null`, an `NullPointerException` will be thrown when it is evaluated.
Thanks to the short-circuiting, however, it will not be evaluated at runtime.

Moreover, the logical disjunction operator (`||`) also supports short-circuiting:
If the left operand is true, then the right operand will not be evaluated and the result will be true.

### Subscript operator

The subscript operator is used when accessing an element of an array:

```
intArray = _new_array("int", 4);
intArray[0] = 0;
for i = 1 to 3 step 1 {
  intArray[i] = intArray[i - 1] + 1;
}
```

### Function call operator

The function call operator `()` is used to call a function. You should put the parameters in the parentheses.

### Operator precedence

The table below lists all Zlang operators in order of precedence:

| Level    | Operator(s) | Name(s) |
| :------: | :------:    |:------: |
|  1       | `[]` `()` `!` | Subscript operator, function call operator/parentheses, logical negation|
|  2       | `*` `/` | Multiplication, division|
|  3       | `+` `-` | Addition/unary plus, subtraction/unary minus|
|  4       | `==`  `!=` `<` `<=` `>` `>=` |  Relational operators|
|  5       | `&&` |  Logical conjunction|
|  6       | &#124;&#124; |  Logical disjunction|