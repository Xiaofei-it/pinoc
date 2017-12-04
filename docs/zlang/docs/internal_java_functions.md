# Internal Java Functions

This chapter introduces the internal Java functions provided by the Zlang programming language.
Please note that, you must pass the parameter of the correct type to the function.
Otherwise, an error will occur at runtime.

Some Zlang functions allows a variable number of parameters, which is represented by an ellipsis (...). 

## Basic Java object functions

### _equal(obj1, obj2)

Indicates whether `obj1` is "equal to" `obj2`.

`_equal(obj1, obj2)` in Zlang is equivalent to `obj1.equals(obj2)` in Java.

**Parameters:**

obj1 - the reference object to compare.

obj2 - the reference object with which to compare.

**Returns:**

true if the two objects are the same; false otherwise.

### _compare(obj1, obj2)

Compares `obj1` with `obj2` for order.

`_compare(obj1, obj2)` in Zlang is equivalent to `((Comparable)obj1).compareTo(obj2)` in Java.

**Parameters:**

obj1 - the reference object to compare.

obj2 - the reference object with which to compare.

**Returns:**

A negative integer, zero, or a positive integer
as `obj1` is less than, equal to, or greater than `obj2`.

### _hashcode(obj)

Returns a hash code value for `obj`.

`_hashcode(obj)` in Zlang is equivalent to `obj.hashCode()` in Java.

**Parameters:**

obj - the object.

**Returns:**

A hash code value for this object.

## Type functions

### _get_class(obj)

Returns the runtime class of `obj`.

`_get_class(obj)` in Zlang is equivalent to `obj.getClass()` in Java.

**Parameters:**

obj - the object.

**Returns:**

The `Class` object that represents the runtime class of this object.

### _get_class_name(obj)

Returns the name of the runtime class of `obj`.

`_get_class_name(obj)` in Zlang is equivalent to `obj.getClass().getName()` in Java.

**Parameters:**

obj - the object.

**Returns:**

The name of the `Class` object that represents the runtime class of this object.

### _instance_of(obj, class)

Determines if `obj` is assignment-compatible with the object represented by `class`.

**Parameters:**

obj - the object.

class - the specified class, may be a `Class` object, or a string which specifies the name of the class.

**Returns:**

true if obj is an instance of the class.

### _is_boolean(obj)

Determines if `obj` is a `boolean` or a `Boolean`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is a `boolean` or a `Boolean`.

### _is_char(obj)

Determines if `obj` is a `char` or a `Character`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is a `char` or a `Character`.

### _is_short(obj)

Determines if `obj` is a `short` or a `Short`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is a `short` or a `Short`.

### _is_int(obj)

Determines if `obj` is an `int` or an `Integer`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is an `int` or an `Integer`.

### _is_long(obj)

Determines if `obj` is a `long` or a `Long`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is a `long` or a `Long`.

### _is_float(obj)

Determines if `obj` is a `float` or a `Float`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is a `float` or a `Float`.

### _is_double(obj)

Determines if `obj` is a `double` or a `Double`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is a `double` or a `Double`.

### _is_string(obj)

Determines if `obj` is a `String`.

**Parameters:**

obj - the object.

**Returns:**

true if `obj` is a `String`.

## Array functions

### _length(arr)

Returns the length of the array.

**Parameters:**

arr - the array.

**Returns:**

The length of the array.

### _array_of(...)

Returns an new array containing the objects passed to it.

`_array_of(1, 4, 3)` returns {1, 4, 3}. `_array_of("abc", 45, 8.9)` returns {"abc", 45, 8.9}.

**Parameters:**

... - the objects to put into the array.

**Returns:**

The new array.

### _new_array(type, ...)

Returns an new array with the specified component type and dimensions.

`_new_array("int", 4)` returns `new int[4]`. `_new_array("java.lang.Integer", 4, 5)` returns `new Integer[4][5]`.

**Parameters:**

type - the Class object representing the component type of the new array.

... - an array of `int` representing the dimensions of the new array. 

**Returns:**

The new array.

## List functions

### _new_list()

Returns a new list.

**Returns:**

A new list.

### _size(list)

Returns the number of elements in the list.

**Parameters:**

list - the list.

**Returns:**

The number of elements in the list.

### _is_empty(list)

Returns true if the list contains no elements.

**Parameters:**

list - the list.

**Returns:**

true if the list contains no elements, false otherwise.

### _add(list, element)

Appends the specified element to the end of the list.

**Parameters:**

list - the list.

element - element to be appended to the list.

### _remove(list, element)

Removes the first occurrence of the specified element from the list, if it is present.

**Parameters:**

list - the list.

element -  element to be removed from the list, if present.

**Returns:**

true if the list contained the specified element.

### _contains(list, element)

Returns true if the list contains the specified element.

**Parameters:**

list - the list.

element -  whose presence in the list is to be tested.

**Returns:**

true if the list contains the specified element.

### _list_get(list, index)

Returns the element at the specified position in the list.

**Parameters:**

list - the list.

index - index of the element to return. 

**Returns:**

The element at the specified position in the list.

### _list_set(list, index, element)

Replaces the element at the specified position in the list with the specified element (optional operation).

**Parameters:**

list - the list.

index - index of the element to replace.

element - element to be stored at the specified position.

**Returns:**

The element previously at the specified position.

## Set

### _new_set()

Returns a new set.

**Returns:**

A new set.

### _size(set)

Returns the number of elements in the set.

**Parameters:**

set - the set.

**Returns:**

The number of elements in the set.

### _is_empty(set)

Returns true if the set contains no elements.

**Parameters:**

set - the set.

**Returns:**

true if the set contains no elements, false otherwise.

### _add(set, element)

Adds the specified element to the set if it is not already present.

**Parameters:**

set - the set.

element - element to be added to the set. 

**Returns:**

true if the set did not already contain the specified element, false otherwise.

### _remove(set, element)

Removes the specified element from the set if it is present.

**Parameters:**

set - the set.

element - element to be removed from the set, if present.

**Returns:**

true if the list contained the specified element.

### _contains(set, element)

Returns true if the set contains the specified element.

**Parameters:**

set - the set.

element - whose presence in the set is to be tested.

**Returns:**

true if the set contains the specified element.

## Map

### _new_map()

Returns a new map.

**Returns:**

A new map.

### _size(map)

Returns the number of key-value mappings in the map.

**Parameters:**

map - the map.

**Returns:**

The  number of key-value mappings in the map.

### _is_empty(map)

Returns true if the map contains no key-value mappings.

**Parameters:**

map - the map.

**Returns:**

true if the map contains no key-value mappings, false otherwise.

### _map_put(map, key, value)

Associates the specified value with the specified key in the map.
If the map previously contained a mapping for the key, the old value is replaced by the specified value.

**Parameters:**

map - the map.

key - key with which the specified value is to be associated.

value - value to be associated with the specified key.

**Returns:**

The previous value associated with key, or null if there was no mapping for key.

### _map_get(map, key)

Returns the value to which the specified key is mapped, or null if the map contains no mapping for the key.

**Parameters:**

map - the map.

key - the key whose associated value is to be returned.

**Returns:**

The value to which the specified key is mapped, or null if the map contains no mapping for the key.

### _map_contains_key(map, key)

Returns true if the map contains a mapping for the specified key.

**Parameters:**

map - the map.

key -  key whose presence in the map is to be tested.

**Returns:**

true if the map contains a mapping for the specified key.

### _map_contains_value(map, value)

Returns true if the map maps one or more keys to the specified value.

**Parameters:**

map - the map.

value - value whose presence in the map is to be tested.

**Returns:**

true if the map maps one or more keys to the specified value.

## Console output

### _print(o)

Prints an object.

**Parameters:**

o - the object, may be a character, an integer, a string, an object, etc.

### _println(o)

Prints an object and then terminate the line.

**Parameters:**

o - the object, may be a character, an integer, a string, an object, etc.

### _println()

Terminates the current line by writing the line separator string.

## Instance creation

### _new_instance(class, ...)

Create and initialize a new instance of the specified class, with the specified initialization parameters.

`_new_instance("java.math.BigInteger", "123")` in Zlang is equivalent to `new BigInteger("123")` in Java.

`_new_instance("com.example.Example", 389, true, temp)` in Zlang is equivalent to `new Example(389, true, temp)` in Java.

**Parameters:**

class - the specified class, may be a `Class` object, or a string which specifies the name of the class.

... - the specified initialization parameters.

**Returns:**

The new instance.

## Method invocation

### _invoke_method(object, method_name, ...)

Invokes the method specified by the method name, on the specified object with the specified parameters.

`_invoke_method(anObject, "fun")` in Zlang is equivalent to `anObject.fun()` in Java.

`_invoke_method(anObject, "fun", 1, "input")` in Zlang is equivalent to `anObject.fun(1, "input")` in Java.

**Parameters:**

object - the specified object.

method_name - the name of the specified method.

... - the specified parameters.

**Returns:**

The return value of the method.

### _invoke_public_method(object, method_name, ...)

Invokes the public member method specified by the method name, on the specified object with the specified parameters.

`_invoke_method(anObject, "fun")` in Zlang is equivalent to `anObject.fun()` in Java.

`_invoke_method(anObject, "fun", 1, "input")` in Zlang is equivalent to `anObject.fun(1, "input")` in Java.

**Parameters:**

object - the specified object.

method_name - the name of the specified method.

... - the specified parameters.

**Returns:**

The return value of the method.

### _invoke_static_method(target, method_name, ...)

Invokes the static method specified by the method name, on the specified target with the specified parameters.

Suppose there is a class `com.example.Example`, `anObject` is an instance of the class, and the class has
a static method `fun`.

`_invoke_static_method(anObject, "fun", 123)` in Zlang is equivalent to `anObject.fun(123)` in Java.

`_invoke_static_method("com.example.Example", "fun", 123)` in Zlang is equivalent to `Example.fun(123)` in Java.

**Parameters:**

target - the specified target, may be an object, may be a `Class` object, or a string which specifies the name of the class.

method_name - the name of the specified method.

... - the specified parameters.

**Returns:**

The return value of the method.

### _invoke_static_public_method(target, method_name, ...)

Invokes the static public member method specified by the method name, on the specified target with the specified parameters.

Suppose there is a class `com.example.Example`, `anObject` is an instance of the class, and the class has
a static method `fun`.

`_invoke_static_method(anObject, "fun", 123)` in Zlang is equivalent to `anObject.fun(123)` in Java.

`_invoke_static_method("com.example.Example", "fun", 123)` in Zlang is equivalent to `Example.fun(123)` in Java.

**Parameters:**

target - the specified target, may be an object, may be a `Class` object, or a string which specifies the name of the class.

method_name - the name of the specified method.

... - the specified parameters.

**Returns:**

The return value of the method.

## Field access

### _get_field(object, field_name)

Returns the value of the field specified by the field name on the specified object.

`a = _get_field(anObject, "field")` in Zlang is equivalent to `a = anObject.field` in Java.

**Parameters:**

object - the specified object.

field_name - the name of the specified field.

**Returns:**

The value of the field in the object.

### _get_public_field(object, field_name)

Returns the value of the public member field specified by the field name on the specified object.

`a = _get_field(anObject, "field")` in Zlang is equivalent to `a = anObject.field` in Java.

**Parameters:**

object - the specified object.

field_name - the name of the specified field.

**Returns:**

The value of the public member field in the object.

### _get_static_field(target, field_name)

Returns the value of the static field specified by the field name on the specified target.

`a = _get_field(anObject, "field")` in Zlang is equivalent to `a = anObject.field` in Java.
Suppose there is a class `com.example.Example`, `anObject` is an instance of the class, and the class has
a static field `field`.

`a = _get_static_field(anObject, "field")` in Zlang is equivalent to `a = anObject.field` in Java.

`a = _get_static_field("com.example.Example, "field")` in Zlang is equivalent to `a = Example.field` in Java.

**Parameters:**

target - the specified target, may be an object, may be a `Class` object, or a string which specifies the name of the class.

field_name - the name of the specified field.

**Returns:**

The value of the static field in the object.


### _get_static_public field(target, field_name)

Returns the value of the static public member field specified by the field name on the specified target.

`a = _get_field(anObject, "field")` in Zlang is equivalent to `a = anObject.field` in Java.
Suppose there is a class `com.example.Example`, `anObject` is an instance of the class, and the class has
a static field `field`.

`a = _get_static_field(anObject, "field")` in Zlang is equivalent to `a = anObject.field` in Java.

`a = _get_static_field("com.example.Example, "field")` in Zlang is equivalent to `a = Example.field` in Java.

**Parameters:**

target - the specified target, may be an object, may be a `Class` object, or a string which specifies the name of the class.

field_name - the name of the specified field.

**Returns:**

The value of the static public member field in the object.

### _set_field(object, field_name, value)

Sets the field specified by the field name on the specified object to the specified new value.

`_set_field(anObject, "field", 345)` in Zlang is equivalent to `anObject.field = 345` in Java.

**Parameters:**

object - the specified object whose field should be modified.

field_name - the name of the specified field.

value - the new value for the field of the object being modified

### _set_public_field(object, field_name, value)

Sets the public member field specified by the field name on the specified object to the specified new value.

`_set_field(anObject, "field", 345)` in Zlang is equivalent to `anObject.field = 345` in Java.

**Parameters:**

object - the specified object whose field should be modified.

field_name - the name of the specified field.

value - the new value for the field of the object being modified

### _set_static_field(target, field_name, value)

Sets the static field specified by the field name on the specified target to the specified new value.

Suppose there is a class `com.example.Example`, `anObject` is an instance of the class, and the class has
a static field `field`.

`_set_static_field(anObject, "field", 34)` in Zlang is equivalent to `anObject.field = 34` in Java.

`_set_static_field("com.example.Example, "field", 34)` in Zlang is equivalent to `Example.field = 34` in Java.

**Parameters:**

target - the specified object whose field should be modified, may be a `Class` object, or a string which specifies the name of the class.

field_name - the name of the specified field.

value - the new value for the field of the object being modified.

### _set_static_public_field(target, field_name, value)

Sets the static public member field specified by the field name on the specified target to the specified new value.

Suppose there is a class `com.example.Example`, `anObject` is an instance of the class, and the class has
a static field `field`.

`_set_static_field(anObject, "field", 34)` in Zlang is equivalent to `anObject.field = 34` in Java.

`_set_static_field("com.example.Example, "field", 34)` in Zlang is equivalent to `Example.field = 34` in Java.

**Parameters:**

target - the specified object whose field should be modified, may be a `Class` object, or a string which specifies the name of the class.

field_name - the name of the specified field.

value - the new value for the field of the object being modified.