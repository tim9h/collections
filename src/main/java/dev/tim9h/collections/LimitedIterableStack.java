package dev.tim9h.collections;

import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class LimitedIterableStack<T> extends LinkedList<T> {

	private static final long serialVersionUID = 1L;

	private int capacity;

	private int cursor = 0;

	public LimitedIterableStack(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public void push(T value) {
		add(value);
		if (size() > capacity) {
			removeFirst();
		}
		cursor = 0;
	}

	@Override
	public T pop() {
		if (isEmpty()) {
			return null;
		}
		var value = getLast();
		removeLast();
		return value;
	}

	@Override
	public T peek() {
		if (isEmpty()) {
			return null;
		}
		return getLast();
	}

	public T previous() {
		cursor++;
		if (isEmpty()) {
			cursor = 0;
			return null;
		} else if (size() - cursor < 0) {
			cursor = 1;
			return getLast();
		}
		return get(size() - cursor);
	}

	public T next() {
		cursor--;
		if (isEmpty()) {
			cursor = 0;
			return null;
		} else if (cursor <= 0) {
			cursor = size();
			return getFirst();
		}
		return get(size() - cursor);
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		if (capacity < size() && capacity > 0) {
			removeRange(0, size() - capacity);
			if (cursor > capacity) {
				cursor = 0;
			}
		} else if (capacity <= 0) {
			clear();
			this.capacity = 0;
		}
	}

	@Override
	public String toString() {
		return isEmpty() ? "[empty]" : "[" + stream().map(t -> {
			if (cursor > 0 && t == get(size() - cursor)) {
				return "(" + t.toString() + ")";
			} else {
				return t.toString();
			}
		}).collect(Collectors.joining(", ")) + "]";
	}

	@Override
	public int hashCode() {
		var result = super.hashCode();
		return 31 * result + Objects.hash(capacity, cursor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		var other = (LimitedIterableStack<?>) obj;
		return capacity == other.capacity && cursor == other.cursor;
	}
	
	public int getCursor() {
		return cursor;
	}
	
	public T getCursorValue() {
		return get(size() - cursor -1);
	}
	
	public void resetCursor() {
		cursor = 0;
	}

}
