package nl.tudelft.ti2206.group9.level;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;
import nl.tudelft.ti2206.group9.level.entity.AbstractObstacle;
import nl.tudelft.ti2206.group9.level.entity.AbstractPickup;
import nl.tudelft.ti2206.group9.level.entity.AbstractPowerup;
import nl.tudelft.ti2206.group9.level.entity.Log;
import nl.tudelft.ti2206.group9.level.entity.Player;
import nl.tudelft.ti2206.group9.level.entity.PowerupDestroy;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;

/**
 * Add all the collision handlers to a CollisionMap.
 *
 * @author Mathias
 */
public class CollisionHandler {

    /**
     * The CollisionMap were the collions will be stored.
     */
    private final CollisionMap collisions = defaultCollisions();

    /**
     * Delegate the collision method to CollisionMap.collide(collider,collidee).
     *
     * @param collider The collider
     * @param collidee The collidee the collider collides with
     */
    public void collide(final AbstractEntity collider,
                        final AbstractEntity collidee) {
        collisions.collide(collider, collidee);
    }

    /**
     * Add all collisions to the CollisionMap.
     *
     * @return CollisionMap that contains all the collisions
     */
    public CollisionMap defaultCollisions() {
        final CollisionMap collisionMap = new CollisionMap();
        collisionMap.onCollision(Player.class, AbstractObstacle.class,
                (collider, collidee) -> {
                    if (!AbstractPowerup.isActive(PowerupInvulnerable.class)) {
                        collider.die();
                        notifyCollide(AbstractObstacle.class);
                    }
                });
        collisionMap.onCollision(Player.class, Log.class,
                (collider, collidee) -> {
                    if (AbstractPowerup.isActive(PowerupDestroy.class)) {
                        collidee.selfDestruct();
                        notifyCollide(Log.class);
                        State.addScore(Log.VALUE);
                    } else if (!AbstractPowerup.isActive(
                            PowerupInvulnerable.class)) {
                        collider.die();
                        notifyCollide(AbstractObstacle.class);
                    }
                });
        collisionMap.onCollision(Player.class, AbstractPickup.class,
                (collider, collidee) -> {
                    collidee.doAction();
                    collidee.selfDestruct();
                    notifyCollide(collidee.getClass());
                });
        return collisionMap;
    }

    /** Notifies the GameObservable of Collision.
     *  @param c The class that the Player collided with. */
    private void notifyCollide(final Class<? extends AbstractEntity> c) {
        OBSERVABLE.notify(Category.PLAYER, GameObserver.Player.COLLISION,
                c.getSimpleName());
    }

}
