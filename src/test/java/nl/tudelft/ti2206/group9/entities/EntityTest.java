package nl.tudelft.ti2206.group9.entities;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.Before;
import org.junit.Test;

public class EntityTest {
	
	public static final int THREE = 3;
	
	private AbstractEntity first;
	private AbstractEntity second;

	@Before
	public void setUp() throws Exception {
		first = mock(AbstractEntity.class);
		second = mock(AbstractEntity.class);
	}

	@Test
	public void testCheckCollisionDistance1Size1() {
		setEntities(Point3D.ZERO, Point3D.UNITCUBE, 
					Point3D.UNITX, Point3D.UNITCUBE);
		first.checkCollision(second);
		
		verify(first).collision(second);
	}

	@Test
	public void testCheckCollisionDistance3Size1() {
		setEntities(Point3D.ZERO, Point3D.UNITCUBE, 
					new Point3D(THREE, 0, 0), Point3D.UNITCUBE);
		first.checkCollision(second);
		
		verify(first, never()).collision(second);
	}

	@Test
	public void testCheckCollisionDistanceSqrt2Size1() {
		setEntities(Point3D.ZERO, Point3D.UNITCUBE, 
					new Point3D(1, 1, 0), Point3D.UNITCUBE);
		first.checkCollision(second);
		
		verify(first).collision(second);
	}

	@Test
	public void testCheckCollisionDistance0Size1() {
		setEntities(Point3D.ZERO, Point3D.UNITCUBE, 
					Point3D.ZERO, Point3D.UNITCUBE);
		first.checkCollision(second);
		
		verify(first).collision(second);
	}

	@Test
	public void testCheckCollisionDistance0_5Size1() {
		setEntities(Point3D.ZERO, Point3D.UNITCUBE, 
					new Point3D(1.0 / 2.0, 0, 0), Point3D.UNITCUBE);
		first.checkCollision(second);
		
		verify(first).collision(second);
	}

	@Test
	public void testCheckCollisionDistance1Size2() {
		setEntities(Point3D.ZERO, new Point3D(2, 2, 2), 
					Point3D.UNITX, new Point3D(2, 2, 2));
		first.checkCollision(second);
		
		verify(first).collision(second);
	}

	@Test
	public void testCheckCollisionDistance2Size2() {
		setEntities(Point3D.ZERO, new Point3D(2, 2, 2), 
					new Point3D(2, 0, 0), new Point3D(2, 2, 2));
		first.checkCollision(second);
		
		verify(first).collision(second);
	}

	@Test
	public void testCheckCollisionDistance3Size2() {
		setEntities(Point3D.ZERO, new Point3D(2, 2, 2), 
					new Point3D(THREE, 0, 0), new Point3D(2, 2, 2));
		first.checkCollision(second);
		
		verify(first, never()).collision(second);
	}
	
	public void setEntities(final Point3D firstCenter, final Point3D firstSize, 
			final Point3D secondCenter, final Point3D secondSize) {
		when(first.getCenter()).thenReturn(firstCenter);
		when(first.getSize()).thenReturn(firstSize);
		when(second.getCenter()).thenReturn(secondCenter);
		when(second.getSize()).thenReturn(secondSize);
	}

}
