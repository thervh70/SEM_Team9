package nl.tudelft.ti2206.group9.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;

/**
 * A map of possible collisions and their handlers.
 * @author Mathias
 */
public class CollisionMap {

    /** The collection of collision handlers. */
    private final Map<Class<? extends AbstractEntity>,
            Map<Class<? extends AbstractEntity>, CollisionHandler<?, ?>>
            > handlers;

    /**
     * Creates a new, empty collision map.
     */
    public CollisionMap() {
        this.handlers = new HashMap<>();
    }

    /**
     * Adds a two-way collision interaction to this collection, i.e. the
     * collision handler will be used for both C1 versus C2 and C2 versus C1.
     *
     * @param <C1> The collider type.
     * @param <C2> The collidee (AbstractEntity that was moved into) type.
     * @param collider The collider type.
     * @param collidee The collidee type.
     * @param handler The handler that handles the collision.
     */
    public <C1 extends AbstractEntity, C2 extends AbstractEntity> void
    onCollision(
            final Class<C1> collider, final Class<C2> collidee,
            final CollisionHandler<C1, C2> handler) {
        onCollision(collider, collidee, true, handler);
    }

    /**
     * Adds a collision interaction to this collection.
     *
     * @param <C1> The collider type.
     * @param <C2> The collidee (AbstractEntity that was moved into) type.
     * @param collider The collider type.
     * @param collidee The collidee type.
     * @param symetric <code>true</code> if this collision is used for both
     *                  C1 against C2 and vice versa;
     *                  <code>false</code> if only for C1 against C2.
     * @param handler The handler that handles the collision.
     */
    public <C1 extends AbstractEntity, C2 extends AbstractEntity> void
    onCollision(
            final Class<C1> collider, final Class<C2> collidee,
            final boolean symetric, final CollisionHandler<C1, C2> handler) {
        addHandler(collider, collidee, handler);
        if (symetric) {
            addHandler(collidee, collider, new InverseCollisionHandler<C2, C1>(
                    handler));
        }
    }

    /**
     * Adds the collision interaction..
     *
     * @param collider The collider type.
     * @param collidee The collidee type.
     * @param handler The handler that handles the collision.
     */
    private void addHandler(final Class<? extends AbstractEntity> collider,
                            final Class<? extends AbstractEntity> collidee,
                            final CollisionHandler<?, ?> handler) {
        if (!handlers.containsKey(collider)) {
            handlers.put(
                    collider,
                    new HashMap<>());
        }

        final Map<Class<? extends AbstractEntity>, CollisionHandler<?, ?>
                > map = handlers.get(collider);
        map.put(collidee, handler);
    }

    /**
     * Handles the collision between two colliding parties, if a suitable
     * collision handler is listed.
     *
     * @param <C1> The collider type.
     * @param <C2> The collidee (AbstractEntity that was moved into) type.
     * @param collider The collider.
     * @param collidee The collidee.
     */
    public <C1 extends AbstractEntity, C2 extends AbstractEntity> void
    collide(final C1 collider, final C2 collidee) {
        final Class<? extends AbstractEntity> colliderKey
                = getMostSpecificClass(handlers, collider.getClass());
        if (colliderKey == null) {
            return;
        }

        final Map<Class<? extends AbstractEntity>, CollisionHandler<?, ?>> map
                = handlers.get(colliderKey);
        final Class<? extends AbstractEntity> collideeKey
                = getMostSpecificClass(map, collidee.getClass());
        if (collideeKey == null) {
            return;
        }

        final CollisionHandler<C1, C2> collisionHandler
                = (CollisionHandler<C1, C2>) map.get(collideeKey);
        if (collisionHandler == null) {
            return;
        }

        collisionHandler.handleCollision(collider, collidee);
    }

    /**
     * Figures out the most specific class that is listed in the map. I.e. if A
     * extends B and B is listed while requesting A, then B will be returned.
     *
     * @param map The map with the key collection to find a matching class in.
     * @param key The class to search the most suitable key for.
     * @return The most specific class from the key collection.
     */
    private Class<? extends AbstractEntity> getMostSpecificClass(
            final Map<Class<? extends AbstractEntity>, ?> map,
            final Class<? extends AbstractEntity> key) {
        final List<Class<? extends AbstractEntity>> collideeInheritance
                = getInheritance(key);
        for (final Class<? extends AbstractEntity> pointer
                : collideeInheritance) {
            if (map.containsKey(pointer)) {
                return pointer;
            }
        }
        return null;
    }

    /**
     * Returns a list of all classes and interfaces the class inherits.
     *
     * @param clazz The class to create a list of super classes and
     *              interfaces for.
     * @return A list of all classes and interfaces the class inherits.
     */         //found.add((Class<? extends AbstractEntity>) is unchecked,
                //thats what the SuppressWarnings is for.
    @SuppressWarnings("unchecked")
    private List<Class<? extends AbstractEntity>> getInheritance(
            final Class<? extends AbstractEntity> clazz) {
        final List<Class<? extends AbstractEntity>> found = new ArrayList<>();
        found.add(clazz);

        int index = 0;
        while (found.size() > index) {
            final Class<?> current = found.get(index);
            final Class<?> superClass = current.getSuperclass();
            if (superClass != null
                    && AbstractEntity.class.isAssignableFrom(superClass)) {
                found.add((Class<? extends AbstractEntity>) superClass);
            }
            for (final Class<?> classInterface : current.getInterfaces()) {
                if (AbstractEntity.class.isAssignableFrom(classInterface)) {
                    found.add((Class<? extends AbstractEntity>) classInterface);
                }
            }
            index++;
        }

        return found;
    }

    /**
     * Handles the collision between two colliding parties.
     *
     * @author Mathias
     *
     * @param <C1> The collider type.
     * @param <C2> The collidee type.
     */
    public interface CollisionHandler<C1 extends AbstractEntity,
            C2 extends AbstractEntity> {

        /**
         * Handles the collision between two colliding parties.
         * @param collider The collider.
         * @param collidee The collidee.
         */
        void handleCollision(C1 collider, C2 collidee);
    }

    /**
     * An symmetrical copy of a collision hander.
     *
     * @author Mathias
     *
     * @param <C1> The collider type.
     * @param <C2> The collidee type.
     */
    private static class InverseCollisionHandler<C1 extends AbstractEntity,
            C2 extends AbstractEntity>
            implements CollisionHandler<C1, C2> {

        /**
         * The handler of this collision.
         */
        private final CollisionHandler<C2, C1> handler;

        /**
         * Creates a new collision handler.
         * @param hndlr The symmetric handler for this collision.
         */
        InverseCollisionHandler(final CollisionHandler<C2, C1> hndlr) {
            this.handler = hndlr;
        }

        /**
         * Handles this collision by flipping the collider and collidee, making
         * it compatible with the initial collision.
         */
        @Override
        public void handleCollision(final C1 collider, final C2 collidee) {
            handler.handleCollision(collidee, collider);
        }
    }

}

