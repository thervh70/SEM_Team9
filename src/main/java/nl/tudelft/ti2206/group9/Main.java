/**
 * 
 */
package nl.tudelft.ti2206.group9;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	private static Mixer mixer;
	private static Clip clip;

	private Main() { }
	
	/**
	 * @param args does nothing.
	 * @throws InterruptedException 
	 */
	public static void main(String... args) throws InterruptedException {
        State.resetAll();
        State.getTrack().addEntity(new Coin(new Point3D(TRACKLENGTH, 0, 0)));
		initialiseTune("audio/kuikentje.wav");
		clip.start();		

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
			int distance = 1;
			State.getTrack().moveTrack(distance);
			State.addDistance(distance);
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
	
	/**
	 * Gets the audio file and prepares it for streaming.
	 */
	private static void initialiseTune(String path) {
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		
		try { 
			clip = (Clip) mixer.getLine(dataInfo); 
			} catch (LineUnavailableException lue) { 
				lue.printStackTrace(); 
			}
		
		try	{
			URL soundUrl = Main.class.getResource(path);
			AudioInputStream audioStream = 
						AudioSystem.getAudioInputStream(soundUrl);
			clip.open(audioStream);
		} catch (LineUnavailableException lue) {
			lue.printStackTrace(); 
		} catch (UnsupportedAudioFileException uafe) {
			uafe.printStackTrace(); 
		} catch (IOException ioe) { 
			ioe.printStackTrace(); 
		}
	}

}
