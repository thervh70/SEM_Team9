package nl.tudelft.ti2206.group9.gui.popup;

import java.util.Arrays;

import nl.tudelft.ti2206.group9.audio.SoundEffectPlayer;
import nl.tudelft.ti2206.group9.gui.Style;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

/**
 * Abstract Popup class as a template for all Popups in the game.
 * @author Maarten, Robin
 */
@SuppressWarnings("restriction")
public abstract class AbstractInfoPopup extends Popup {

    /** Width of the PopUpMenu. */
    public static final double WIDTH = 260;
    /** Height of the PopUpMenu. */
    public static final double HEIGHT = 320;
    /** Size of the HBox. */
    protected static final double HBOX_SPACING = 10;
    /** Size of the VBox. */
    protected static final double VBOX_SPACING = 50;

    /** The AudioPlayer to be used for a button sound effect. */
    private static final SoundEffectPlayer BUTTON_SOUND = new SoundEffectPlayer(
            "nl/tudelft/ti2206/group9/audio/button.wav");

    /** The left button in the Popup. */
    private final Button left;
    /** The right button in the Popup. */
    private final Button right;

    /**
     * Constructs a new Popup with default {@link #WIDTH} and {@link #HEIGHT}.
     * @param leftButton Button that should be shown in the bottom-left.
     * @param rightButton Button that should be shown in the bottom-right.
     */
    public AbstractInfoPopup(final Button leftButton,
                             final Button rightButton) {
        super();
        left = leftButton;
        right = rightButton;

        setWidth(WIDTH);
        setHeight(HEIGHT);
        setHideOnEscape(false);

        final Rectangle rect = new Rectangle(WIDTH, HEIGHT, Color.WHITESMOKE);
        rect.setStroke(Color.BLACK);

        Style.setPopupButtonStyle(left);
        Style.setPopupButtonStyle(right);

        final HBox hbox = new HBox(HBOX_SPACING, left, right);
        hbox.setAlignment(Pos.CENTER);

        final Node[] content = createContent();
        final Node[] content2 = Arrays.copyOf(content, content.length + 1);
        content2[content.length] = hbox;
        final VBox vbox = new VBox(VBOX_SPACING, content2);
        vbox.setAlignment(Pos.CENTER);

        getContent().addAll(rect, vbox);
    }

    /**
     * This method returns the content that should be added to the VBox of the
     * Popup. Each next node will be placed below the previous.
     * @return A list of Nodes to be added to the VBox of the Popup.
     */
    public abstract Node[] createContent();

    /**
     * @return the left Button
     */
    public Button getLeftButton() {
        return left;
    }

    /**
     * @return the right Button
     */
    public Button getRightButton() {
        return right;
    }

    /** Plays the button sound once. */
    protected static void playButtonSound() {
        BUTTON_SOUND.play();
    }

}
