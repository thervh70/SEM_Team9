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
import javafx.scene.paint.PhongMaterial;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by Maikel on 03/09/2015.
 *
 * Class containing the styling for the GUI.
 */
@SuppressWarnings("restriction")
public final class Style {
	    
    /** Hide public constructor. */
    private Style() { }

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

    /**
     * Alters the looks and behaviour of a button.
     *
     * @param b Button to be styled.
     */
    public static void setButtonStyle(final Button b) {

        /** Adjusting looks of button */
    	final Color color = Color.BLACK;
    	final CornerRadii corner = new CornerRadii(3);
    	final Insets inset = new Insets(0);
    	final BackgroundFill fill = new BackgroundFill(color, corner, inset);
    	final Background buttonBack = new Background(fill);

        b.setTextFill(Color.WHITE);
        b.setBackground(buttonBack);
        b.setFont(Font.font("Roboto", FontWeight.BOLD, 14));

        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                b.setScaleX(1.2);
                b.setScaleY(1.2);
            }
        });

        /** Action to be taken on MouseExited Event. */
        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent e) {
                b.setScaleX(1);
                b.setScaleY(1);
            }
        });
    }

    /**
     * Changes the styling and behaviour of a button in a pop up.
     *
     * @param b Button to be changed.
     */
    public static void setPopupButtonStyle(final Button b) {
        /** Adjusting the looks of the button */
    	final Color color = Color.BLACK;
    	final CornerRadii corner = new CornerRadii(3);
    	final Insets inset = new Insets(0);
    	final BackgroundFill fill = new BackgroundFill(color, corner, inset);
    	final Background buttonBack = new Background(fill);
        b.setTextFill(Color.WHITE);
        b.setBackground(buttonBack);
        b.setFont(Font.font("Roboto", FontWeight.BOLD, 14));

        /** Action to be taken on MouseEntered Event */
        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent e) {
                b.setTextFill(Color.CORNFLOWERBLUE);
            }
        });

        /** Action to be taken on MouseExited Event. */
        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent e) {
                b.setTextFill(Color.WHITE);
            }
        });
    }

    /**
     * Changes the styling of a label.
     *
     * @param l Label to be changed.
     */
    public static void setLabelStyle(final Label l) {
    	final Color color = Color.BLACK;
    	final CornerRadii corner = new CornerRadii(3);
    	final Insets inset = new Insets(-4);
    	final BackgroundFill fill = new BackgroundFill(color, corner, inset);
    	final Background buttonBack = new Background(fill);
        l.setBackground(buttonBack);
        l.setTextFill(Color.WHITE);
        l.setFont(Font.font("Roboto", FontWeight.BOLD, 18));
    }

    /**
     * Sets the background of a pane.
     *
     * @param src Path to source of the imagefile.
     * @param p The pane.
     */
    public static void setBackground(final String src, final Pane p) {
    	final Image image = new Image("nl/tudelft/ti2206/group9/gui/" + src);
    	final BackgroundSize backgroundSize = new BackgroundSize(
    			GUIConstant.WIDTH, GUIConstant.HEIGHT, true, true, true, false);
    	final BackgroundImage backgroundImage = new BackgroundImage(image,
        		BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
        		BackgroundPosition.CENTER, backgroundSize);
    	final Background background = new Background(backgroundImage);
        p.setBackground(background);
    }

}
