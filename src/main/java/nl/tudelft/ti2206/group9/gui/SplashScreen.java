package nl.tudelft.ti2206.group9.gui;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Input;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;
import nl.tudelft.ti2206.group9.util.Logger;

/**
 * @author Maikel and Mitchell
 *
 * A splashcreen that show a "Press any key to continue", also starts the
 * entire application.
 */
@SuppressWarnings("restriction")
public final class SplashScreen extends Application {
    /** Duration of transition in ms. */
    private static final int TRANSITION_TIME = 750;

    /**
     * Creating and  displaying the scene.
     * @param primaryStage The stage to be started.
     */
    public void start(final Stage primaryStage) {
    	GameObservable.addObserver(new Logger());

        /** Creating a new stackpane and scene. */
    	final StackPane root = new StackPane();
    	final Scene scene = new Scene(root,
    			GUIConstant.WIDTH, GUIConstant.HEIGHT);

        /** Setting the background image */
        Style.setBackground("sc.png", root);

        Label text = createLabel("Press any key to continue");
        addMouseClick(root, primaryStage);
        addKeyPressed(scene, primaryStage);

        /** Add the text to the canvas and give it a fade
         * in/ fade out effect. */
        root.getChildren().add(text);
        root.setAlignment(Pos.CENTER);
        
        generateFadeTransition(text);
        
        /** Setting the right scene and displaying it. */
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	/**
     * Main method to launch the application.
     * @param args -
     */
    public static void main(final String... args) {
        launch(args);
    }

    /**
     * Creating a new label for displaying text.
     * @param string a given sentence.
     * @return label resulting label.
     */
    private Label createLabel(final String text) {
        final Label label = new Label(text);
        Style.setLabelStyle(label);
		return label;
	}
    
    /**
     * Defining what has happens in case of a mouseClickEvent.
     * @param stackpane current stackPane.
     * @param primaryStage current primaryStage.
     */
    private static void addMouseClick(final StackPane stackPane, 
    									final Stage primaryStage) {
    	stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent me) {
                GameObservable.notify(Category.INPUT, Input.MOUSE,
                        me.getButton());
                GameObservable.notify(Category.MENU, Menu.ANY_KEY);
                StartScreen.start(primaryStage);
            }
        });    	
    }

    /**
     * Defining what happens in case of a random keyPressedEvent.
     * @param scene current scene.
     * @param primaryStage current primaryStage.
     */
    private static void addKeyPressed(final Scene scene, 
    									final Stage primaryStage) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent ke) {
                GameObservable.notify(Category.INPUT, Input.KEYBOARD,
                        ke.getCode());
                GameObservable.notify(Category.MENU, Menu.ANY_KEY);
                StartScreen.start(primaryStage);
            }
        });
	
    }

    /**
     * Generates a fade transition for a specific label.
     * @param label given label.
     */
	private static void generateFadeTransition(final Label label) {
        final FadeTransition ft = new FadeTransition(
                Duration.millis(TRANSITION_TIME), label);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(TRANSITION_TIME * 2);
        ft.setAutoReverse(true);
        ft.play();
	}

}
