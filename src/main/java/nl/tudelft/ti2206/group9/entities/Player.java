package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.State;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Player entity that is controllable by the user.
 * @author Maarten
 */
public class Player extends AbstractEntity {
	
	/**
	 * Constructs a new Player at the "center" of the game.
	 */
	public Player() {
		super(new Point3D(0, 0, 1), new Point3D(1, 1, 2));
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
		
	}

}
