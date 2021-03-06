package nl.tudelft.ti2206.group9.level;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import nl.tudelft.ti2206.group9.gui.scene.GameScene;
import nl.tudelft.ti2206.group9.level.TrackPart.Node;
import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;
import nl.tudelft.ti2206.group9.level.entity.AbstractPickup;
import nl.tudelft.ti2206.group9.level.entity.AbstractPowerup;
import nl.tudelft.ti2206.group9.level.entity.Coin;
import nl.tudelft.ti2206.group9.level.entity.Fence;
import nl.tudelft.ti2206.group9.level.entity.Log;
import nl.tudelft.ti2206.group9.level.entity.Pillar;
import nl.tudelft.ti2206.group9.level.entity.Player;
import nl.tudelft.ti2206.group9.level.entity.PowerupCoinMagnet;
import nl.tudelft.ti2206.group9.level.entity.PowerupDestroy;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;
import nl.tudelft.ti2206.group9.level.entity.PowerupSlowness;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList.Listener;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * This class holds all entities present in the game, such as Coins, a Player
 * and Obstacles.
 * @author Maarten, Mitchell
 *
 */
public class Track {

    /** Width of the track (amount of lanes). */
    public static final int WIDTH = 3;
    /** Length of the track. */
    public static final double LENGTH = 75;
    /** Standard modulus number for both modulo calculation. */
    public static final int MOD = 50;

    /** Amount of units the track should move per tick, initially. */
    static final double UNITS_PER_TICK_BASE = 0.4;
    /** Acceleration of the units per tick, per tick. */
    static final double UNITS_PER_TICK_ACCEL = 0.0001;
    /** Current distance moved by the track, reset every run. */
    private static double distance;
    /** Records the distance from the previous distance update. */
    private static int previousDistance;

    /** List of entities on the track. */
    private final ObservableLinkedList<AbstractEntity> entities;
    /** Index of the player entity in the entities list. */
    private int player;

    /** Random number generator for generating stuff on the track. */
    private Random random;
    /** List of all TrackParts the Track can consist of. */
    private List<TrackPart> trackParts;
    /**
     * CollisionMap that stores all collisions.
     */
    private CollisionHandler collisions = new CollisionHandler();
    /**
     * Map that contains all createEntityCommands.
     */
    private static Map<Class<? extends AbstractEntity>, CreateEntityCommand>
    createEntityMap = new ConcurrentHashMap<>();
    /**
     * The Single instance this class can have.
     */
    private static Track currentTrack = new Track();

    static {
        createEntityMap.put(Coin.class, Coin::new);
        createEntityMap.put(Log.class, Log::new);
        createEntityMap.put(Pillar.class, Pillar::new);
        createEntityMap.put(Fence.class, Fence::new);
        createEntityMap.put(AbstractPickup.class, p -> {
            final ArrayList<AbstractPickup> list = new ArrayList<>();
            list.add(new Coin(p));
            list.add(new PowerupInvulnerable(p));
            list.add(new PowerupSlowness(p));
            list.add(new PowerupDestroy(p));
            list.add(new PowerupCoinMagnet(p));
            return list.get((int) (Math.random() * list.size()));
        });
    }

    /** Length of the track that is already created. */
    private double trackLeft;

    /** Default constructor, new Random() is created as generator. */
    private Track() {
        this(new Random());
    }

    /**
     * Get the one true instance of Track.
     * @return the only instance of Track
     */
    public static Track getInstance() {
        return currentTrack;
    }

    /**
     * Reset the instance of track by making it a new Track.
     */
    public static void reset() {
        currentTrack = new Track();
    }

    /**
     * Constructor, in which one can specify the Random generator to use.
     * @param generator the Random generator to use for this Track.
     */
    public Track(final Random generator) {
        entities = new ObservableLinkedList<>();
        trackParts = new TrackParser().parseTrack();
        entities.add(new Player());
        player = 0;
        random = generator;
    }

    /**
     * Check whether the distance has been increased by 50 (or more).
     * This check is used for soundtrack speed increasing.
     */
    public static void distanceCheck() {
        final int currentDistance = modulo(Track.getDistance());
        if (currentDistance > previousDistance) {
            previousDistance = currentDistance;
            OBSERVABLE.notify(GameObserver.Category.PLAYER,
                    GameObserver.Player.DISTANCE_INCREASE, (int) getDistance());
        }
    }

    /**
     * Update the current distance every {@link #MOD} moves or points increase.
     * @param amount number of (distance or points)
     * @return updated amount
     */
    public static int modulo(final double amount) {
        return (int) (Math.floor(amount / MOD) * MOD);
    }

    /** Moves all coins, when CoinMagnet is active. */
    private void moveCoinMagnet() {
        if (!AbstractPowerup.isActive(PowerupCoinMagnet.class)) {
            return;
        }
        final double dist = 10;
        synchronized (this) {
            for (final AbstractEntity e : entities) {
                if (!(e instanceof Coin)) {
                    continue;
                }
                final Point3D c = e.getCenter(); // reference, so addX works
                if (c.getZ() > dist) {
                    continue;
                }
                final double diffX = getPlayer().getCenter().getX() - c.getX();
                c.addX((diffX - diffX * e.getCenter().getZ() / dist) / dist);
            }
        }
    }

    /**
     * Moves the track towards the player (thus making the player run over
     * the track, like a treadmill).
     * @param dist amount of units to move the track
     */
    public final void moveTrack(final double dist) {
        synchronized (this) {
            for (int i = 0; i < entities.size(); i++) {
                if (i == player) {
                    continue;
                }
                moveEntity(entities.get(i), -dist);
            }
            int index = 0;
            if (index == player) {
                index++;
            }
            while (true) {
                if (index >= entities.size()) {
                    break;
                }
                if (entities.get(index).getCenter().getZ()
                        > GameScene.CAMERA_TRANS.getZ()) {
                    break;
                }
                entities.remove(index);
            }
        }
    }

    /**
     * Make sure the collisions are checked over the interval that the entities
     * are moved.
     * @param entity Entity that the collision is checked with.
     * @param dist The distance that the second entity has moved.
     */
    private void moveEntity(final AbstractEntity entity,
            final double dist) {
        final double oldZ = entity.getCenter().getZ();
        final double diffZ =
                (getPlayer().getSize().getZ() + entity.getSize().getZ())
                * Math.signum(dist);
        for (double i = 0; Math.abs(i) < Math.abs(dist); i += diffZ) {
            entity.getCenter().addZ(diffZ);
            getPlayer().checkCollision(entity);
        }
        entity.getCenter().setZ(oldZ + dist);
    }

    /**
     * Adds entity to the list of entities.
     * @param entity entity to add
     * @return this Track, allowing for chaining.
     */
    public final Track addEntity(final AbstractEntity entity) {
        synchronized (this) {
            entities.add(entity);
        }
        return this;
    }

    /**
     * Removes entity from the list of entities.
     * @param entity entity to remove
     * @return this Track, allowing for chaining.
     */
    public final Track removeEntity(final AbstractEntity entity) {
        synchronized (this) {
            entity.getCenter().setZ(GameScene.CAMERA_TRANS.getZ() - 1);
        }
        return this;
    }

    /**
     * @return the Player
     */
    public final Player getPlayer() {
        return (Player) entities.get(player);
    }

    /**
     * @return the entities
     */
    public final List<AbstractEntity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /** @param listener The listener to add to the Observable entities list. */
    public final void addEntitiesListener(final Listener listener) {
        entities.addListener(listener);
    }

    /**
     * @param amount the amount to be added
     */
    public static void addDistance(final double amount) {
        distance += amount;
    }

    /**
     * @return the distance
     */
    public static double getDistance() {
        return distance;
    }

    /**
     * @param dist the distance to set
     */
    public static void setDistance(final double dist) {
        Track.distance = dist;
    }

    /**
     * @return the previousDistance
     */
    public static int getPreviousDistance() {
        return previousDistance;
    }

    /**
     * @param newPrevDist the previousDistance to set
     */
    public static void setPreviousDistance(final int newPrevDist) {
        previousDistance = newPrevDist;
    }
    /**
     * Get the number of Units that pass by per tick at this moment.
     * @return double Units per Tick
     */
    public static double getUnitsPerTick() {
        final double div = Math.pow(UNITS_PER_TICK_ACCEL, -1) / 2
                * UNITS_PER_TICK_BASE * UNITS_PER_TICK_BASE;
        double upt = UNITS_PER_TICK_BASE * Math.sqrt(getDistance() / div + 1);
        if (AbstractPowerup.isActive(PowerupSlowness.class)) {
            upt /= 2;
        }
        return upt;
    }

    /**
     * This method should be called each ticks. It generates new coins and
     * obstacles. Also moves the track forward (thus making the Player run).
     */
    public final void step() {
        synchronized (this) {
            if (trackLeft > 0) {
                trackLeft -= getUnitsPerTick();
            } else {
                final int rand = random.nextInt(trackParts.size());
                final TrackPart part = trackParts.get(rand);
                addTrackPartToTrack(part);
            }
        }
        getPlayer().step();
        PowerupInvulnerable.step();

        distance += getUnitsPerTick();
        State.addScore(getUnitsPerTick());
        moveTrack(getUnitsPerTick());
        moveCoinMagnet();
    }

    /**
     * Adds all entities from the TrackPart to the Track.
     * @param part the TrackPart to be added
     */
    private void addTrackPartToTrack(final TrackPart part) {
        AbstractEntity add;
        for (final Node entity : part.getEntities()) {
            final Point3D center = new Point3D(entity.getCenter());
            center.addZ(LENGTH);
            add = createEntityMap.get(entity.getType()).createEntity(center);
            addEntity(add);
        }
        trackLeft = part.getLength();
    }

    /**
     * Get the CollisionHandler with all the collisions.
     * @return CollisionHandler that contains all collisions and their handlers.
     */
    public CollisionHandler getCollisions() {
        return collisions;
    }

    /**
     * Set the collision.
     *
     * @param collisionHandler the new CollisionHandler.
     */
    public void setCollisions(final CollisionHandler collisionHandler) {
        collisions = collisionHandler;
    }

    /**
     * Interface that is a Command, used to render the Track.
     *
     * @author Mathias
     */
    public interface CreateEntityCommand {
        /**
         * Create an Entity upon call.
         *
         * @param center the cente of the Entity
         * @return the created Entity
         */
        AbstractEntity createEntity(Point3D center);
    }

}
