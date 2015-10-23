package nl.tudelft.ti2206.group9.gui.popup;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.ti2206.group9.gui.scene.MainMenuScene;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.shop.CurrentItems;
import nl.tudelft.ti2206.group9.util.GameObserver;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;


/**
 * PausePopup, to be shown when the Game is paused.
 * @author Maarten, Robin
 */
@SuppressWarnings("restriction")
public class PausePopup extends AbstractInfoPopup {

    /**
     * Type of buttons that exist.
     */
    enum BType {
        /**
         * Sound Effects toggle.
         */
        SETTING_SOUNDEFFECTS,
        /**
         * Soundtrack toggle.
         */
        SETTING_SOUNDTRACK
    }
    /**
     * Initially the toggles are set to off.
     */
    private static final String INIT_TOGGLE = "DISABLED";
    /**
     * Standard string for toggles that are set to on.
     */
    private static final String ACTIVE_TOGGLE = "ENABLED";
    /** Size of the VBox. */
    protected static final double VBOX_SPACING_SETTINGS = 30;

    /**
     * Creates a new PausePopup.
     * @param resumeEvent event that should happen when "Resume" button is
     * clicked.
     * @param menuEvent event that should happen when "Main Menu" button is
     * clicked.
     */
    public PausePopup(final EventHandler<MouseEvent> resumeEvent,
            final EventHandler<MouseEvent> menuEvent) {
        super(new Button("Resume"), new Button("Return to Main Menu"));

        getLeftButton().setOnMouseClicked(mouseEvent -> {
            playButtonSound();
            hide();
            resumeEvent.handle(mouseEvent);
            CurrentItems.getSoundtrackPlayer().play();
        });

        getRightButton().setOnMouseClicked(mouseEvent -> {
            playButtonSound();
            hide();
            menuEvent.handle(mouseEvent);
            CurrentItems.getSoundtrackPlayer().resetSpeed();
            CurrentItems.getSoundtrackPlayer().stop();
        });
    }

    /**
     * Creates a Sound toggle button and a Back button.
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        final Label pauseTitle = createLabel("Paused");

        final Label soundEfLabel = createLabel("Soundtrack");
        final Label soundTrLabel = createLabel("Sound effects");
        String soundtrToggle = INIT_TOGGLE;
        if (State.isSoundtrackEnabled()) {
            soundtrToggle = ACTIVE_TOGGLE;
        }
        final Button soundtrButton = createButton(soundtrToggle);
        String soundEfToggle = INIT_TOGGLE;
        if (State.isSoundEffectsEnabled()) {
            soundEfToggle = ACTIVE_TOGGLE;
        }
        final Button soundEfButton = createButton(soundEfToggle);

        setToggleButtonFunction(soundtrButton, BType.SETTING_SOUNDTRACK);
        setToggleButtonFunction(soundEfButton, BType.SETTING_SOUNDEFFECTS);
        soundtrButton.setTooltip(new Tooltip("Enable/disable soundtrack"));
        soundEfButton.setTooltip(new Tooltip("Enable/disable sound effects"));

        VBox vBoxLeft = new VBox(VBOX_SPACING_SETTINGS,
                soundTrLabel, soundtrButton);
        vBoxLeft.setAlignment(Pos.CENTER);
        VBox vBoxRight = new VBox(VBOX_SPACING_SETTINGS,
                soundEfLabel, soundEfButton);
        vBoxRight.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(HBOX_SPACING, vBoxLeft, vBoxRight);
        return new Node[]{pauseTitle, hBox};
    }

    /**
     * This method sets the function of a button.
     * @param button Button to be set.
     * @param type Type of button
     */
    protected static void setToggleButtonFunction(final Button button,
                                                  final BType type) {
        button.setOnAction(event -> {
            playButtonSound();
            String s;
            if (type == BType.SETTING_SOUNDTRACK) {
                MainMenuScene.getAudioPlayer().stop();
                State.setSoundtrackEnabled(!State.isSoundtrackEnabled());
                if (State.isSoundtrackEnabled()) {
                    s = ACTIVE_TOGGLE;
                } else {
                    s = INIT_TOGGLE;
                }
                button.setText(s);
                OBSERVABLE.notify(GameObserver.Category.MENU,
                        GameObserver.Menu.SETTING_SOUNDTRACK, s);
            } else {
                State.setSoundEffectsEnabled(!State.isSoundEffectsEnabled());
                if (State.isSoundEffectsEnabled()) {
                    s = ACTIVE_TOGGLE;
                } else {
                    s = INIT_TOGGLE;
                }
                button.setText(s);
                OBSERVABLE.notify(GameObserver.Category.MENU,
                        GameObserver.Menu.SETTING_SOUNDEFFECTS, s);
            }
        });
    }

}
