# collections

A small Java collections library published as `dev.tim9h:collections`.

## LimitedIterableStack

`LimitedIterableStack<T>` is a stack with a configurable capacity and a cursor for browsing stored values. The newest value is at the top of the stack. `push` adds to the top and, when the positive capacity is reached, discards the oldest value. A zero capacity stores no values. `pop` and `peek` return `null` when the stack is empty.

After a push, the cursor sits immediately before the newest value (offset `-1`), so the first `previous()` selects the latest pushed value. Calling `previous()` then moves toward older values, and `next()` moves back toward newer values; either returns `null` when there is no value in that direction. `getCursorValue()` returns the selected value, or `null` when no value is selected. `resetCursor()` returns the cursor to immediately before the newest value, ready for a new history-navigation sequence.

```java
import dev.tim9h.collections.LimitedIterableStack;

var history = new LimitedIterableStack<String>(3);
history.push("first");
history.push("second");
history.push("third");
history.push("fourth"); // "first" is discarded

var top = history.previous();   // "fourth"
var older = history.previous(); // "third"
```

The capacity can be read with `getCapacity()` and changed with `setCapacity(int)`. Setting capacity to zero or less clears the stack and sets capacity to zero. A positive capacity trims the oldest values if needed. The collection encapsulates its storage and exposes a read-only iterator, so callers cannot bypass the capacity limit using inherited collection mutators.

## Build

This project uses Maven and targets Java 26. Run the tests and build with:

```shell
mvn test
```
