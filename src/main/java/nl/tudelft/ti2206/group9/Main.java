/**
 * 
 */
package nl.tudelft.ti2206.group9;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.MyKeyListener;
import nl.tudelft.ti2206.group9.util.Point3D;

import java.awt.event.KeyEvent;

/**
 * @author Maarten, Mathias
 *
 */
public final class Main {

	public static final int TRACKLENGTH = 50;
	public static final int TRACKWIDTH = 3;
	public static final int TICK = 100;
	public static final double COINCHANCE = 0.07;
	public static final double OBSTACLECHANCE = 0.07;

	private static char[][] track;

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
				State.getTrack().addEntity(new Coin(new Point3D(TRACKLENGTH, 0, 0)));
				State.getTrack().addEntity(new Coin(new Point3D(TRACKLENGTH, 1, 0)));
				State.getTrack().addEntity(new Coin(new Point3D(TRACKLENGTH, 2, 0)));
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
			String trackString = "\n\n\n\n\n\n\n\n";
            for (int i = 0; i < TRACKWIDTH; i++) {
                for (int j = 0; j < TRACKLENGTH; j++) {
                    trackString += track[j][i];
                }
                trackString += "\n";
            }
            trackString += " score: " + State.getScore();
			System.out.println(trackString);
		}

	}

	private static void trackRender() {
		track = new char[TRACKLENGTH][TRACKWIDTH];
		for (int i = 0; i < TRACKLENGTH; i++) {
			for (int j = 0; j < TRACKWIDTH; j++) {
				track[i][j] = '-';
			}
		}
		for (AbstractEntity entity : State.getTrack().getEntities()) {
			if (entity.getCenter().getX() >= TRACKLENGTH
			 || entity.getCenter().getX() < 0) {
				continue;
			}
			if (entity instanceof Player) {
				track[(int) entity.getCenter().getX()][(int) entity.getCenter().getY()] = 'P';
			}
			if (entity instanceof Coin) {
				track[(int) entity.getCenter().getX()][(int) entity.getCenter().getY()] = 'o';
			}
			if (entity instanceof Obstacle) {
				track[(int) entity.getCenter().getX()][(int) entity.getCenter().getY()] = '#';
			}
		}
	}

    protected void addKeys() {
        final Player player = (Player) State.getTrack().getEntities().get(0);
        MyKeyListener.addKey(KeyEvent.VK_UP, new Action() {

            @Override
            public void doAction() {
                player.move(Direction.JUMP);
            }
        });
        MyKeyListener.addKey(KeyEvent.VK_DOWN, new Action() {

            @Override
            public void doAction() {
                player.move(Direction.SLIDE);
            }
        });
        MyKeyListener.addKey(KeyEvent.VK_LEFT, new Action() {

            @Override
            public void doAction() {
                player.move(Direction.LEFT);
            }
        });
        MyKeyListener.addKey(KeyEvent.VK_RIGHT, new Action() {

            @Override
            public void doAction() {
                player.move(Direction.RIGHT);
            }
        });

    }
}
