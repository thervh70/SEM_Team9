package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;
import nl.tudelft.ti2206.group9.level.entity.AbstractObstacle;
import nl.tudelft.ti2206.group9.level.entity.AbstractPickup;
import nl.tudelft.ti2206.group9.level.entity.Player;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;
import nl.tudelft.ti2206.group9.util.CollisionMap;
import nl.tudelft.ti2206.group9.util.GameObserver;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;

/**
 * Add all the collision handlers to a CollisionMap.
 * @author Mathias
 */
public class CollisionHandler {

    /** The CollisionMap were the collions will be stored. */
    private CollisionMap collisions = defaultCollisions();

    /**
     * Delegate the collision method to CollisionMap.collide(collider,collidee).
     * @param collider The collider
     * @param collidee The collidee the collider collides with
     */
    public void collide(final AbstractEntity collider,
                        final AbstractEntity collidee) {
        collisions.collide(collider, collidee);
    }

    /**
     * Add all collisions to the CollisionMap.
     * @return CollisionMap that contains all the collisions
     */
    public CollisionMap defaultCollisions() {
        final CollisionMap collisionMap = new CollisionMap();

        collisionMap.onCollision(Player.class, AbstractObstacle.class,
                (collider, collidee) -> {
                    if (!PowerupInvulnerable.isActive()) {
                        OBSERVABLE.notify(
                                GameObserver.Category.PLAYER,
                                GameObserver.Player.COLLISION,
                                AbstractObstacle.class.getSimpleName());
                        collider.die();
                    }
                });

        collisionMap.onCollision(Player.class, AbstractPickup.class,
                (collider, collidee) -> {
                    OBSERVABLE.notify(GameObserver.Category.PLAYER,
                            GameObserver.Player.COLLISION,
                            this.getClass().getSimpleName());
                    collidee.doAction();
                    collidee.selfDestruct();
                });

        return collisionMap;
    }

}
