package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * @author Robin
 */
@SuppressWarnings("restriction")
public final class PopupMenu {

    /** Width of the PopUpMenu. */
    private static final double WIDTH = 250;
    /** Heigth of the PopUpMenu. */
    private static final double HEIGTH = 300;
    /** Size of the HBox. */
    private static final double HBOXSIZE = 20;
    /** Size of the VBox. */
    private static final double VBOXSIZE = 50;

	/** Hide public constructor. */
	private PopupMenu() { }

    /**
     * Generic method to build popups.
     * Generates a menu based on the parameters.
     * 2 mouseEvent listeners are passed to this method in order to setup
     * buttons in the popup.
     * @param t Text to be displayed in popup
     * @param button1 First button text
     * @param button2 Second button text
     * @param event First button event
     * @param event2 Second button event
     * @return A fully built popup ready to be put on the screen.
     */
    public static Popup makeMenu(final String t, final String button1,
            final String button2,
    		final EventHandler<MouseEvent> event,
    		final EventHandler<MouseEvent> event2) {

        final Popup warning = new Popup();
        warning.centerOnScreen();
        warning.setWidth(WIDTH);
        warning.setHeight(HEIGTH);

        Rectangle rect = new Rectangle(WIDTH, HEIGTH, Color.WHITESMOKE);

        Button yes = new Button(button1);
        Button no = new Button(button2);
        Style.setButtonStyle(yes, 14);
        Style.setButtonStyle(no, 14);

        Text text = new Text(t);
        text.setFill(Color.BLACK);

        HBox hbox = new HBox(HBOXSIZE, yes, no);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(VBOXSIZE, text, hbox);
        vbox.setAlignment(Pos.CENTER);

        yes.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent y) {
                warning.hide();
                event.handle(y);
            }
        });

        no.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent n) {
                warning.hide();
                event2.handle(n);
            }
        });

        warning.getContent().addAll(rect, vbox);
        return warning;
    }

    /**
     * Generic method to build popups.
     * Generates a menu based on the parameters.
     * 2 mouseEvent listeners are passed to this method in order to setup
     * buttons in the popup.
     * @param t Text to be displayed in popup
     * @param score Score to be displayed
     * @param coins Total amount of coins to be displayed
     * @param button1 First button text
     * @param button2 Second button text
     * @param event First button event
     * @param event2 Second button event
     * @return A fully built popup ready to be put on the screen.
     */
    public static Popup makeFinalMenu(final String t, final int score,
            final int coins,
    		final String button1, final String button2,
    		final EventHandler<MouseEvent> event,
    		final EventHandler<MouseEvent> event2) {

        final Popup warning = new Popup();
        warning.centerOnScreen();
        warning.setWidth(WIDTH);
        warning.setHeight(HEIGTH);


        Rectangle rect = new Rectangle(WIDTH, HEIGTH, Color.WHITESMOKE);

        Button yes = new Button(button1);
        Button no = new Button(button2);
        Text text = new Text(t);

        Style.setButtonStyle(yes, 14);
        Style.setButtonStyle(no, 14);

        String s = "Final Score: " + score;
        Text finalScore = new Text(s);
        String c = "Total amount of coins: " + coins;
        Text finalCoins = new Text(c);

        HBox hbox = new HBox(HBOXSIZE, yes, no);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(VBOXSIZE, text, finalScore, finalCoins, hbox);
        vbox.setAlignment(Pos.CENTER);

        yes.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent y) {
                warning.hide();
                event.handle(y);
            }
        });

        no.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(final MouseEvent n) {
                warning.hide();
                event2.handle(n);
            }
        });

        warning.getContent().addAll(rect, vbox);
        return warning;
    }
}
