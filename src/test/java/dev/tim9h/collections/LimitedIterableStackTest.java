package dev.tim9h.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LimitedIterableStackTest {

	@Test
	void testCapacity() {
		var capacity = 5;
		var stack = new LimitedIterableStack<Integer>(capacity);
		for (var i = 0; i < 10; i++) {
			stack.push(i);
		}
		assertEquals(5, stack.size());
	}

	@Test
	void testCapacityIncrease() {
		var capacity = 5;
		var stack = new LimitedIterableStack<Integer>(capacity);
		for (var i = 0; i < 10; i++) {
			stack.push(i);
		}
		stack.setCapacity(8);
		for (var i = 10; i < 20; i++) {
			stack.push(i);
		}
		assertEquals(8, stack.size());
		assertEquals(12, stack.getFirst());
		assertEquals(19, stack.getLast());
	}

	@Test
	void testCapacityDecrease() {
		var capacity = 5;
		var stack = new LimitedIterableStack<Integer>(capacity);
		for (var i = 0; i < 10; i++) {
			stack.push(i);
		}
		stack.setCapacity(3);
		assertEquals(3, stack.size());
		assertEquals(7, stack.getFirst());
		assertEquals(9, stack.getLast());
	}

	@Test
	void testPop() {
		var stack = new LimitedIterableStack<Integer>(3);
		for (var i = 0; i < 2; i++) {
			stack.push(i);
		}
		assertEquals(1, stack.pop());
		assertEquals(1, stack.size());
		stack.pop();
		assertTrue(stack.isEmpty());
		assertNull(stack.pop());
	}

	@Test
	void testPrevious() {
		var stack = new LimitedIterableStack<Integer>(3);
		for (var i = 0; i < 3; i++) {
			stack.push(i);
		}
		assertEquals(0, stack.getCursor());
		assertEquals(1, stack.previous());
		assertEquals(1, stack.getCursor());
		assertEquals(0, stack.previous());
		assertEquals(2, stack.getCursor());
		assertNull(stack.previous());
		assertEquals(2, stack.getCursor());
		stack.setCapacity(0);
		assertNull(stack.previous());
	}

	@Test
	void testNext() {
		var stack = new LimitedIterableStack<Integer>(3);
		for (var i = 0; i < 3; i++) {
			stack.push(i);
		}
		assertEquals(0, stack.getCursor());
		assertNull(stack.next());
		assertEquals(0, stack.getCursor());
		stack.previous();
		assertEquals(2, stack.next());
		assertEquals(0, stack.getCursor());
		stack.setCapacity(0);
		assertNull(stack.next());
	}

	@Test
	void testCapacityCursorMovement() {
		var stack = new LimitedIterableStack<Integer>(5);
		for (var i = 0; i < 5; i++) {
			stack.push(i);
		}
		stack.previous();
		stack.previous();
		assertEquals(2, stack.getCursor());
		assertEquals(2, stack.getCursorValue());
		stack.setCapacity(4);
		assertEquals(2, stack.getCursorValue());
		stack.setCapacity(3);
		assertEquals(2, stack.getCursorValue());
	}

	@Test
	void testResetCursor() {
		var stack = new LimitedIterableStack<Integer>(5);
		for (var i = 0; i < 5; i++) {
			stack.push(i);
		}
		stack.previous();
		stack.previous();
		stack.resetCursor();
		assertEquals(0, stack.getCursor());
	}

	@Test
	void testCursorValueAndPopRemainValid() {
		var stack = new LimitedIterableStack<Integer>(3);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(3, stack.getCursorValue());
		assertEquals(2, stack.previous());
		assertEquals(2, stack.getCursorValue());
		assertEquals(3, stack.pop());
		assertEquals(0, stack.getCursor());
		assertEquals(2, stack.getCursorValue());
		assertNull(new LimitedIterableStack<Integer>(2).getCursorValue());
	}

	@Test
	void testCapacityCannotBeBypassedAndIteratorIsReadOnly() {
		var stack = new LimitedIterableStack<Integer>(2);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(2, stack.size());
		assertEquals(2, stack.getFirst());
		var iterator = stack.iterator();
		assertEquals(2, iterator.next());
		assertThrows(UnsupportedOperationException.class, iterator::remove);
		assertEquals(2, stack.size());
	}

	@Test
	void testNullValuesAndEquality() {
		var stack = new LimitedIterableStack<String>(2);
		stack.push(null);
		assertEquals("[(null)]", stack.toString());
		var equalStack = new LimitedIterableStack<String>(2);
		equalStack.push(null);
		assertEquals(stack, equalStack);
		assertEquals(stack.hashCode(), equalStack.hashCode());
		assertNotEquals(stack, new LimitedIterableStack<String>(3));
	}

}
