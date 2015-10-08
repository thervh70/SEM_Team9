package nl.tudelft.ti2206.group9.entities;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Player entity that is controllable by the user.
 * @author Maarten
 */
public class Player extends AbstractEntity {

	/** Height of the Player's bounding box. */
	public static final double HEIGHT = 1.8;
	/** Width of the Player's bounding box. */
	public static final double WIDTH = 0.8;
	/** Depth of the Players bounding box */
	public static final double DEPTH = 0.1;

	/** Gravity. This is added to the vertical speed
	 * of the Player each tick.
	 */
	public static final double GRAVITY = 0.025;
	/** Jump speed. This is the initial vertical speed
	 * of the Player on jump.
	 */
	public static final double JUMP_SPEED = 0.4;

	/** Slide length in ticks. */
	public static final double SLIDE_LENGTH = 40;
	/** Lowest point in slide. */
	public static final double SLIDE_MIN_HEIGHT = HEIGHT / 2;

	/** Indicates whether the Player is alive or not. */
	private boolean alive;
	/** Indicates whether the Player is jumping or not. */
	private boolean jumping;
	/** Vertical speed (y-direction) of the Player. */
	private double vspeed;
	/** Lane where the Player is currently moving to (smoothly). */
	private double moveLane;
	/** Horizontal speed (x-direction) of the Player. */
	private double hspeed;
	/** Indicates whether the Player is sliding or not. */
	private boolean sliding;
	/** Rate at which the Player's size in-/decreases. */
	private double slideSpeed;
	/** Whether the player is invincible. */
	private boolean invincible;

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
		super(center, new Point3D(WIDTH, HEIGHT, DEPTH));
	}

	/** Lets the player die. */
	public final void die() {
		alive = false;
	}

	/** Lets the player live. */
	public final void respawn() {
		alive = true;
	}

	/** @return whether the player is alive. */
	public final boolean isAlive() {
		return alive;
	}

	/** @return whether the player is invincible */
	public final boolean isInvincible() {
		return invincible;
	}

	/** @param set whether the Player should be invincible */
	public void setInvincible(final boolean set) {
		invincible = set;
	}

	/**
	 * When colliding with a coin, Coin.VALUE is added to score,
	 * and amount of coins is increased by one.
	 * @param collidee Entity that this Player collides with.
	 */
	@Override
	public final void collision(final AbstractEntity collidee) {
		if (collidee instanceof Coin) {
			OBSERVABLE.notify(Category.PLAYER,
					GameObserver.Player.COLLISION, Coin.class.getSimpleName());
			State.addScore(Coin.VALUE);
			State.addCoins(1);
		}
		if (collidee instanceof AbstractObstacle) {
			OBSERVABLE.notify(
					Category.PLAYER, GameObserver.Player.COLLISION,
					AbstractObstacle.class.getSimpleName());
			if (!isInvincible()) {
				die();
			}
		}
	}

	/**
	 * Change the lane the player is currently at. The center of the player
	 * is capped between the edges of the track (currently -1.5 and +1.5).
	 * @param dir amount of units to move.
	 */
	private void changeLane(final double dir) { //NOPMD - Travis says "unused"
		if (moveLane + dir >= -Track.WIDTH / 2
				&& moveLane + dir <= Track.WIDTH / 2) {
			OBSERVABLE.notify(Category.PLAYER,
					GameObserver.Player.START_MOVE, (int) moveLane);
			moveLane += dir;
		}
	}

	/** Is executed each step in {@link #step()}.
	 * Keeps the Player moving.
	 */
	private void changeLaneStep() {
		final double dist = moveLane - getCenter().getX();
		final double delta = 0.02; // higher means faster acceleration
		final double slow = 5; 	// higher means lower terminal speed
		if (Math.abs(dist) < delta && hspeed != 0) {
			getCenter().setX(moveLane);
			hspeed = 0;
			OBSERVABLE.notify(Category.PLAYER,
					GameObserver.Player.STOP_MOVE, (int) moveLane);
		} else {
			if (Math.abs(hspeed) < Math.abs(dist) / slow) {
				hspeed += delta * Math.signum(dist);
			} else {
				hspeed = dist / slow;
			}
		}
		getCenter().addX(hspeed);
	}

	/** Used for testability only.
	 * @return The lane where the Player is currently moving to
	 */
	int getMoveLane() {
		return (int) moveLane;
	}

	/** Make the player jump (in the y-direction). */
	private void jump() {
		if (!jumping && !sliding) {
			vspeed = JUMP_SPEED;
			jumping = true;
			OBSERVABLE.notify(Category.PLAYER, GameObserver.Player.JUMP);
		}
	}

	/** Is executed each step in {@link #step()}.
	 * Keeps the Player jumping.
	 */
	private void jumpStep() {
		final Point3D pos = getCenter();
		if (jumping) {
			vspeed -= GRAVITY;
		}
		pos.addY(vspeed);

		final double bottomToFloor = getSize().getY() / 2;
		if (pos.getY() < bottomToFloor) {
			jumping = false;
			vspeed = 0;
			pos.setY(bottomToFloor);
		}
	}

	/** Make the player slide. */
	private void slide() {
		// Slide is done with a quadratic function
		// y = (max-min) {1/(ticks/2) (x - ticks/2)} ^ 2 + min
		// y' = 2 (max-min) 1/(ticks/2) (x - ticks/2) 1/(ticks/2)
		// y'' = 2 (max-min) 1/(ticks/2) 1/(ticks/2)
		// y'(0) = 2 (max-min) 1/(ticks/2) (-ticks/2) 1/(ticks/2)
		if (!jumping && !sliding) {
			slideSpeed = -1 * 2 * (HEIGHT - SLIDE_MIN_HEIGHT)
					/ (SLIDE_LENGTH / 2);
			sliding = true;
			OBSERVABLE.notify(Category.PLAYER, GameObserver.Player.SLIDE);
		}
	}

	/** Is executed each step in {@link #step()}.
	 * Keeps the Player sliding.
	 */
	private void slideStep() {
		if (sliding) {
			getSize().addY(slideSpeed);
			getSize().setZ(HEIGHT * DEPTH
					/ getSize().getY()); // volume = const
			getCenter().addY(slideSpeed / 2);

			slideSpeed += 2 * (HEIGHT - SLIDE_MIN_HEIGHT)
					/ (SLIDE_LENGTH / 2) / (SLIDE_LENGTH / 2);
		}
		if (getSize().getY() >= HEIGHT) {
			sliding = false;
			getSize().setY(HEIGHT);
		}
	}

	/**
	 * Decide which move methods should be called when keyboard input is
	 * detected.
	 * @param direction Left/Right/Jump/Slide
	 */
	public final void move(final Direction direction) {
		if (isAlive()) {
			switch (direction) {
			case LEFT:  changeLane(-1.0);	break;
			case RIGHT: changeLane(1.0);	break;
			case JUMP:  jump();				break;
			case SLIDE: slide();			break;
			default:	break;
			}
		}
	}

	/** Is executed each step. This is done in Track. */
	public final void step() {
		changeLaneStep();
		jumpStep();
		slideStep();
	}
}
