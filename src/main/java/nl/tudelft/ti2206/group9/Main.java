package nl.tudelft.ti2206.group9;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;

/**
 * @author Maarten
 *
 */
public final class Main {
	
	public static final int RENDERDIST = 50;

	private Main() { }

	/**
	 * @param args does nothing.
	 * @throws InterruptedException 
	 */
	public static void main(String... args) throws InterruptedException {
		State.resetAll();
		InternalTicker.start();
	}
	
	/**
	 * Draws the track to the console. (Standard out)
	 */
	public static void drawTrack() {
		System.out.println("\n\n\n\n\n\n\n\n" + trackRender()
			+ " score: " + State.getScore());
	}

	private static String trackRender() {
		char[] track = new char[RENDERDIST];
		for (int i = 0; i < RENDERDIST; i++) {
			track[i] = ' ';
		}
		for (AbstractEntity entity : State.getTrack().getEntities()) {
			if (entity.getCenter().getX() >= RENDERDIST
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
		return new String(track);
	}

}
