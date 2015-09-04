package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Player entity that is controllable by the user.
 * @author Maarten
 */
public class Player extends AbstractEntity {
	
	/** Gravity. This is added to the vertical speed of the Player each tick. */
	private static final double GRAVITY = 0.005;
	/** Jump speed. This is the initial vertical speed of the Player on jump. */
	private static final double JUMPSPEED = 0.12;
	
	/** Indicates whether the Player is alive or not. */
	private boolean alive;
	/** Indicates whether the Player is jumping or not. */
	private boolean jumping;
	/** Vertical speed (z-direction) of the Player. */
	private double vspeed;
	
	/**
	 * Constructs a new Player at the "center" of the game.
	 */
	public Player() {
		super(new Point3D(0, 0, 1), new Point3D(1, 1, 2));
		alive = true;
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
	
	/** Make the player jump (in the z-direction). */
	public void jump() {
		if (!jumping) {
			vspeed = JUMPSPEED;
			jumping = true;
		}
	}

	/** Is executed each step. This is done in Track. */
	public void step() {
		Point3D pos = getCenter();
		if (jumping) {
			vspeed -= GRAVITY;
		}
		getCenter().addZ(vspeed);

		Point3D bottom = new Point3D(pos);
		double bottomToFloor = getSize().getZ() / 2;
		bottom.addZ(bottomToFloor);
		if (pos.getZ() < bottomToFloor) {
			jumping = false;
			vspeed = 0;
			pos.setZ(bottomToFloor);
		}
	}

}
