package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * @author Robin
 */
@SuppressWarnings("restriction")
public final class PopupMenu {

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
    public static Popup makeMenu(String t, String button1, String button2, 
    		final EventHandler<MouseEvent> event, 
    		final EventHandler<MouseEvent> event2) {

        final Popup warning = new Popup();
        warning.centerOnScreen();
        warning.setWidth(250);
        warning.setHeight(300);
        
        Rectangle rect = new Rectangle(250, 300, Color.WHITESMOKE);

        Button yes = new Button(button1);
        Button no = new Button(button2);
        Text text = new Text(t);
        text.setFill(Color.BLACK);

        HBox hbox = new HBox(20, yes, no);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(50, text, hbox);
        vbox.setAlignment(Pos.CENTER);

        yes.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent y) {
                warning.hide();
                event.handle(y);
            }
        });

        no.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent n) {
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
    public static Popup makeFinalMenu(String t, int score, int coins, 
    		String button1, String button2, 
    		final EventHandler<MouseEvent> event, 
    		final EventHandler<MouseEvent> event2) {

        final Popup warning = new Popup();
        warning.centerOnScreen();
        warning.setWidth(250);
        warning.setHeight(300);

        Rectangle rect = new Rectangle(250, 300, Color.WHITESMOKE);

        Button yes = new Button(button1);
        Button no = new Button(button2);
        Text text = new Text(t);

        String s = "Final Score: " + score;
        Text finalScore = new Text(s);
        String c = "Total amount of coins: " + coins;
        Text finalCoins = new Text(c);

        HBox hbox = new HBox(20,yes,no);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(50, text, finalScore, finalCoins, hbox);
        vbox.setAlignment(Pos.CENTER);

        yes.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent y) {
                warning.hide();
                event.handle(y);
            }
        });

        no.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent n) {
                warning.hide();
                event2.handle(n);
            }
        });

        warning.getContent().addAll(rect, vbox);
        return warning;
    }
}
