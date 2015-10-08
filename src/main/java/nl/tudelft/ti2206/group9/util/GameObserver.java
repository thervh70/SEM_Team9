package nl.tudelft.ti2206.group9.util;

import java.util.Arrays;
import java.util.Observer;

/**
 * Interface for observers who want to observe the game.
 * @author Maarten
 */
public interface GameObserver extends Observer {

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
		/** Exceptions that happen during the game, see {@link Error}. */
		ERROR,
	}

	/** Super-enum for specific statuses/actions/... */
	interface Specific { }

	/** Specific statuses for Game updates. */
	enum Game implements Specific {
		/** Game started. */
		STARTED,
		/** Game stopped. */
		STOPPED,
		/** Game paused. */
		PAUSED,
		/** Game resumed. */
		RESUMED,
		/** Game restarted. */
		RETRY,
		/** Game exited to main menu. */
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
		/** Setting "sound" has been toggled. optionalArg: isSoundEnabled. */
		SETTING_SOUND,
		/** Back from settings screen to main menu. */
		SETTINGS_BACK,
		/** Exit button has been pressed. */
		EXIT,
		/** Load game button has been pressed. */
		LOAD_MENU,
		/** Back button for load menu has been pressed. */
		LOAD_BACK,
		/** Load game button has been pressed. */
		LOAD,
		/** Shop button in main menu has been pressed. */
		SHOP,
        /** Back button in ShopScene has been pressed. */
        SHOP_BACK
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
	 * Specific Exceptions thrown during the game.
	 * optionalArgs: exception location, exception message.
	 */
	enum Error implements Specific {
		/** There has been an exception with reading/writing to files.
		 *  optionalArgs: exception location, exception message. */
		IOEXCEPTION,
		/** There has been an exception with parsing an URL.
		 *  optionalArgs: exception location, exception message. */
		MALFORMEDURLEXCEPTION,
		/** There has been an exception with the audio player.
		 *  optionalArgs: exception location, exception message. */
		MEDIAEXCEPTION,
		/** There has been an exception with parsing JSON files.
		 *  optionalArgs: exception location, exception message. */
		PARSEEXCEPTION
	}

	/** Object containing information about the game update. */
	class GameUpdate {

		/** Category of the update. */
		private final Category cat;
		/** Specific case of the update. */
		private final Specific spec;
		/** Optional arguments of the update. */
		private final Object[] args;

		/**
		 * Default constructor.
		 * @param c Category of the update.
		 * @param s Specific case of the update.
		 * @param optionalArgs Optional arguments of the update.
		 */
		public GameUpdate(final Category c, final Specific s,
				final Object... optionalArgs) {
			cat = c;
			spec = s;
			args = optionalArgs;
		}

		/** @return the Category of the update. */
		public Category getCat() {
			return cat;
		}

		/** @return the Specific case of the update. */
		public Specific getSpec() {
			return spec;
		}

		/** @return the Optional arguments of the update. */
		public Object[] getArgs() {
			return Arrays.copyOf(args, args.length);
		}

	}

}
