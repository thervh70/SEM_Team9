package nl.tudelft.ti2206.group9.util;

public interface GameObserver {

	/** Different Categories for updates. */
	enum Category {
		/** Game statuses, see {@link Game}. */
		GAME,
		/** Input events (mouse and keyboard presses), see {@link Input}. */
		INPUT,
		/** Menu button presses, see {@link Menu}. */
		MENU,
		/** Player actions, see {@link Player}. */
		PLAYER,
	};
	
	/** Super-enum for specific statuses/actions/... */
	interface Specific { }
	
	/** Specific statuses for Game updates. */
	enum Game implements Specific {
		STARTED,
		STOPPED,
		PAUSED,
		TO_MAIN_MENU,
	}
	
	/** 
	 * Whether input is from Keyboard or from Mouse. 
	 * Mouse input is accompanied with optionalArg MouseButton.
	 */
	enum Input implements Specific {
		/** Keyboard input. optionalArg: KeyCode. */
		KEYBOARD,
		/** Mouse input. optionalArg: MouseButton. */
		MOUSE,
	}
	
	/**
	 * Specific actions for Menu buttons.
	 */
	enum Menu implements Specific {
		/** Splash screen has been clicked. */
		ANY_KEY,
		/** Start button has been pressed. */
		START,
		/** Settings button has been pressed. */
		SETTINGS,
		/** Exit button has been pressed. */
		EXIT,
	}
	
	/**
	 * Specific actions executed by the Player.
	 */
	enum Player implements Specific {
		/** Player starts moving. optionalArg: current lane. */
		START_MOVE,
		/** Player stops moving. optionalArg: current lane. */
		STOP_MOVE,
		/** Player jumps. */
		JUMP,
		/** Player slides. */
		SLIDE,
		/** Player collides. optionalArg: name of entity collided with. */
		COLLISION,
	}
	
	/**
	 * Is called when the game is updated. The internal classes should call
	 * {@link State#updateObservers} to update GameObservers.
	 * @param cat the Category of this update.
	 * @param spec the Specific action of this update.
	 * @param optionalArgs Optional arguments that come with the update
	 * 			(e.g. lane numbers, mouse buttons, keyboard keys, ...)
	 */
	void gameUpdate(Category cat, Specific spec, Object[] optionalArgs);
	
}