package nl.tudelft.ti2206.group9.util;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Add this Logger to GameObservable, and it will log every single action in
 * the game.
 * @author Maarten
 */
public class Logger implements GameObserver {

	public static final Map<Specific, String> STRINGS =
			new HashMap<Specific, String>();
	
	public static final String OUTFILE = "./out.txt";
	
	{
		FileWriter fw;
		try {
			fw = new FileWriter(OUTFILE, false);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String lbl;
		
		lbl = " [ GAME ] ";
		STRINGS.put(Game.PAUSED, 		lbl + "Game has been paused.");
		STRINGS.put(Game.STARTED, 		lbl + "Game has been started.");
		STRINGS.put(Game.STOPPED, 		lbl + "Game has been stopped.");
		STRINGS.put(Game.TO_MAIN_MENU, 	lbl + "Game exited to main menu.");

		lbl = " [INPUT ] ";
		STRINGS.put(Input.KEYBOARD, 	lbl + "Keyboard key %s pressed.");
		STRINGS.put(Input.MOUSE, 		lbl + "Mouse button %s pressed.");
		
		lbl = " [ MENU ] ";
		STRINGS.put(Menu.ANY_KEY, 		lbl + "Any key pressed.");
		STRINGS.put(Menu.EXIT, 			lbl + "Game exited.");
		STRINGS.put(Menu.SETTINGS, 		lbl + "Switched to menu Settings.");
		STRINGS.put(Menu.START, 		lbl + "Proceeding to start game.");
		
		lbl = " [PLAYER] ";
		STRINGS.put(Player.COLLISION, 	lbl + "Collided with %s.");
		STRINGS.put(Player.JUMP, 		lbl + "Jumping.");
		STRINGS.put(Player.SLIDE, 		lbl + "Sliding.");
		STRINGS.put(Player.START_MOVE, 	lbl + "Started moving from lane %i.");
		STRINGS.put(Player.STOP_MOVE, 	lbl + "Stopped moving in lane %i.");
	}

	@Override
	public void gameUpdate(Category cat, Specific spec, Object[] optionalArgs) {
		String line = getLogString(spec, optionalArgs);
		
		FileWriter fw;
		try {
			fw = new FileWriter(OUTFILE, true);
			fw.write(line + "\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println(line);
		}
	}

	private String getLogString(Specific spec, Object[] optionalArgs) {
		return new Timestamp(System.currentTimeMillis()).toString() 
				+ String.format(STRINGS.get(spec), optionalArgs);
	}

}
