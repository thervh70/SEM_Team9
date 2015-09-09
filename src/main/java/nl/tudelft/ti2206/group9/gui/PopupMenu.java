package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * @author Robin
 */
public class PopupMenu {

    public static Popup makeWarning(String t, final Pane root, final EventHandler<MouseEvent> event, final EventHandler<MouseEvent> event2) {
        root.setDisable(true);

        final Popup warning = new Popup();
        warning.centerOnScreen();
        warning.setWidth(100);
        warning.setHeight(300);
        
        Rectangle rect = new Rectangle(300, 100, Color.WHITESMOKE);=

        Text text = new Text(t);
        text.setFill(Color.BLACK);

        Button yes = new Button("Yes");

        Button no = new Button("No");

        HBox hbox = new HBox(20,yes,no);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(50, text, hbox);
        vbox.setAlignment(Pos.CENTER);

        yes.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent y){
                warning.hide();
                event.handle(y);
            }
        });

        no.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent n){
                warning.hide();
                event2.handle(n);
                root.setDisable(false);
            }
        });

        warning.getContent().addAll(rect, vbox);
        return warning;
    }
}
