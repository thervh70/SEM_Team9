package nl.tudelft.ti2206.group9;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HelloGameTest {
	
	private int answer = 42;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMain() {
		assertEquals(42, answer);
		HelloGame.main(null);
		assertEquals(42, answer);
	}

}
