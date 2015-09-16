package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by Maikel on 03/09/2015.
 *
 * Class containing the styling for the GUI.
 */
@SuppressWarnings("restriction")
public final class Style {

	private Style() { }

    public static void setButtonStyle(final Button b, double size) {
        Color color = Color.BLACK;
        CornerRadii corner = new CornerRadii(3);
        Insets inset = new Insets(0);
        BackgroundFill fill = new BackgroundFill(color, corner, inset);
        Background buttonBack = new Background(fill);
        b.setTextFill(Color.WHITE);
        b.setBackground(buttonBack);
        b.setFont(Font.font("Roboto", FontWeight.BOLD, 14));


                b.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        b.setScaleX(1.2);
                        b.setScaleY(1.2);
                    }
                });

        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                b.setScaleX(1);
                b.setScaleY(1);
            }
        });
    }

    public static void setPopupButtonStyle(final Button b, double size){
        Color color = Color.BLACK;
        CornerRadii corner = new CornerRadii(3);
        Insets inset = new Insets(0);
        BackgroundFill fill = new BackgroundFill(color, corner, inset);
        Background buttonBack = new Background(fill);
        b.setTextFill(Color.WHITE);
        b.setBackground(buttonBack);
        b.setFont(Font.font("Roboto", FontWeight.BOLD, 14));

        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                b.setTextFill(Color.CORNFLOWERBLUE);

            }
        });

        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                b.setTextFill(Color.WHITE);
            }
        });

    }

    public static void setLabelStyle(Label l, int size){
        Color color = Color.BLACK;
        CornerRadii corner = new CornerRadii(3);
        Insets inset = new Insets(-4);
        BackgroundFill fill = new BackgroundFill(color, corner, inset);
        Background buttonBack = new Background(fill);
        l.setBackground(buttonBack);
        l.setTextFill(Color.WHITE);
        l.setFont(Font.font("Roboto", FontWeight.BOLD, 18));

    }

    public static void setBackground(String src, Pane p){
        Image image = new Image(src);
        BackgroundSize backgroundSize = new BackgroundSize(
        		GUIConstant.WIDTH, GUIConstant.HEIGHT, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, 
        		BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, 
        		BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        p.setBackground(background);
    }


}