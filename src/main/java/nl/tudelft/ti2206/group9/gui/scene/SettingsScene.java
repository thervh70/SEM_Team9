package nl.tudelft.ti2206.group9.gui.scene;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;

/**
 * A screen for displaying a settings menu.
 * @author Maikel, Maarten and Mitchell
 */
public final class SettingsScene extends AbstractMenuScene {

    /** Max volume. */
    private static final double MAX_VOLUME = 10;
    /** Effect slider row. */
    private static final int EFFECT_SLIDER_COLUMN = 4;
    /** Soundtrack slider row. */
    private static final int TRACK_SLIDER_COLUMN = 6;
    /** Sliders row. */
    private static final int SLIDER_ROW = 16;
    /** Constant for converting the slider value to a setable volume level. */
    private static final int VOLUME_CONVERTER = 10;

    /**
     * Create a slider for the sound effects volume.
     */
    private static Slider soundEffectVolumeSlider;
    /**
     * Create a slider for the soundtrack volume.
     */
    private static Slider soundtrackVolumeSlider;

    /**
     * Types of buttons that exist.
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
     * Types of sliders that exist.
     */
    enum SType {
        /**
         * Sound Effects slider.
         */
        VOLUME_SOUNDEFFECTS,
        /**
         * Soundtrack slider.
         */
        VOLUME_SOUNDTRACK
    }

    /**
     * Initially the toggles are set to off.
     */
    private static final String INIT_TOGGLE = "DISABLED";
    /**
     * Standard string for toggles that are set to on.
     */
    private static final String ACTIVE_TOGGLE = "ENABLED";

    /**
     * Creates a Sound toggle button and a Back button.
     * @return an array of Nodes to be added to the Scene.
     */
    @Override
    public Node[] createContent() {
        final int columnSpan = 5;
        final Label soundEfLabel = createLabel("Sound Effects", 4, 13);
        final Label sounfTrLabel = createLabel("Soundtrack", 6, 13);
        final Button backButton = createButton("BACK", 0, 22);
        final Label playerName = getPlayerLabelContent();
        GridPane.setColumnSpan(soundEfLabel, columnSpan);
        GridPane.setColumnSpan(backButton, columnSpan);
        GridPane.setColumnSpan(playerName, columnSpan);
        String soundtrToggle = INIT_TOGGLE;
        if (State.isSoundtrackEnabled()) {
            soundtrToggle = ACTIVE_TOGGLE;
        }
        final Button soundtrButton = createButton(soundtrToggle, 6, 14);
        String soundEfToggle = INIT_TOGGLE;
        if (State.isSoundEffectsEnabled()) {
            soundEfToggle = ACTIVE_TOGGLE;
        }
        createSliders();
        final Button soundEfButton = createButton(soundEfToggle, 4, 14);
        setBackButtonFunction(backButton);
        setToggleButtonFunction(soundtrButton, BType.SETTING_SOUNDTRACK);
        setToggleButtonFunction(soundEfButton, BType.SETTING_SOUNDEFFECTS);
        GridPane.setColumnSpan(soundEfButton, 2);
        setTooltips(soundtrButton, soundEfButton, backButton);
        return new Node[]{backButton, soundtrButton, soundEfButton, playerName,
                soundEfLabel, sounfTrLabel, soundEffectVolumeSlider,
                soundtrackVolumeSlider};
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
                    soundtrackVolumeSlider.setDisable(false);
                    MainMenuScene.getAudioPlayer().play();
                } else {
                    s = INIT_TOGGLE;
                    soundtrackVolumeSlider.setDisable(true);
                }
                OBSERVABLE.notify(Category.MENU, Menu.SETTING_SOUNDTRACK, s);
            } else {
                State.setSoundEffectsEnabled(!State.isSoundEffectsEnabled());
                if (State.isSoundEffectsEnabled()) {
                    s = ACTIVE_TOGGLE;
                    soundEffectVolumeSlider.setDisable(false);
                } else {
                    s = INIT_TOGGLE;
                    soundEffectVolumeSlider.setDisable(true);
                }
                OBSERVABLE.notify(Category.MENU, Menu.SETTING_SOUNDEFFECTS, s);
             }
             button.setText(s);
        });
    }

    /**
     * Set backbutton function.
     *
     * @param b Button to be set.
     */
    private static void setBackButtonFunction(final Button b) {
        b.setOnAction(event -> {
                    playButtonSound();
                    OBSERVABLE.notify(Category.MENU, Menu.SETTINGS_BACK);
                    ShaftEscape.setScene(new MainMenuScene());
                }
        );
    }

    /**
     * Sets the tooltips of the SettingsScene buttons.
     * @param soundtrButton that needs a tooltip.
     * @param soundEfButton that needs a tooltip.
     * @param backButton that needs a tooltip.
     */
    private static void setTooltips(final Button soundtrButton,
            final Button soundEfButton, final Button backButton) {
        soundtrButton.setTooltip(new Tooltip("Enable/disable soundtrack"));
        soundEfButton.setTooltip(new Tooltip("Enable/disable sound effects"));
        backButton.setTooltip(new Tooltip("Back to main menu"));
    }

    /**
     * Creates the sliders of the SettingsScene.
     */
    private static void createSliders() {
        soundEffectVolumeSlider = createVolumeSlider(EFFECT_SLIDER_COLUMN,
                SLIDER_ROW, State.isSoundEffectsEnabled(),
                SType.VOLUME_SOUNDEFFECTS, State.getSoundEffectVolume());
        soundtrackVolumeSlider = createVolumeSlider(TRACK_SLIDER_COLUMN,
                SLIDER_ROW, State.isSoundtrackEnabled(),
                SType.VOLUME_SOUNDTRACK, State.getSoundtrackVolume());
    }

    /**
     * Creating a slider that can adapt the volume.
     * Every slider has, besides its place on the screen, it's own slider
     * type and initial volume level. These two have been added so that the
     * slider values can be saved and can be distinguished in functionality.
     * @param column  Columnconstraint.
     * @param row     Rowconstraint.
     * @param enabled indicates whether the slider is enabled.
     * @param sliderType (SType) indicates the type of slider.
     * @param initialVolume (double) starts at the initial volume level,
     * especially necessary for save games, where initial volume levels
     * may be different.
     * @return Return created slider.
     */
    private static Slider createVolumeSlider(final int column,
            final int row, final boolean enabled, final SType sliderType,
            final double initialVolume) {
        final Slider slider = new Slider();
        final int majorTickUnit = 5;
        final int minorTickCount = 4;
        final int width = 120;
        if (!enabled) {
            slider.setDisable(true);
        }
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMaxWidth(width);
        slider.setMax(MAX_VOLUME);
        slider.setMin(0);
        slider.setTooltip(new Tooltip("Adjust volume"));
        slider.setValue(initialVolume * VOLUME_CONVERTER);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(majorTickUnit);
        slider.setMinorTickCount(minorTickCount);
        GridPane.setConstraints(slider, column, row);
        final Color color = Color.BLACK;
        final CornerRadii corner = new CornerRadii(4);
        final Insets inset = new Insets(0);
        final BackgroundFill fill = new BackgroundFill(color, corner, inset);
        final Background sliderBack = new Background(fill);
        slider.setBackground(sliderBack);
        setSliderFunction(slider, sliderType);
        return slider;
    }

    /**
     * Sets the volume of the the sounds that are used in the application.
     * The audioplayer volumes of the 'non-GameScenes' are already set below.
     * The others are set in the GameScene (SoundEffectObserver).
     * @param slider the given slider to set the function of.
     * @param type the given type of slider to set the volume of.
     */
    protected static void setSliderFunction(final Slider slider,
            final SType type) {
        slider.setOnMouseReleased(event -> {
            if (type == SType.VOLUME_SOUNDTRACK) {
                State.setSoundtrackVolume(soundtrackVolumeSlider.
                        getValue() / VOLUME_CONVERTER);
                MainMenuScene.getAudioPlayer().
                        setVolume(State.getSoundtrackVolume());
            } else {
                State.setSoundEffectVolume(soundEffectVolumeSlider.
                        getValue() / VOLUME_CONVERTER);
            }
            playButtonSound();
        });
    }

    /** Override background, the Settings background shows "Settings". */
    @Override
    public String getBackgroundPath() {
        return "settingsBackground.png";
    }

}
