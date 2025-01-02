package dev.tim9h.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
		assertEquals(2, stack.previous());
		assertEquals(1, stack.getCursor());
		assertEquals(1, stack.previous());
		assertEquals(2, stack.getCursor());
		assertEquals(0, stack.previous());
		assertEquals(null, stack.previous());
		assertEquals(0, stack.getCursor());
		assertEquals(2, stack.previous());
		assertEquals(1, stack.getCursor());
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
		assertEquals(0, stack.next());
		assertEquals(3, stack.getCursor());
		assertEquals(1, stack.next());
		assertEquals(2, stack.getCursor());
		assertEquals(2, stack.next());
		assertEquals(1, stack.getCursor());
		assertEquals(null, stack.next());
		assertEquals(0, stack.getCursor());
		assertEquals(0, stack.next());
		assertEquals(3, stack.getCursor());
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
		stack.setCapacity(4);
		stack.previous();
		stack.previous();
		stack.setCapacity(3);
		assertEquals(0, stack.getCursor());
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

}
