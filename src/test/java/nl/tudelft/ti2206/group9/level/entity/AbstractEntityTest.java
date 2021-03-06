package nl.tudelft.ti2206.group9.level.entity;

import nl.tudelft.ti2206.group9.gui.renderer.AbstractBoxRenderer;
import nl.tudelft.ti2206.group9.level.CollisionHandler;
import nl.tudelft.ti2206.group9.level.Track;
import nl.tudelft.ti2206.group9.util.Point3D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractEntityTest {

    public static final int THREE = 3;

    private transient AbstractEntity first;
    private transient AbstractEntity second;

    private transient AbstractEntity entity;

    private CollisionHandler collisionHandler;

    @Before
    public void setUp() {
        first = mock(AbstractEntity.class);
        second = mock(AbstractEntity.class);

        entity = new TestEntity(Point3D.ZERO, Point3D.UNITCUBE);
        collisionHandler = mock(CollisionHandler.class);
        Track.getInstance().setCollisions(collisionHandler);
    }

    @Test
    public void testCheckCollisionDistance1Size1() {
        setEntities(Point3D.ZERO, Point3D.UNITCUBE,
                    Point3D.UNITX, Point3D.UNITCUBE);
        first.checkCollision(second);

        verify(collisionHandler).collide(first, second);
    }

    @Test
    public void testCheckCollisionDistance3Size1() {
        setEntities(Point3D.ZERO, Point3D.UNITCUBE,
                new Point3D(THREE, 0, 0), Point3D.UNITCUBE);
        first.checkCollision(second);

        verify(collisionHandler, never()).collide(first, second);
    }

    @Test
    public void testCheckCollisionDistanceSqrt2Size1() {
        setEntities(Point3D.ZERO, Point3D.UNITCUBE,
                    new Point3D(1, 1, 0), Point3D.UNITCUBE);
        first.checkCollision(second);

        verify(collisionHandler).collide(first, second);
    }

    @Test
    public void testCheckCollisionDistance0Size1() {
        setEntities(Point3D.ZERO, Point3D.UNITCUBE,
                    Point3D.ZERO, Point3D.UNITCUBE);
        first.checkCollision(second);

        verify(collisionHandler).collide(first, second);
    }

    @Test
    public void testCheckCollisionDistanceHalfSize1() {
        setEntities(Point3D.ZERO, Point3D.UNITCUBE,
                    new Point3D(1.0 / 2.0, 0, 0), Point3D.UNITCUBE);
        first.checkCollision(second);

        verify(collisionHandler).collide(first, second);
    }

    @Test
    public void testCheckCollisionDistance1Size2() {
        setEntities(Point3D.ZERO, new Point3D(2, 2, 2),
                    Point3D.UNITX, new Point3D(2, 2, 2));
        first.checkCollision(second);

        verify(collisionHandler).collide(first, second);
    }

    @Test
    public void testCheckCollisionDistance2Size2() {
        setEntities(Point3D.ZERO, new Point3D(2, 2, 2),
                    new Point3D(2, 0, 0), new Point3D(2, 2, 2));
        first.checkCollision(second);

        verify(collisionHandler).collide(first, second);
    }

    @Test
    public void testCheckCollisionDistance3Size2() {
        setEntities(Point3D.ZERO, new Point3D(2, 2, 2),
                    new Point3D(THREE, 0, 0), new Point3D(2, 2, 2));
        first.checkCollision(second);

        verify(collisionHandler, never()).collide(first, second);
    }

    public void setEntities(final Point3D firstCenter, final Point3D firstSize,
            final Point3D secondCenter, final Point3D secondSize) {
        when(first.getCenter()).thenReturn(firstCenter);
        when(first.getSize()).thenReturn(firstSize);
        when(second.getCenter()).thenReturn(secondCenter);
        when(second.getSize()).thenReturn(secondSize);
    }



    @Test
    public void testAbstractEntity() {
        entity = new AbstractEntity(Point3D.ZERO, Point3D.UNITCUBE) {

            @Override
            public AbstractBoxRenderer<? extends AbstractEntity>
                    createRenderer() {
                return null;
            }
        };
        assertEquals(Point3D.ZERO, entity.getCenter());
        assertEquals(Point3D.UNITCUBE, entity.getSize());
    }

    @Test
    public void testGetCenter() {
        assertEquals(Point3D.ZERO, entity.getCenter());
    }

    @Test
    public void testSetCenter() {
        entity.setCenter(Point3D.UNITX);
        assertEquals(Point3D.UNITX, entity.getCenter());
    }

    @Test
    public void testGetSize() {
        assertEquals(Point3D.UNITCUBE, entity.getSize());
    }

    @Test
    public void testSetSize() {
        entity.setSize(new Point3D(2.0, 2.0, 2.0));
        assertEquals(new Point3D(2.0, 2.0, 2.0), entity.getSize());
    }

    @Test
    public void testHashCode() {
        final int centerHash = Point3D.ZERO.hashCode();
        final int sizeHash = Point3D.UNITCUBE.hashCode();
        final int prime = 31;
        assertEquals(prime * prime,
                new TestEntity(null, null).hashCode());
        assertEquals((prime + centerHash) * prime,
                new TestEntity(Point3D.ZERO, null).hashCode());
        assertEquals(prime * prime + sizeHash,
                new TestEntity(null, Point3D.UNITCUBE).hashCode());
        assertEquals((prime + centerHash) * prime + sizeHash,
                entity.hashCode());
    }

    @Test
    public void testEquals() {
        final AbstractEntity null1 = new TestEntity(null, Point3D.UNITCUBE);
        final AbstractEntity null2 = new TestEntity(Point3D.ZERO, null);

        assertTrue(entity.equals(entity));
        assertFalse(entity.equals(null)); // NOPMD - intended equals(null)
        assertFalse(entity.equals(""));
        assertFalse(null1.equals(entity));
        assertTrue(null1.equals(new TestEntity(null, Point3D.UNITCUBE)));
        assertFalse(entity.equals(null1));
        assertFalse(entity.equals(new TestEntity(Point3D.UNITX,
                Point3D.UNITCUBE)));
        assertFalse(null2.equals(entity));
        assertTrue(null2.equals(new TestEntity(Point3D.ZERO, null)));
        assertFalse(entity.equals(null2));
        assertFalse(entity.equals(new TestEntity(Point3D.ZERO,
                new Point3D(2, 2, 2))));
        assertTrue(entity.equals(new TestEntity(Point3D.ZERO,
                Point3D.UNITCUBE)));
    }

    @Test
    public void testToString() {
        assertEquals("TestEntity [center=" + entity.getCenter()
                + ",size=" + entity.getSize() + "]", entity.toString());
    }

    /**
     * This class extends the AbstractEntity with default behaviour because
     * it is easier to test.
     * @author Maarten
     */
    private static class TestEntity extends AbstractEntity {

        /**
         * Default constructor.
         * @param center center of the bounding box
         * @param size size of the bounding box
         */
        TestEntity(final Point3D center, final Point3D size) {
            super(center, size);
        }

        @Override
        public AbstractBoxRenderer<? extends AbstractEntity> createRenderer() {
            return null;
        }

    }
}
