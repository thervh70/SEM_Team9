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

import java.awt.event.KeyEvent;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Obstacle;
import nl.tudelft.ti2206.group9.entities.Player;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.Action;
import nl.tudelft.ti2206.group9.util.Direction;
import nl.tudelft.ti2206.group9.util.KeyMap;

/**
 * @author Maarten, Mathias
 *
 */
public final class Main {

	private static Mixer mixer;
	private static Clip clip;
	
	public static final double TRACKWIDTH = 3;
	
	public static final int RENDERDIST = 50;

	private Main() { }
	
	/**
	 * @param args does nothing.
	 * @throws InterruptedException 
	 */
	public static void main(String... args) throws InterruptedException {
		initialiseTune("audio/kuikentje.wav");
		clip.start();		

		State.resetAll();
		Main.addKeys();
//		window.addKeyListener(new KeyMap());
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
		char[][] track = new char[(int) TRACKWIDTH][RENDERDIST];
		for (int i = 0; i < RENDERDIST; i++) {
			for (int j = 0; j < TRACKWIDTH; j++) {
				track[j][i] = '-';
			}
		}
		for (AbstractEntity entity : State.getTrack().getEntities()) {
			int x = (int) entity.getCenter().getZ();	 //Length of track
			int y = (int) entity.getCenter().getX() + 1; //Width of track
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

    private static void addKeys() {
        final Player player = (Player) State.getTrack().getEntities().get(0);
        KeyMap.addKey(KeyEvent.VK_UP, new Action() {
            public void doAction() {
                player.move(Direction.JUMP);
            }
        });
        KeyMap.addKey(KeyEvent.VK_DOWN, new Action() {
            public void doAction() {
                player.move(Direction.SLIDE);
            }
        });
        KeyMap.addKey(KeyEvent.VK_LEFT, new Action() {
            public void doAction() {
                player.move(Direction.LEFT);
            }
        });
        KeyMap.addKey(KeyEvent.VK_RIGHT, new Action() {
            public void doAction() {
                player.move(Direction.RIGHT);
            }
        });

    }
}
