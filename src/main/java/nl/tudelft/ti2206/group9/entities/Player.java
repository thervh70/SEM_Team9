package nl.tudelft.ti2206.group9.entities;

import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * Player entity that is controllable by the user.
 * @author Maarten
 */
public class Player extends AbstractEntity {

	public Player() { super(new Point3D(0, 1, 0.9), new Point3D(0.8, 0.8, 1.8)); }
	/**
	 * Constructs a new Player at the "center" of the game.
	 */
	public Player(Point3D center) {
		super(center, new Point3D(0.8, 0.8, 1.8));
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
	}

    /**
     * Decide which move methods should be called when keyboard input is detected.
     * @param direction
     */
    public void move(Direction direction) {
        switch(direction) {
//            case JUMP: jump(); break;
//            case SLIDE: slide(); break;
            case LEFT: changeLane(-1); break;
            case RIGHT: changeLane(1); break;
        }
    }

    /**
     * Change the lane the player is currently at.
     * @param dir
     *      -1: Move one lane left.
     *      1: Move one lane right.
     */
    private void changeLane(int dir) {
        Point3D currCenter = this.getCenter();
        Point3D newCenter = new Point3D(currCenter.getX(), currCenter.getY() + dir, currCenter.getZ());
        if(newCenter.getY() <= 2 & newCenter.getY() >= 0)
            this.setCenter(newCenter);
    }
}
