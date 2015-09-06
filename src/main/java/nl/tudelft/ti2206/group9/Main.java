package nl.tudelft.ti2206.group9;

import java.awt.event.KeyEvent;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.MyKeyListener;

/**
 * @author Maarten, Mathias
 *
 */
public final class Main {
	
	public static final int TRACKWIDTH = 3;
	
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
		char[][] track = new char[TRACKWIDTH][RENDERDIST];
		for (int i = 0; i < RENDERDIST; i++) {
			for (int j = 0; j < TRACKWIDTH; j++) {
				track[j][i] = '-';
			}
		}
		for (AbstractEntity entity : State.getTrack().getEntities()) {
			int x = (int) entity.getCenter().getX();
			int y = (int) entity.getCenter().getY();
			if (x >= RENDERDIST || x < 0) {
				continue;
			}
			if (entity instanceof Player) {
				track[y][x] = 'P';
			}
			if (entity instanceof Coin) {
				track[y][x] = 'o';
			}
			if (entity instanceof Obstacle) {
				track[y][x] = '#';
			}
		}
		return new String(track[0]) + "\n" + new String(track[1]) + "\n" 
			 + new String(track[2]);
	}

    protected void addKeys() {
        final Player player = (Player) State.getTrack().getEntities().get(0);
        MyKeyListener.addKey(KeyEvent.VK_UP, new Action() {
            public void doAction() {
                player.move(Direction.JUMP);
            }
        });
        MyKeyListener.addKey(KeyEvent.VK_DOWN, new Action() {
            public void doAction() {
                player.move(Direction.SLIDE);
            }
        });
        MyKeyListener.addKey(KeyEvent.VK_LEFT, new Action() {
            public void doAction() {
                player.move(Direction.LEFT);
            }
        });
        MyKeyListener.addKey(KeyEvent.VK_RIGHT, new Action() {
            public void doAction() {
                player.move(Direction.RIGHT);
            }
        });

    }
}
