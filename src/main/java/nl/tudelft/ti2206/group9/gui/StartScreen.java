package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;

/**
 * @author Maikel and Robin
 *
 * A startscreen with different options/buttons like a options menu, startbutton
 * and exit button.
 */
@SuppressWarnings("restriction")
public final class StartScreen {

	/** Hide public constructor. */
	private StartScreen() { }

    /**
     *Creating and displaying the startscreen.
     *
     * @param primaryStage The stage to be started.
     */
    public static void start(final Stage primaryStage) {

        final Stage window;
        final Scene startScreen;
        final Button startButton,
        settingsButton,loadButton,
        exitButton;
        window = primaryStage;
        final Label label = new Label("");

        /**Creating the gridPane which is used for the layout. */
        final StackPane pane = new StackPane();
        pane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));


        /**Setting a background for the menu.*/
        Style.setBackground("sc.png", pane);

        HBox hbox = new HBox(60);
        VBox vbox = new VBox(30);


        /** Add text to buttons give them a location on the pane.*/
        startButton = new Button("START");
        Style.setButtonStyle(startButton);
        GridPane.setConstraints(startButton, 6, 26);

        settingsButton = new Button("SETTINGS");
        Style.setButtonStyle(settingsButton);
        GridPane.setConstraints(settingsButton, 2, 26);

        exitButton = new Button("EXIT");
        Style.setButtonStyle(exitButton);

        loadButton = new Button("LOAD GAME");
        Style.setButtonStyle(loadButton);

        hbox.getChildren().addAll(settingsButton, startButton, exitButton);
        vbox.getChildren().addAll(label, loadButton,hbox);

        vbox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.BOTTOM_CENTER);

        /**Adding all buttons to the pane.*/
        pane.getChildren().addAll(vbox);

        /**Creating the scene. */
        startScreen = new Scene(pane, GUIConstant.WIDTH, GUIConstant.HEIGHT);

        /**Setting function of the buttons. */
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                GameObservable.notify(Category.MENU, Menu.EXIT);
                primaryStage.close();
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                GameObservable.notify(Category.MENU, Menu.START);
                GameScreen.start(primaryStage);
            }
        });

        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                GameObservable.notify(Category.MENU, Menu.SETTINGS);
                SettingsScreen.start(primaryStage);
            }
        });

        /**Set the scene for the window and display it. */
        window.setScene(startScreen);
        window.show();
    }
}

