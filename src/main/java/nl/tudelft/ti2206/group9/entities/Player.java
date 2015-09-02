package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Player entity that is controllable by the user.
 * @author Maarten
 */
public class Player extends AbstractEntity {
	
	private boolean alive;
	
	/**
	 * Constructs a new Player at the "center" of the game.
	 */
	public Player() {
		super(new Point3D(0, 0, 1), new Point3D(1, 1, 2));
		alive = true;
	}
	
	/** Lets the player die */
	public void die() {
		alive = false;
	}
	
	/** Lets the player live */
	public void respawn() {
		alive = true;
	}
	
	/** @return whether the player is alive */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * When colliding with a coin, Coin.VALUE is added to score, 
	 * and amount of coins is increased by one.
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

}
