package nl.tudelft.ti2206.group9.level.entity;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Superclass of all powerups. Handles the countdowns and cheat booleans of all
 * subclasses.
 * @author Maarten
 */
public abstract class AbstractPowerup extends AbstractPickup {

    /** The default size for this Powerup. */
    public static final Point3D SIZE = new Point3D(0.5, 0.5, 0.5);
    /** The default time this Powerup should last. */
    public static final int SECONDS = 10;

    /** The amount of ticks that the countdowns still last. */
    private static Map<Class<? extends AbstractPowerup>, Integer> countdown =
            new ConcurrentHashMap<>();

    /** Cheat booleans. */
    private static Map<Class<? extends AbstractPowerup>, Boolean> cheat =
            new ConcurrentHashMap<>();

    /**
     * Default constructor.
     * @param cent the center of this Powerup.
     * @param decorating the Pickup that this Powerup is decorating.
     */
    public AbstractPowerup(final Point3D cent,
            final AbstractPickup decorating) {
        super(cent, SIZE, decorating);
        initClass(getClass());
    }

    /**
     * Default constructor, decorating nothing.
     * @param cent the center of this Powerup.
     */
    public AbstractPowerup(final Point3D cent) {
        this(cent, null);
    }

    /**
     * Is called every step in Track.
     */
    public static void step() {
        for (final Entry<Class<? extends AbstractPowerup>, Boolean> e
                : cheat.entrySet()) {
            if (!e.getValue() && countdown.get(e.getKey()) > 0) {
                countdown.put(e.getKey(), countdown.get(e.getKey()) - 1);
                if (countdown.get(e.getKey()) == 0) {
                    OBSERVABLE.notify(
                            GameObserver.Category.PLAYER,
                            GameObserver.Player.POWERUPOVER,
                            e.getKey().getSimpleName());
                }
            }
        }
    }

    /** @param c The class to check activeness for.
     *  @return Whether this Powerup is active. */
    public static boolean isActive(final Class<? extends AbstractPowerup> c) {
        initClass(c);
        return countdown.get(c) != 0 || cheat.get(c);
    }
    /** @return Whether this Powerup is active. */
    public final boolean isActive() {
        return isActive(getClass());
    }

    /** Activates this Powerup foreveerrrrr.
     *  @param c The class to set cheat switch for.
     *  @param enable whether the cheat should be enabled */
    public static void setCheat(final Class<? extends AbstractPowerup> c,
            final boolean enable) {
        cheat.put(c, enable);
    }
    /** Activates this Powerup foreveerrrrr.
     *  @param enable whether the cheat should be enabled */
    public final void setCheat(final boolean enable) {
        setCheat(getClass(), enable);
    }

    /**
     * Reset the counters.
     */
    public static void resetCounters() {
        for (final Class<? extends AbstractPowerup> c : countdown.keySet()) {
            countdown.put(c, 0);
        }
    }

    /** @param c The class to get the seconds left for.
     *  @return the amount of seconds until this Powerup's effect stops. */
    public static double getSecondsLeft(
            final Class<? extends AbstractPowerup> c) {
        initClass(c);
        return (double) countdown.get(c) / (double) InternalTicker.FPS;
    }
    /** @return the amount of seconds until this Powerup's effect stops. */
    public final double getSecondsLeft() {
        return getSecondsLeft(getClass());
    }

    @Override
    protected Action thisAction() {
        return () -> countdown.put(getClass(), SECONDS * InternalTicker.FPS);
    }

    /**
     * Initializes the class in the two maps if this has not been done already.
     * @param c The class to initialize for.
     */
    private static void initClass(final Class<? extends AbstractPowerup> c) {
        if (countdown.get(c) == null) {
            countdown.put(c, 0);
            cheat.put(c, false);
        }
    }

}
