package nl.tudelft.ti2206.group9.entities;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PickupTest {

	private static final double DELTA = 0.000001;

	private int testActions;
	private AbstractPickup pickup;

	@Before
	public void setUp() {
		State.resetAll();
		testActions = 0;
	}

	@After
	public void tearDown() {
		State.resetAll();
	}

	@Test
	public void testPickupNoDecoration() {
		pickup = new TestPickup(null);
		pickup.doAction();
		assertEquals(1, pickup.getValue(), DELTA);
		assertEquals(1, State.getScore(), DELTA);
		assertEquals(1, testActions);
	}

	@Test
	public void testPickupSingleDecoration() {
		pickup = new TestPickup(new TestPickup(null));
		pickup.doAction();
		assertEquals(2, pickup.getValue(), DELTA);
		assertEquals(2, State.getScore(), DELTA);
		assertEquals(2, testActions);
	}

	class TestPickup extends AbstractPickup {

		public TestPickup(final AbstractPickup decorating) {
			super(Point3D.ZERO, Point3D.UNITCUBE, decorating);
		}

		@Override
		protected double thisValue() {
			return 1;
		}

		@Override
		protected Action thisAction() {
			return () -> testActions++;
		}

	}

}
