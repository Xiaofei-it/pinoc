# Statements

This chapter covers the statements of the Zlang programming language.

## Variables

### No declaration or definition

In Zlang, you do not need to declare or define a variable before accessing it.

### Scope of a variable

A Zlang program consists of functions, i.e., it is a series of functions.

A variable exists and only exists in a particular function,
and can be accessed within the function. It cannot be accessed outside the function.

The scope of a variable starts from where it is assigned for the first time
and ends at the end of the function.

See the following:

```
function f1() {
  a = 1;
  if (a == 1) {
    c = 3;
  }
  /* c is still accessible. */
  println(c);
}
```

See an ***incorrect*** example:

```
function f2() {
  a = 1;
}

function f3() {
  b = a;
}
```

Calling `f3()` will cause an exception at runtime, because `a` is not assigned.

See another ***incorrect*** example:

```
function f4() {
  return 1;
}

c = f4();
```

This will cause an exception at compile time,
because a statement, which tries to assign a value to `c`, appears outside a function.

### Type of a variable

The type of a variable is dynamical, depending on the value you assign to it.

If a particular variable is assigned values of different types,
the type of the variable changes according to the type of the value it is assigned.

See the following example;

```
/* a is a double. */
a = 3.4;

/* a is an integer. */
a = 5;

/* a is a string. */
a = "abc";

/* a is a map. */
a = _new_map();
```

## Control structures

### Conditional structures (if/else)

Zlang supports the usual `if-else`:

```
b = true;
if (b) println("This is an example.");
```

See a more complex example:

```
function ackermann(m, n) {
  if (m == 0) {
    return n + 1;
  } else if (m > 0 && n == 0) {
    return ackermann(m - 1, 1);
  } else {
    return ackermann(m - 1, ackermann(m, n - 1));
  }
}
```

### Looping structures

#### `for` loop

The `for` loop of Zlang is similar to BASIC:

```
for variable = expression_a to expression_b step expression_c
  statement
```

It will be executed as following:

1. `expression_a` is evaluated and assigned to `variable`;

2. `statment` is executed;

3. `expression_c` is evaluated and the result is *v1*;

4. Calculate the sum of *v1* and the current value of `variable` and the result is *v2*;

5. *v2* is assigned to `variable';
 
6. `expression_b` is evaluated and the result is *v3*;

7. If *v2* &le; *v3*, then go to **2**; otherwise the loop is finished.

See an example which calculates the sum of the numbers from 1 to 100:

```
function sum() {
  s = 0;
  for i = 1 to 100 step 1 {
    s = s + i;
  }
  return s;
}
```

#### `while` loop

The `while` loop is similar to Java:

The following is another example which also calculates the sum of the numbers from 1 to 100:

```
function sum() {
  s = 0;
  i = 1;
  while (i <= 100) {
    s = s + i;
    i = i + 1;
  }
  return s;
}
```

#### `break` and `continue`

Zlang also supports the `break` and `continue` statements.

We writes an example about `break`, which also calculates the sum of the numbers from 1 to 100:

```
function sum() {
  s = 0;
  i = 1;
  while (true) {
    s = s + i;
    i = i + 1;
    if (i == 101) break;
  }
  return s;
}
```

Now the following is an example about `continue`, which calculates the sum of the numbers from 1 to 100,
except 40:

```
function sum() {
  s = 0;
  for i = 1 to 100 step 1 {
    if (i == 40) continue;
    s = s + i;
  }
  return s;
}
```

### `return`

The `return` statement is similar to Java.

Notice that, in Zlang, a function may have a return value, but it may also not have a return value.

Let's see the following function:

```
function foo(a) {
  if (a < 0) {
    println(a);
  } else {
    return a * 2;
  }
}
```

Now `foo(-3)` prints `-3`. And `println(foo(5))` prints `10`.
But `println(foo(-3))` causes an exception at runtime, because `foo(-3)` does not have a return value.