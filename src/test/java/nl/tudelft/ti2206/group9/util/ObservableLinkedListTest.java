package nl.tudelft.ti2206.group9.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList.Listener;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList.Listener.Type;

import org.junit.Before;
import org.junit.Test;

public class ObservableLinkedListTest {

	private Listener testListener;
	private ObservableLinkedList<Integer> testList;

	@Before
	public void setUp() {
		testListener = mock(Listener.class);
		testList = new ObservableLinkedList<Integer>();
	}

	@Test
	public void testRemoveListener() {
		testList.addListener(testListener);
		testList.removeListener(testListener);
		testList.add(1);
		verify(testListener, never()).update(Type.ADD_LAST, 1, -1);
	}

	@Test
	public void testAddT() {
		testList.addListener(testListener);
		testList.add(1);
		verify(testListener).update(Type.ADD_LAST, 1, -1);
		testList.removeListener(testListener);
	}

	@Test
	public void testAddFirstT() {
		testList.addListener(testListener);
		testList.addFirst(1);
		verify(testListener).update(Type.ADD_FIRST, 1, -1);
		testList.removeListener(testListener);
	}

	@Test
	public void testAddLastT() {
		testList.addListener(testListener);
		testList.addLast(1);
		verify(testListener).update(Type.ADD_LAST, 1, -1);
		testList.removeListener(testListener);
	}

	@Test
	public void testRemoveObject() {
		testList.addListener(testListener);
		final Integer item = 1;
		testList.add(item);
		testList.remove(item);
		verify(testListener).update(Type.REMOVE, 1, -1);
		testList.removeListener(testListener);
	}

	@Test
	public void testRemoveInt() {
		testList.addListener(testListener);
		final Integer item = 1;
		testList.add(item);
		testList.remove(0);
		verify(testListener).update(Type.ADD_LAST, 1, -1);
		verify(testListener).update(Type.REMOVE_INDEX, 1, 0);
		testList.removeListener(testListener);
	}

}
