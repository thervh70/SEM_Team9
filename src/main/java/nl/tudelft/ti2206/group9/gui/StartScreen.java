package nl.tudelft.ti2206.group9.gui;

import nl.tudelft.ti2206.group9.util.GameObservable;
import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Input;
import nl.tudelft.ti2206.group9.util.GameObserver.Menu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

        Stage window;
        Scene startScreen;
        Button startButton, settingsButton, exitButton;

        window = primaryStage;

        /**Creating the gridPane which is used for the layout. */
        GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        grid.setVgap(20);
        grid.setHgap(20);

        /**Setting a background for the menu.*/
        //grid.getChildren().add(Style.setBackground(
        // 		"/View/Resources/background_splashscreen.png"));

        /** Add text to buttons give them a location on the grid.*/
        startButton = new Button("START");
        GridPane.setConstraints(startButton, 7, 21);

        settingsButton = new Button("SETTINGS");
        GridPane.setConstraints(settingsButton, 3, 21);

        exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 11, 21);

        /**Adding all buttons to the gridpane.*/
        grid.getChildren().addAll(startButton, settingsButton, exitButton);

        /**Creating the scene. */
        startScreen = new Scene(grid, GUIConstant.WIDTH, GUIConstant.HEIGHT);

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
             //   SettingsScreen.start(primaryStage);
            }
        });

        /**Set the scene for the window and display it. */
        window.setScene(startScreen);
        window.show();
    }
}

