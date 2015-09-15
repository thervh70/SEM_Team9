package nl.tudelft.ti2206.group9.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class Point3DTest {

	public static final double DELTA = 0.00000001;
	
	private transient Point3D point;
	
	@Before
	public void setUp() throws Exception {
		point = new Point3D(1.0, 2.0, 0.0);
	}

	@Test
	public void testPoint3DDoubleDoubleDouble() {
		assertEquals(1.0, point.getX(), DELTA);
		assertEquals(2.0, point.getY(), DELTA);
		assertEquals(0.0, point.getZ(), DELTA);
	}

	@Test
	public void testPoint3DPoint3D() {
		Point3D point1 = new Point3D(point);
		assertEquals(1.0, point1.getX(), DELTA);
		assertEquals(2.0, point1.getY(), DELTA);
		assertEquals(0.0, point1.getZ(), DELTA);
	}

	@Test
	public void testAddX() {
		point.addX(1.0);
		assertEquals(2.0, point.getX(), DELTA);
	}

	@Test
	public void testAddY() {
		point.addY(-1.0);
		assertEquals(1.0, point.getY(), DELTA);
	}

	@Test
	public void testAddZ() {
		point.addZ(1.0);
		assertEquals(1.0, point.getZ(), DELTA);
	}

	@Test
	public void testGetX() {
		assertEquals(1.0, point.getX(), DELTA);
	}

	@Test
	public void testSetX() {
		point.setX(2.0);
		assertEquals(2.0, point.getX(), DELTA);
	}

	@Test
	public void testGetY() {
		assertEquals(2.0, point.getY(), DELTA);
	}

	@Test
	public void testSetY() {
		point.setY(0.0);
		assertEquals(0.0, point.getY(), DELTA);
	}

	@Test
	public void testGetZ() {
		assertEquals(0.0, point.getZ(), DELTA);
	}

	@Test
	public void testSetZ() {
		point.setZ(2.0);
		assertEquals(2.0, point.getZ(), DELTA);
	}

	@Test
	public void testSet() {
		Point3D point1 = new Point3D(0.0, 1.0, 2.0);
		point.set(point1);
		assertEquals(0.0, point.getX(), DELTA);
		assertEquals(1.0, point.getY(), DELTA);
		assertEquals(2.0, point.getZ(), DELTA);
	}
	
	@Test
	public void testEquals() {
		assertTrue(point.equals(point));
		assertFalse(point.equals(null));
		assertFalse(point.equals(""));
		assertFalse(point.equals(new Point3D(2.0, 2.0, 0.0)));
		assertFalse(point.equals(new Point3D(1.0, 1.0, 0.0)));
		assertFalse(point.equals(new Point3D(1.0, 2.0, 2.0)));
		assertTrue(point.equals(new Point3D(1.0, 2.0, 0.0)));
	}

	@Test
	public void testToString() {
		assertEquals("(1.0, 2.0, 0.0)", point.toString());
	}

}
