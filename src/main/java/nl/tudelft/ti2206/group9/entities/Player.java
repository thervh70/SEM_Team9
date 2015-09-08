package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.Main;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Direction;
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
	
	/** Gravity. This is added to the vertical speed of the Player each tick. */
	public static final double GRAVITY = 0.005;
	/** Jump speed. This is the initial vertical speed of the Player on jump. */
	public static final double JUMPSPEED = 0.12;
	
	/** Indicates whether the Player is alive or not. */
	private boolean alive;
	/** Indicates whether the Player is jumping or not. */
	private boolean jumping;
	/** Vertical speed (y-direction) of the Player. */
	private double vspeed;
	
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
	public Player(Point3D center) {
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
	
	/** Make the player jump (in the y-direction). */
	private void jump() {
		if (!jumping) {
			vspeed = JUMPSPEED;
			jumping = true;
		}
	}
	
	/** Make the player slide. */
	private void slide() { }

	/** Is executed each step. This is done in Track. */
	public void step() {
		Point3D pos = getCenter();
		if (jumping) {
			vspeed -= GRAVITY;
		}
		getCenter().addY(vspeed);

		Point3D bottom = new Point3D(pos);
		double bottomToFloor = getSize().getY() / 2;
		bottom.addY(bottomToFloor);
		if (pos.getY() < bottomToFloor) {
			jumping = false;
			vspeed = 0;
			pos.setY(bottomToFloor);
		}
	}

    /**
     * Decide which move methods should be called when keyboard input is
     * detected.
     * @param direction Left/Right/Jump/Slide
     */
    public void move(Direction direction) {
        switch (direction) {
            case LEFT: 	changeLane(-1);	break;
            case RIGHT:	changeLane(1);	break;
            case JUMP:	jump();			break;
            case SLIDE:	slide();		break;
            default:	break;
        }
    }

    /**
     * Change the lane the player is currently at. The center of the player
     * is capped between the edges of the track (currently -1.5 and +1.5).
     * @param dir amount of units to move.
     */
    private void changeLane(double dir) {
        Point3D newCenter = new Point3D(getCenter());
        newCenter.addX(dir);
        if (newCenter.getX() <= Main.TRACKWIDTH / 2
        		&& newCenter.getX() >= -Main.TRACKWIDTH / 2) {
			setCenter(newCenter);
		}
    }
}
