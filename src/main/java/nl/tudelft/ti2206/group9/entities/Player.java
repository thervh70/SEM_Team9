package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Player entity that is controllable by the user.
 * @author Maarten
 */
public class Player extends AbstractEntity {

	/** Indicates whether the player is alive or not. */
	private boolean alive;

	/** Height of the Player's bounding box. */
	public static final double HEIGHT = 1.8;
	/** Width of the Player's bounding box. */
	public static final double WIDTH = 0.8;

	/**
	 * Constructs a new Player at the "center" of the game.
	 */
	public Player() {
		this(new Point3D(0, HEIGHT / 2, 0));
	}

	/**
	 * Constructs a new Player at a user-defined center.
	 * @param center user-defined center.
	 */
	public Player(final Point3D center) {
		super(center, new Point3D(WIDTH, HEIGHT, WIDTH));
	}

	/** Lets the player die. */
	public void die() {
		alive = false;
	}

	/** Lets the player live. */
	public void respawn() {
		alive = true;
	}

	/** @return whether the player is alive. */
	public boolean isAlive() {
		return alive;
	}

	/**
     * When colliding with a coin, Coin.VALUE is added to score,
	 * and amount of coins is increased by one.
	 * @param collidee Entity that this Player collides with.
	 */
	@Override
	public void collision(final AbstractEntity collidee) {
		if (collidee instanceof Coin) {
			State.addScore(Coin.VALUE);
			State.addCoins(1);
		}

		if (collidee instanceof Obstacle) {
			die();
		}
	}

    /**
     * Decide which move methods should be called when keyboard input is
     * detected.
     * @param direction Left/Right/Jump/Slide
     */
    public void move(final Direction direction) {
        switch (direction) {
//            case JUMP: jump(); break;
//            case SLIDE: slide(); break;
            case LEFT: changeLane(-1); break;
            case RIGHT: changeLane(1); break;
            default: break;
        }
    }

    /**
     * Change the lane the player is currently at. The center of the player
     * is capped between the edges of the track (currently -1.5 and +1.5).
     * @param dir amount of units to move.
     */
    private void changeLane(final double dir) {
        Point3D newCenter = new Point3D(getCenter());
        newCenter.addX(dir);
        if (newCenter.getX() <= Track.WIDTH / 2
        		&& newCenter.getX() >= -Track.WIDTH / 2) {
			setCenter(newCenter);
		}
    }
}
