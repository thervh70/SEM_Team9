package nl.tudelft.ti2206.group9.gui; // NOPMD - too many imports, but no way
                                      // to reduce them, unfortunately.

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.renderer.AbstractGroupRenderer;
import nl.tudelft.ti2206.group9.gui.renderer.GroupEntitiesRenderer;
import nl.tudelft.ti2206.group9.gui.renderer.GroupLightRenderer;
import nl.tudelft.ti2206.group9.gui.renderer.GroupTrackRenderer;
import nl.tudelft.ti2206.group9.gui.renderer.GroupWallRenderer;
import nl.tudelft.ti2206.group9.gui.scene.GameScene;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;
import nl.tudelft.ti2206.group9.level.entity.AbstractPowerup;
import nl.tudelft.ti2206.group9.level.entity.PowerupCoinMagnet;
import nl.tudelft.ti2206.group9.level.entity.PowerupDestroy;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;
import nl.tudelft.ti2206.group9.level.entity.PowerupSlowness;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maarten.
 */
public class ExternalTicker extends AnimationTimer {

    /** Height of the box in-game where the score is displayed. */
    private static final int SCORE_BOX_HEIGHT = 160;
    /** Width of the box in-game where the score is displayed. */
    private static final int SCORE_BOX_WIDTH = 160;

    /** Distance between labels in overlay. */
    private static final int LABEL_DISTANCE = 10;

    /** Label for the countdownLabel animation. */
    private final Label countdownLabel = new Label();
    /** List that stores the entities, to be held up-to-date with Track. */
    private final AbstractGroupRenderer entities;
    /** Group that stores the wall. */
    private final AbstractGroupRenderer wall;
    /** Group that stores the track. */
    private final AbstractGroupRenderer track;
    /** Group that stores the lights. */
    private final GroupLightRenderer light;

    /** Default constructor. */
    public ExternalTicker() {
        super();

        if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
            entities = new GroupEntitiesRenderer();
            wall = new GroupWallRenderer();
            track = new GroupTrackRenderer();
            light = new GroupLightRenderer();

            GameScene.addWorld(entities, wall, track, light);

        } else {
            entities = null;
            wall = null;
            track = null;
            light = null;
        }
    }

    @Override
    public final void handle(final long now) {
        if (State.isSoundtrackEnabled()) {
            Track.distanceCheck();
        }
        renderScene();
    }

    /**
     * This method renders the scene.
     */
    private void renderScene() {
        GameScene.clearOverlay();
        GameScene.addOverlay(renderScore(), countdownLabel);

        if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
            entities.update();
            wall.update();
            track.update();
            light.update();
        }
    }

    /**
     * @return VBox with score labels
     */
    private VBox renderScore() {
        final ArrayList<Label> labels = new ArrayList<>();
        final ArrayList<Node> nodes = new ArrayList<>();
        labels.add(new Label(State.getPlayerName()));
        labels.add(new Label("Highscore: " + State.getHighscore()));
        labels.add(new Label("Score: " + Track.modulo(State.getScore())));
        labels.add(new Label("Distance: " + Track.modulo(Track.getDistance())));
        labels.add(new Label("Coins: " + State.getCoins()));

        Style.setLabelStyle(labels.toArray(new Label[]{}));
        nodes.addAll(labels);
        nodes.addAll(createPowerupTimers());

        final VBox scoreBox = new VBox(LABEL_DISTANCE,
                nodes.toArray(new Node[]{}));
        scoreBox.setStyle(" -fx-background-color:BLACK;");
        scoreBox.setMinSize(SCORE_BOX_WIDTH, SCORE_BOX_HEIGHT);
        return scoreBox;
    }

    /**
     * @return a Group containing all PowerupTimers (could be none).
     */
    private List<Group> createPowerupTimers() {
        final ArrayList<Group> bars = new ArrayList<>();
        final double iconSize = 24;
        final ArrayList<Class<? extends AbstractPowerup>> powerups =
                new ArrayList<>();
        powerups.add(PowerupInvulnerable.class);
        powerups.add(PowerupSlowness.class);
        powerups.add(PowerupDestroy.class);
        powerups.add(PowerupCoinMagnet.class);
        final ArrayList<String> icons = new ArrayList<>();
        icons.add("invulnerable");
        icons.add("slowness");
        icons.add("destroy");
        icons.add("coinmagnet");
        for (int i = 0; i < powerups.size(); i++) {
            if (!AbstractPowerup.isActive(powerups.get(i))) {
                continue;
            }
            final Group bar = new Group();
            final double sec = AbstractPowerup.getSecondsLeft(powerups.get(i));
            final Rectangle rect = new Rectangle(iconSize, 0,
                    (SCORE_BOX_WIDTH - iconSize) * sec
                    / (double) AbstractPowerup.SECONDS, iconSize);
            rect.setFill(Color.LIME);
            bar.getChildren().add(Style.getIcon(icons.get(i)));
            bar.getChildren().add(rect);
            bars.add(bar);
        }
        return bars;
    }

    /**
     * Render the countdownLabel method.
     * @param index The index to be rendered.
     */
    public void countdown(final int index) {
        final int textSize = 64;
        final int offsetX = 20;
        final int offsetY = 50;

        countdownLabel.setText(Integer.toString(index));
        countdownLabel.setFont(Style.getFont(textSize));
        countdownLabel.setTextFill(Color.WHITE);
        countdownLabel.setLayoutX(ShaftEscape.WIDTH / 2 - offsetX);
        countdownLabel.setLayoutY(ShaftEscape.HEIGHT / 2 - offsetY);

        countdownAnimation(index);
    }

    /**
     * Render the animations for the countdownLabel method.
     * @param index The index to be rendered.
     */
    private void countdownAnimation(final int index) {
        final int duration = 400;
        final ScaleTransition st = new ScaleTransition(
                Duration.millis(duration), countdownLabel);
        st.setByY(1);
        st.setByX(1);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();

        final FadeTransition ft = new FadeTransition(
                Duration.millis(duration), countdownLabel);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
        ft.setToValue(1);
        ft.setFromValue(0);
        ft.play();

        st.setOnFinished(event -> {
            final int newIndex = index - 1;
            if (newIndex > 0) {
                countdown(newIndex);
            } else {
                GameScene.resumeTickers();
            }
        });
    }
}

