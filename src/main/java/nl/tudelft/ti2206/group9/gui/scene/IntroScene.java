package nl.tudelft.ti2206.group9.gui.scene;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.Style;

import java.util.ArrayList;

/**
 * Small scene that contains the intro for our game.
 * It has several animations and a link to the GameScene.
 * @author Robin
 */
public class IntroScene extends AbstractScene {

    /** Font size for lines. */
    private final int lineSize = 25;
    /** Flag for eventhandler. */
    private boolean done = false;
    /** Index for the third item of the list. */
    private final int three = 3;
    /** Index for the fourth item of the list. */
    private final int four = 4;

    @Override
    public Parent createRoot() {
        StackPane pane = new StackPane();
        final BackgroundFill fill = new BackgroundFill(Color.BLACK,
                new CornerRadii(0), new Insets(0));
        final Background background = new Background(fill);
        pane.setBackground(background);
        showIntro();
        pane.getChildren().addAll(showIntro());
        return pane;
    }

    /**
     * Method generates all the correct parameters and
     * starts the animation sequence.
     * It also contains the listener for exiting this scene.
     * @return A list of nodes used for filling the scene.
     */
    private ArrayList<Node> showIntro() {
        final ArrayList<Node> nodeList = new ArrayList<>();
        final Label prologue = new Label("Prologue");
        final Label lineA = new Label("Once upon a time,");
        final Label lineB = new Label("a miner was busy working.");
        final Label lineC = new Label("But then ...");
        final Label lineD = new Label("He had to ");
        final Label key = new Label("Press a key to continue.");
        lineA.setVisible(false);
        lineB.setVisible(false);
        lineC.setVisible(false);
        lineD.setVisible(false);
        key.setVisible(false);

        final int spacing = 10;
        VBox box = new VBox(spacing);
        box.setAlignment(Pos.BASELINE_CENTER);
        box.getChildren().addAll(lineA, lineB, lineC, lineD, key);

        showStory(prologue, new Label[]{lineA, lineB, lineC, lineD, key});

        this.setOnKeyPressed(e -> {
            if (done) {
                ShaftEscape.setScene(new GameScene());
            }
        });
        nodeList.add(prologue);
        nodeList.add(box);
        return nodeList;
    }

    /**
     * Method responsible for enabling all the transitions.
     * It makes the animations cascade correctly.
     * @param prologue The label that says "Prologue".
     * @param list The full list of Labels.
     */
    private void showStory(final Label prologue, final Label[] list) {
        FadeTransition ft = showPrologue(prologue);
        ft.setOnFinished(event -> {
            FadeTransition ft0 = showLine(list[0]);
            ft0.setOnFinished(event1 -> {
                FadeTransition ft1 = showLine(list[1]);
                ft1.setOnFinished(event2 -> {
                    FadeTransition ft2 = showLine(list[2]);
                    ft2.setOnFinished(event3 -> {
                        FadeTransition ft3 = showLine(list[three]);
                        ft3.setOnFinished(event4 -> {
                            done = true;
                            showHint(list[four]);
                        });
                    });
                });
            });
        });
    }

    /**
     * Shows the prologue label.
     * @param prologue the label.
     * @return The transition object.
     */
    private FadeTransition showPrologue(final Label prologue) {
        prologue.setOpacity(0);
        prologue.setTextFill(Color.WHITE);
        final int prologueSize = 35;
        prologue.setFont(Style.getFont(prologueSize));
        final FadeTransition ft = new FadeTransition(
                Duration.millis(2000), prologue);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        return ft;
    }

    /**
     * Method that shows a single line.
     * It sets the visibility and enables a fade.
     * @param l The label to be shown.
     * @return It returns the Transition to be able
     *      to time correctly.
     */
    private FadeTransition showLine(final Label l) {
        l.setVisible(true);
        l.setOpacity(0);
        l.setTextFill(Color.WHITE);
        l.setFont(Style.getFont(lineSize));
        final FadeTransition ft = new FadeTransition(
                Duration.millis(3000), l);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        return ft;
    }

    /**
     * Shows the hint to proceed to the next scene.
     * @param l The label that says "press key to continue".
     */
    private void showHint(final Label l) {
        l.setVisible(true);
        l.setOpacity(0);
        l.setTextFill(Color.WHITE);
        l.setFont(Style.getFont(lineSize));
        final FadeTransition ft = new FadeTransition(
                Duration.millis(750), l);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
}
