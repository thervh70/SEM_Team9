package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by Maikel on 03/09/2015.
 *
 * Class containing the styling for the GUI.
 */
public class Style {

    public static final PhongMaterial BRICK = new PhongMaterial();
    public static final PhongMaterial MOSS = new PhongMaterial();
    public static final PhongMaterial CRACK = new PhongMaterial();
    public static final PhongMaterial FLOOR = new PhongMaterial();
    public static final PhongMaterial COIN = new PhongMaterial();
    public static final PhongMaterial WOOD = new PhongMaterial();

    public static void loadTextures() {
        Image brickTexture = new Image("texture_brick.png");
        BRICK.setDiffuseMap(brickTexture);

        Image mossTexture = new Image("texture_moss.png");
        MOSS.setDiffuseMap(mossTexture);

        Image crackTexture = new Image("texture_crack.png");
        CRACK.setDiffuseMap(crackTexture);

        Image floorTexture = new Image("texture_cobblestone.png");
        FLOOR.setDiffuseMap(floorTexture);

        Image coinTexture = new Image("texture_coin.png");
        COIN.setDiffuseMap(coinTexture);

        Image woodTexture = new Image("texture_wood.png");
        WOOD.setDiffuseMap(woodTexture);
    }


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
        BackgroundSize backgroundSize = new BackgroundSize(480, 640, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        p.setBackground(background);
    }


}