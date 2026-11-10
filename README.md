# collections

A small Java collections library published as `dev.tim9h:collections`. The repository currently contains only one collection: `LimitedIterableStack<T>`.

## LimitedIterableStack

`LimitedIterableStack<T>` is a stack with a configurable capacity and a cursor for browsing previously pushed values. The newest value is at the top of the stack. `push` adds to the top and, when the positive capacity is exceeded, discards the oldest value. `pop` and `peek` return `null` when the stack is empty.

The cursor starts at the top position. Calling `previous()` moves toward older values; calling `next()` moves back toward newer values. `resetCursor()` returns the cursor to its initial position. `getCursorValue()` returns the value at the current cursor position and requires that position to refer to an existing element.

```java
import dev.tim9h.collections.LimitedIterableStack;

var history = new LimitedIterableStack<String>(3);
history.push("first");
history.push("second");
history.push("third");
history.push("fourth"); // "first" is discarded

var top = history.peek();       // "fourth"
var older = history.previous(); // "third"
```

The capacity can be read with `getCapacity()` and changed with `setCapacity(int)`. Setting capacity to zero or less clears the stack and sets capacity to zero. A positive capacity trims the oldest values if needed. Since this class extends `LinkedList`, operations inherited from `LinkedList` other than `push` are not generally subject to the capacity limit.

## Build

This project uses Maven and targets Java 26. Run the tests and build with:

```shell
mvn test
```
