package nl.tudelft.ti2206.group9.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.group9.gui.scene.GameScene;
import nl.tudelft.ti2206.group9.util.CollisionMap;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList;
import nl.tudelft.ti2206.group9.util.ObservableLinkedList.Listener;
import nl.tudelft.ti2206.group9.util.Point3D;
import nl.tudelft.ti2206.group9.level.TrackPart.Node;
import nl.tudelft.ti2206.group9.level.entity.AbstractEntity;
import nl.tudelft.ti2206.group9.level.entity.AbstractPickup;
import nl.tudelft.ti2206.group9.level.entity.Coin;
import nl.tudelft.ti2206.group9.level.entity.Fence;
import nl.tudelft.ti2206.group9.level.entity.Log;
import nl.tudelft.ti2206.group9.level.entity.Pillar;
import nl.tudelft.ti2206.group9.level.entity.Player;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;

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
    public static final double LENGTH = 100;

    /** Amount of units the track should move per tick, initially. */
    static final double UNITS_PER_TICK_BASE = 0.4;
    /** Acceleration of the units per tick, per tick. */
    static final double UNITS_PER_TICK_ACCEL = 0.0001;
    /** Current distance moved by the track, reset every run. */
    private static double distance;

    /** List of entities on the track. */
    private final ObservableLinkedList<AbstractEntity> entities;
    /** Index of the player entity in the entities list. */
    private int player;

    /** Random number generator for generating stuff on the track. */
    private Random random;
    /** List of all TrackParts the Track can consist of. */
    private List<TrackPart> trackParts;
    /** CollisionMap that stores all collisions. */
    private CrashMap collisions = new CrashMap();

    /** Length of the track that is already created. */
    private double trackLeft;

    /** Default constructor, new Random() is created as generator. */
    public Track() {
        this(new Random());
    }

    /**
     * Constructor, in which one can specify the Random generator to use.
     * @param generator the Random generator to use for this Track.
     */
    public Track(final Random generator) {
        entities = new ObservableLinkedList<AbstractEntity>();
        trackParts = new TrackParser().parseTrack();
        entities.add(new Player());
        player = 0;
        random = generator;
    }

    /**
     * Moves the track towards the player (thus making the player run over
     * the track, like a treadmill).
     * @param dist amount of units to move the track
     */
    @SuppressWarnings("restriction")
    public final void moveTrack(final double dist) {
        synchronized (this) {
            for (int i = 0; i < entities.size(); i++) {
                if (i == player) {
                    continue;
                }
                AbstractEntity entity = entities.get(i);
                moveEntity(entity, -dist);
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
    @SuppressWarnings("restriction")
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
     * @param listener
     *             The listener to remove from the Observable entities list.
     */
    public final void removeEntitiesListener(final Listener listener) {
        entities.removeListener(listener);
    }

    /**
     * @param amount the amount to be added
     */
    static final void addDistance(final double amount) {
        distance += amount;
    }

    /**
     * @return the distance
     */
    static final double getDistance() {
        return distance;
    }

    /**
     * @param dist the distance to set
     */
    static final void setDistance(final double dist) {
        Track.distance = dist;
    }

    /**
     * Get the number of Units that pass by per tick at this moment.
     * @return double Units per Tick
     */
    public static final double getUnitsPerTick() {
        final double div = Math.pow(UNITS_PER_TICK_ACCEL, -1) / 2
                * UNITS_PER_TICK_BASE * UNITS_PER_TICK_BASE;
        return UNITS_PER_TICK_BASE
                * Math.sqrt(State.getDistance() / div + 1);
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
    }

    /**
     * Adds all entities from the TrackPart to the Track.
     * @param part the TrackPart to be added
     */
    private void addTrackPartToTrack(final TrackPart part) {
        AbstractEntity add = null;
        for (final Node entity : part.getEntities()) {
            final Point3D center = new Point3D(entity.getCenter());
            center.addZ(LENGTH);
            if (entity.getType().equals(AbstractPickup.class)) {
                add = randomPowerup(center);
            } else if (entity.getType().equals(Coin.class)) {
                add = new Coin(center);
            } else if (entity.getType().equals(Fence.class)) {
                add = new Fence(center);
            } else if (entity.getType().equals(Log.class)) {
                add = new Log(center);
            } else if (entity.getType().equals(Pillar.class)) {
                add = new Pillar(center);
            }
            addEntity(add);
        }
        trackLeft = part.getLength();
    }

    /**
     * @param center the center of the new Powerup.
     * @return a random Powerup.
     */
    private AbstractPickup randomPowerup(final Point3D center) {
        final ArrayList<AbstractPickup> list = new ArrayList<AbstractPickup>();
        list.add(new Coin(center));
        list.add(new PowerupInvulnerable(center));
        return list.get((int) (Math.random() * list.size()));
    }

    /**
     * Get the CrashMap with all the collisions.
     * @return CrashMap that contains all collisions and their handlers.
     */
    public CrashMap getCollisions() {
        return collisions;
    }

    /**
     * Set the collision.
     * @param crashMap the new CrashMap.
     */
    public void setCollisions(CrashMap crashMap) {
        collisions = crashMap;
    }
}
