package nl.tudelft.ti2206.group9.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import nl.tudelft.ti2206.group9.ShaftEscape;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class containing the styling for the GUI.
 * @author Maikel
 */
@SuppressWarnings("restriction")
public final class Style {

   /** BRICK material for walls, brick wall texture. */
    public static final PhongMaterial BRICK = new PhongMaterial();

    /** MOSS material for walls, mossy brick wall texture. */
    public static final PhongMaterial MOSS = new PhongMaterial();

    /** CRACK material used for walls, cracked brick wall texture. */
    public static final PhongMaterial CRACK = new PhongMaterial();

    /** FLOOR material used for floors, mossy cobblestone floor texture. */
    public static final PhongMaterial FLOOR = new PhongMaterial();

    /** COIN material used for coins, question mark box coin texture. */
    public static final PhongMaterial COIN = new PhongMaterial();

    /** WOOD material used for logs, wooden planks obstacle texture. */
    public static final PhongMaterial WOOD = new PhongMaterial();

    /** PILLAR mat. used for pillars. Stack of cracked stone bricks texture. */
    public static final PhongMaterial PILLAR = new PhongMaterial();

    /** FENCE material used for fences, mossy brick stone texture.*/
    public static final PhongMaterial FENCE = new PhongMaterial();

    /** PLAYER material used for the player. */
    public static final PhongMaterial PLAYER = new PhongMaterial();

	/** Size of a button while hovering (relative to 1). */
    private static final double BUTTON_HOVER_SCALE = 1.2;

    /** Preferred width of buttons. */
    private static final int BUTTON_WIDTH = 120;
    /** Popup font size. */
    private static final int POPUP_TEXT = 11;

    /** Hide public constructor. */
    private Style() { }

    /**
     * Method is called once to load all textures.
     * They are loaded into Phongmaterials.
     */
    public static void loadTextures() {
        final String path = "nl/tudelft/ti2206/group9/gui/";

        final Image brickTexture = new Image(path + "texture_brick.png");
        BRICK.setDiffuseMap(brickTexture);

        final Image mossTexture = new Image(path + "texture_moss.png");
        MOSS.setDiffuseMap(mossTexture);

        final Image crackTexture = new Image(path + "texture_crack.png");
        CRACK.setDiffuseMap(crackTexture);

        final Image floorTexture = new Image(path + "texture_cobblestone.png");
        FLOOR.setDiffuseMap(floorTexture);

        final Image coinTexture = new Image(path + "texture_coin.png");
        COIN.setDiffuseMap(coinTexture);

        final Image woodTexture = new Image(path + "texture_wood.png");
        WOOD.setDiffuseMap(woodTexture);

        final Image pillarTexture = new Image(path + "texture_pillar.png");
        PILLAR.setDiffuseMap(pillarTexture);

        final Image fenceTexture = new Image(path + "texture_fence.png");
        FENCE.setDiffuseMap(fenceTexture);

        final Image playerTexture = new Image(path + "texture_player.png");
        PLAYER.setDiffuseMap(playerTexture);
    }

    /**
     * Alters the looks and behaviour of a button.
     *
     * @param b Button to be styled.
     */
    public static void setButtonStyle(final Button b) {

        /** Adjusting looks of button */
    	final Color color = Color.BLACK;
    	final CornerRadii corner = new CornerRadii(4);
    	final Insets inset = new Insets(0);
    	final BackgroundFill fill = new BackgroundFill(color, corner, inset);
    	final Background buttonBack = new Background(fill);
    	final Font font = getFont(14);
        b.setTextFill(Color.WHITE);
        b.setBackground(buttonBack);
        b.setFont(font);
        b.setMinWidth(BUTTON_WIDTH);

        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent e) {
                b.setScaleX(BUTTON_HOVER_SCALE);
                b.setScaleY(BUTTON_HOVER_SCALE);
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
    	final CornerRadii corner = new CornerRadii(4);
    	final Insets inset = new Insets(0);
    	final BackgroundFill fill = new BackgroundFill(color, corner, inset);
    	final Background buttonBack = new Background(fill);
        b.setTextFill(Color.WHITE);
        b.setBackground(buttonBack);
        b.setFont(getFont(POPUP_TEXT));

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
    	final CornerRadii corner = new CornerRadii(4);
    	final Insets inset = new Insets(-4);
    	final BackgroundFill fill = new BackgroundFill(color, corner, inset);
    	final Background buttonBack = new Background(fill);
    	final Font font = getFont(14);
        l.setAlignment(Pos.CENTER);
        l.setBackground(buttonBack);
        l.setTextFill(Color.WHITE);
        l.setFont(font);
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
    			ShaftEscape.WIDTH, ShaftEscape.HEIGHT,
                true, true, true, false);
    	final BackgroundImage backgroundImage = new BackgroundImage(image,
        		BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
        		BackgroundPosition.CENTER, backgroundSize);
    	final Background background = new Background(backgroundImage);
        p.setBackground(background);
    }

    /**
     * Getting a font from the folder.
     *
     * @param size Size of text.
     * @return Font Returns a font.
     */
    public static Font getFont(final int size) {
        Font font = null;
        try {
            font = Font.loadFont(new FileInputStream(new
                   File("src/main/resources/nl/tudelft/"
                    + "ti2206/group9/gui/8bit.ttf")), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return font;
    }

}
