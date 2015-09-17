package nl.tudelft.ti2206.group9.util;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Specific;

/**
 * This utility class handles the observability of the game. If there are any
 * {@link GameObserver}s attached, calling the
 * {@link #notify(Category, Specific, Object...)} method will update these
 * observers.
 * @author Maarten
 */
public final class GameObservable {
	
	private GameObservable() { }
	
	private static List<GameObserver> observers = new ArrayList<GameObserver>();
	
	/**
	 * Add observer to the observers list.
	 * @param go GameObserver to add to the list.
	 */
	public static void addObserver(GameObserver go) {
		observers.add(go);
	}
	
	/**
	 * Remove observer from the observers list.
	 * @param go GameObserver to remove from the list.
	 */
	public static void deleteObserver(GameObserver go) {
		observers.remove(go);
	}
	
	/**
	 * Call this method when you want to update the observers.
	 * @param cat the Category of this update.
	 * @param spec the Specific action of this update.
	 * @param optionalArgs Optional arguments that come with the update
	 * 			(e.g. lane numbers, mouse buttons, keyboard keys, ...)
	 */
	public static void notify(Category cat, Specific spec, 
			Object... optionalArgs) {
		for (GameObserver go : observers) {
			go.gameUpdate(cat, spec, optionalArgs);
		}
	}
	
}
