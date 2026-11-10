package dev.tim9h.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class LimitedIterableStack<T> implements Iterable<T> {

	private final ArrayList<T> elements = new ArrayList<>();

	private int capacity;

	private int cursor = 0;

	public LimitedIterableStack(int capacity) {
		this.capacity = Math.max(0, capacity);
	}

	public void push(T value) {
		if (capacity == 0) {
			return;
		}
		if (elements.size() == capacity) {
			elements.removeFirst();
		}
		elements.add(value);
		cursor = 0;
	}

	public T pop() {
		if (elements.isEmpty()) {
			return null;
		}
		var value = elements.removeLast();
		if (cursor > 0 && !elements.isEmpty()) {
			cursor--;
		}
		cursor = Math.clamp(cursor, 0, Math.max(0, elements.size() - 1));
		return value;
	}

	public T peek() {
		return elements.isEmpty() ? null : elements.getLast();
	}

	public T previous() {
		if (cursor + 1 >= elements.size()) {
			return null;
		}
		cursor++;
		return getCursorValue();
	}

	public T next() {
		if (cursor == 0) {
			return null;
		}
		cursor--;
		return getCursorValue();
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = Math.max(0, capacity);
		if (this.capacity < elements.size()) {
			var excess = elements.size() - this.capacity;
			elements.subList(0, excess).clear();
			cursor = Math.clamp(cursor, 0, Math.max(0, elements.size() - 1));
		}
	}

	public String toString() {
		if (elements.isEmpty()) {
			return "[empty]";
		}
		var result = new StringBuilder("[");
		for (var index = 0; index < elements.size(); index++) {
			if (index > 0) {
				result.append(", ");
			}
			var value = String.valueOf(elements.get(index));
			if (index == elements.size() - 1 - cursor) {
				result.append('(').append(value).append(')');
			} else {
				result.append(value);
			}
		}
		return result.append(']').toString();
	}

	public int hashCode() {
		return Objects.hash(elements, capacity, cursor);
	}

	public boolean equals(Object obj) {
		return this == obj || obj instanceof LimitedIterableStack<?> other && capacity == other.capacity
				&& cursor == other.cursor && elements.equals(other.elements);
	}

	public int size() {
		return elements.size();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public T get(int index) {
		return elements.get(index);
	}

	public T getFirst() {
		return elements.getFirst();
	}

	public T getLast() {
		return elements.getLast();
	}

	@Override
	public Iterator<T> iterator() {
		return Collections.unmodifiableList(new ArrayList<>(elements)).iterator();
	}

	public int getCursor() {
		return cursor;
	}

	public T getCursorValue() {
		if (elements.isEmpty()) {
			return null;
		}
		return elements.get(elements.size() - 1 - cursor);
	}

	public void resetCursor() {
		cursor = 0;
	}

}
