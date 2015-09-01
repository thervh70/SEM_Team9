/**
 * 
 */
package nl.tudelft.ti2206.group9;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
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

	private static char[] track;

	private Main() { }

	/**
	 * @param args
	 * @throws IOException 
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
			}
			State.getTrack().moveTrack(1);
			trackRender();
			System.out.println("\n\n\n\n\n\n\n\n" + new String(track)
				+ " score: " + State.getScore());
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
		}
	}

}
