package nl.tudelft.ti2206.group9.audio;

import java.util.Observable;

import nl.tudelft.ti2206.group9.util.GameObserver;

/**
 * The SoundEffectsPlayer plays the SoundEffects that accompany the player's
 * movement.
 * @author Maarten and Mitchell
 */
public class SoundEffectObserver implements GameObserver { //NOPMD - complexity

    /** The path for any audio file. */
    private static String audioPath = "src/main/"
    + "resources/nl/tudelft/ti2206/group9/audio/";
    /** The SoundEffectPlayer to be used for the coin sound effect. */
    private static SoundEffectPlayer apCoin =
        new SoundEffectPlayer(audioPath + "coin.wav");
    /** The SoundEffectPlayer to be used for the death sound effect. */
    private static SoundEffectPlayer apDie =
        new SoundEffectPlayer(audioPath + "death.wav");
    /** The SoundEffectPlayer to be used for the jump sound effect. */
    private static SoundEffectPlayer apJump =
        new SoundEffectPlayer(audioPath + "jump.wav");
    /** The SoundEffectPlayer to be used for the move sound effect. */
    private static SoundEffectPlayer apMove =
        new SoundEffectPlayer(audioPath + "move.wav");
    /** The SoundEffectPlayer to be used for the slide sound effect. */
    private static SoundEffectPlayer apSlide =
        new SoundEffectPlayer(audioPath + "slide.wav");

    @Override
    public void update(final Observable o, final Object arg) {
        final GameUpdate update = (GameUpdate) arg;
        if (update.getCat() != Category.PLAYER) {
            return;
        }
        switch ((Player) update.getSpec()) {
        case JUMP:       apJump.play(); break;
        case SLIDE:      apSlide.play(); break;
        case START_MOVE: apMove.play(); break;
        case STOP_MOVE:  break;
        case COLLISION:
            switch ((String) update.getArgs()[0]) {
            case "AbstractObstacle": apDie.play(); break;
            case "Coin":             apCoin.play(); break;
            default: break;
            }
            break;
        default:
            break;
        }
    }

}
