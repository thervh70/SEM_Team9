/**
 * 
 */
package nl.tudelft.ti2206.group9;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Point3D;

/**
 * @author Maarten
 *
 */
public final class Main {

	public static final int TRACKLENGTH = 50;
	public static final int TICK = 100;
	public static final double COINCHANCE = 0.07;
	public static final double OBSTACLECHANCE = 0.07;

	private static char[] track;

	private Main() { }

	/**
	 * @param args does nothing.
	 * @throws InterruptedException 
	 */
	public static void main(String... args) throws InterruptedException {
		State.resetAll();
		State.getTrack().addEntity(new Coin(new Point3D(TRACKLENGTH, 0, 0)));

		while (true) {
			Thread.sleep(TICK);
			if (Math.random() < COINCHANCE) {
				State.getTrack().addEntity(
						new Coin(new Point3D(TRACKLENGTH, 0, 0)));
			} else if (Math.random() < OBSTACLECHANCE) {
				State.getTrack().addEntity(new Obstacle(
						new Point3D(TRACKLENGTH, 0, 0),
						Point3D.UNITCUBE));
			}
			State.getTrack().moveTrack(1);
			if (!State.getTrack().getPlayer().isAlive()) {
				System.out.println("Ghagha, you ish ded.");
				break;
			}
			trackRender();
			System.out.println(new String(track)
			+ " score: " + State.getScore()
			+ " coins: " + State.getCoins() 
			+ " distance: " + State.getDistance() + "\n\n\n\n\n\n\n\n");
		}

	}

	private static void trackRender() {
		track = new char[TRACKLENGTH];
		for (int i = 0; i < TRACKLENGTH; i++) {
			track[i] = ' ';
		}
		for (AbstractEntity entity : State.getTrack().getEntities()) {
			if (entity.getCenter().getX() >= TRACKLENGTH
			 || entity.getCenter().getX() < 0) {
				continue;
			}
			if (entity instanceof Player) {
				track[(int) entity.getCenter().getX()] = 'P';
			}
			if (entity instanceof Coin) {
				track[(int) entity.getCenter().getX()] = 'o';
			}
			if (entity instanceof Obstacle) {
				track[(int) entity.getCenter().getX()] = '#';
			}
		}
	}

}
