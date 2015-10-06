package nl.tudelft.ti2206.group9.audio;

import java.util.Observable;

import nl.tudelft.ti2206.group9.util.GameObserver;

/**
 * The SoundEffectsPlayer plays the SoundEffects that accompany the player's
 * movement.
 * @author Maarten and Mitchell
 */
public class SoundEffectsPlayer implements GameObserver { //NOPMD - complexity

	/** The path for any audio file. */
	private static String audioPath = "src/main/"
	+ "resources/nl/tudelft/ti2206/group9/audio/";
	/** The AudioPlayer to be used for the coin sound effect. */
	private static AudioPlayer apCoin = new AudioPlayer(audioPath + "coin.wav");
	/** The AudioPlayer to be used for the death sound effect. */
	private static AudioPlayer apDie = new AudioPlayer(audioPath + "death.wav");
	/** The AudioPlayer to be used for the jump sound effect. */
	private static AudioPlayer apJump = new AudioPlayer(audioPath + "jump.wav");
	/** The AudioPlayer to be used for the move sound effect. */
	private static AudioPlayer apMove = new AudioPlayer(audioPath + "move.wav");
	/** The AudioPlayer to be used for the slide sound effect. */
	private static AudioPlayer apSlide = new AudioPlayer(audioPath
			+ "slide.wav");

	@Override
	public void update(final Observable o, final Object arg) {
		final GameUpdate update = (GameUpdate) arg;
		if (update.getCat() != Category.PLAYER) {
			return;
		}
		switch ((Player) update.getSpec()) {
		case JUMP:       apJump.play(false); break;
		case SLIDE:      apSlide.play(false); break;
		case START_MOVE: apMove.play(false); break;
		case STOP_MOVE:  break;
		case COLLISION:
			switch ((String) update.getArgs()[0]) {
			case "AbstractObstacle":
				apDie.play(false);
				break;
			case "Coin":
				apCoin.play(false);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}

}
