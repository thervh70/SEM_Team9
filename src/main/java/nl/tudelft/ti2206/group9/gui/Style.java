package nl.tudelft.ti2206.group9.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.print.DocFlavor;
import javax.xml.soap.Node;

/**
 * Created by Maikel on 03/09/2015.
 *
 * Class containing the styling for the GUI.
 */
public class Style {


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
                        b.setScaleX(1.3);
                        b.setScaleY(1.3);
                    }
                });

        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                b.setScaleX(1);
                b.setScaleY(1);
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
        BackgroundSize backgroundSize = new BackgroundSize(480, 640, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        p.setBackground(background);
    }


}