package nl.tudelft.ti2206.group9.gui; // NOPMD - many imports

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.tudelft.ti2206.group9.ShaftEscape;
import nl.tudelft.ti2206.group9.gui.scene.AbstractScene;
import nl.tudelft.ti2206.group9.gui.scene.GameScene;
import nl.tudelft.ti2206.group9.level.InternalTicker;
import nl.tudelft.ti2206.group9.level.State;
import nl.tudelft.ti2206.group9.level.Track;
import nl.tudelft.ti2206.group9.level.entity.AbstractObstacle;
import nl.tudelft.ti2206.group9.level.entity.Player;
import nl.tudelft.ti2206.group9.level.entity.PowerupInvulnerable;
import nl.tudelft.ti2206.group9.level.save.SaveGame;
import nl.tudelft.ti2206.group9.shop.CurrentItems;
import nl.tudelft.ti2206.group9.shop.ShopItemLoader;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.Logger;
import nl.tudelft.ti2206.group9.util.Point3D;

import org.junit.After;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;


@SuppressWarnings("restriction")
public class EndToEndTest extends ApplicationTest {

    /** Saved to use <pre>rootNode()</pre>. */
    private Stage stage;

    /** Multiplier for Robot sleeps. */
    private static final long TARDINESS = 10;
    /** Amount of milliseconds the Robot sleeps when sleeping "short". */
    private static final long SHORT = 2 * TARDINESS;
    /** Amount of milliseconds the Robot sleeps when sleeping "long". */
    private static final long LONG = 5 * TARDINESS;
    /** Sleep countdown. */
    private static final long COUNTDOWN = 3500;
    /** Sleep factor playerDies. */
    private static final long SLEEP_FACTOR = 2;
    /** Amount of coins for e2e. */
    private static final int COINS = 9999;

    /** Delta for double equality. */
    private static final double DELTA = 0.000001;

    private static final int MAIN_START = 0;
    private static final int MAIN_SETTINGS = 1;
//    private static final int MAIN_QUIT = 2;
    private static final int MAIN_ACCOUNTS = 3;
    private static final int MAIN_SHOP = 4;
//    private static final int MAIN_HIGHSCORE = 5;

    private static final int ACCOUNT_LOAD = 0;
    private static final int ACCOUNT_NEW = 1;
    private static final int ACCOUNT_TEXTFIELD = 2;
    private static final int ACCOUNT_LIST = 3;

    private static final int SETTINGS_BACK = 0;
    private static final int SETTINGS_SOUNDTRACK = 1;
    private static final int SETTINGS_SOUNDEFFECTS = 2;

    private static final int SHOP_BACK = 1;
    private static final int SHOP_SKIN_NOOB = 0;
    private static final int SHOP_SKIN_ANDY = 1;

    private static final int PAUSE_RESUME = 0;
    private static final int PAUSE_TOMAIN = 1;

    private static final int DEATH_RETRY = 0;
    private static final int DEATH_TOMAIN = 1;

    private static final int WARNING_OK = 0;

    @Override
    public void start(final Stage primaryStage) {
        letPlayerSurvive();
        stage = primaryStage;
        new ShaftEscape().start(stage);
        State.resetAll();
        final String saveDir = SaveGame.getDefaultSaveDir();
        final String savBackup = "old" + saveDir;
        if (new File(saveDir).exists()) {
            new File(saveDir).renameTo(new File(savBackup));
            new File(saveDir).mkdir();
        }
    }

    @After
    public void end() {
        outputEventLog();
        final String saveDir = SaveGame.getDefaultSaveDir();
        final String savBackup = "old" + saveDir;
        new File(saveDir + "Fred.ses").delete();
        if (new File(savBackup).exists()) {
            new File(saveDir).delete();
            new File(savBackup).renameTo(new File(saveDir));
            new File(savBackup).delete();
        }
    }

    private void outputEventLog() {
        try {
            ShaftEscape.LOGGER.writeToFile();
            final String log = new String(Files.readAllBytes(
                    Paths.get(Logger.OUTFILE)), StandardCharsets.UTF_8);
            // Intended use of System.out.println for Travis log
            System.out.println("\n== EVENT_LOG ==");     //NOPMD
            System.out.println(log);                     //NOPMD
            System.out.println("== END_EVENT_LOG ==\n"); //NOPMD
        } catch (IOException e) {
            fail("IOException thrown: " + e.getMessage());
        }
    }

    /**
     * Overview of the EndToEndTest:
     *
     *  - Click on screen to get passed the SplashScreen.
     *  - Go through the accounts, testing the new game button
     *      - First clicking whithout a name
     *      - Then clicking with a faulty name
     *      - Then clicking with a valid name
     *      - You are in the main menu
     *  - Go through the settings by clicking on settings button in the
     *     main menu, toggle the settings
     *  - Go through the shop, buy a currentSkin and return
     *  - Go through the gameplay
     *      - Click the pause button and resume game
     *      - Go through playermovement
     *      - Click the pause button and return to main menu
     *  - Go through account loading
     *      - Click account button in main menu
     *      - Select the account list
     *      - Click load
     *      - Return to main menu
     *  - Go through diePopup
     *      - Start game
     *      - Let player die; click retry
     *      - Let player die; click back to main
     *  - Close application
     *  - Output Log
     */
    @Test
    public void test() { //NOPMD - assert is done in subs.
        clickOn(stage, MouseButton.PRIMARY);
        sleep(SHORT);

        goThroughNameTyping();

        goThroughSettings();
        goThroughShop();

        goThroughGamePlay();

        mainMenu(MAIN_ACCOUNTS);
        accountScreen(ACCOUNT_LIST);
        accountScreen(ACCOUNT_LOAD);
        assertNotNull(State.getPlayerName());

        mainMenu(MAIN_START);
        sleep(COUNTDOWN);
        playerDies();
        sleep(SHORT);
        clickPopup(DEATH_RETRY);
        sleep(COUNTDOWN);
        playerDies();
        clickPopup(DEATH_TOMAIN);

        //        mainMenu(MAIN_QUIT);
        Platform.runLater(stage::close);
    }

    private void letPlayerSurvive() {
        PowerupInvulnerable.setCheat(true);
    }

    private void goThroughSettings() {
        mainMenu(MAIN_SETTINGS);

        // Soundtrack toggle test.
        assertTrue("Soundtrack should be enabled at startup.",
                State.isSoundtrackEnabled());
        settings(SETTINGS_SOUNDTRACK);
        assertFalse("Soundtrack disabled. (1)", State.isSoundtrackEnabled());
        settings(SETTINGS_SOUNDTRACK);
        assertTrue("Soundtrack enabled. (2)", State.isSoundtrackEnabled());
        settings(SETTINGS_SOUNDTRACK);
        assertFalse("Soundtrack disabled. (3)", State.isSoundtrackEnabled());

        // Sound effects toggle test.
        assertTrue("Sound effects should be enabled at startup.",
                State.isSoundEffectsEnabled());
        settings(SETTINGS_SOUNDEFFECTS);
        assertFalse("Sound effects disabled. (1)",
                State.isSoundEffectsEnabled());
        settings(SETTINGS_SOUNDEFFECTS);
        assertTrue("Sound effects enabled. (2)", State.isSoundEffectsEnabled());
        settings(SETTINGS_SOUNDEFFECTS);
        assertFalse("Sound effects disabled. (3)",
                State.isSoundEffectsEnabled());

        settings(SETTINGS_BACK);
    }

    private void goThroughShop() {
        State.setCoins(COINS); //Make sure player has enough coins
        mainMenu(MAIN_SHOP);

        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());
        shopBuyEquipSkin(SHOP_SKIN_NOOB);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());
        shopBuyEquipSkin(SHOP_SKIN_ANDY);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());
        shopBuyEquipSkin(SHOP_SKIN_ANDY);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getAndySkin());
        shopBuyEquipSkin(SHOP_SKIN_NOOB);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());

        shopScreen(SHOP_BACK);
    }

    private void goThroughNameTyping() {
        accountScreen(ACCOUNT_NEW);
        assertNull(AbstractScene.getPopup()); // Assert that Game does not start
        accountScreen(ACCOUNT_TEXTFIELD);
        typeFaultyName();
        accountScreen(ACCOUNT_NEW);
        clickPopup(WARNING_OK);
        accountScreen(ACCOUNT_TEXTFIELD);
        clearTextField();
        typeName();
        accountScreen(ACCOUNT_NEW);
    }

    private void goThroughGamePlay() {
        mainMenu(MAIN_START);
        sleep(COUNTDOWN);

        keyboard(KeyCode.ESCAPE);
        sleep(LONG);
        clickPopup(PAUSE_RESUME);
        sleep(COUNTDOWN);

        moveAround();

        keyboard(KeyCode.ESCAPE);
        sleep(LONG);
        pausePopup(PAUSE_TOMAIN);
    }

    private void typeName() {
        keyboard(KeyCode.CAPS);
        keyboard(KeyCode.F);
        keyboard(KeyCode.CAPS);
        keyboard(KeyCode.R);
        keyboard(KeyCode.E);
        keyboard(KeyCode.D);
    }

    private void typeFaultyName() {
        keyboard(KeyCode.SLASH);
    }

    private void moveAround() {
        final int s1 = 5 * InternalTicker.NANOS_PER_TICK / InternalTicker.E6;
        final int s2 = 75 * InternalTicker.NANOS_PER_TICK / InternalTicker.E6;
        final Point3D center = Track.getInstance().getPlayer().getCenter();
        final Point3D size = Track.getInstance().getPlayer().getSize();

        keyboard(KeyCode.LEFT);
        sleep(s1);
        assertTrue("Player moves to the left", center.getX() < 0);
        keyboard(KeyCode.RIGHT);
        sleep(s2);
        assertEquals("Player centers from the left", 0, center.getX(), DELTA);

        keyboard(KeyCode.D);
        sleep(s1);
        assertTrue("Player moves to the right", center.getX() > 0);
        keyboard(KeyCode.A);
        sleep(s2);
        assertEquals("Player centers from the right", 0, center.getX(), DELTA);

        keyboard(KeyCode.UP);
        sleep(s1);
        assertTrue("Player jumps", center.getY() > 1);
        sleep(s2);

        keyboard(KeyCode.DOWN);
        sleep(s1);
        assertTrue("Player slides", size.getY() < Player.HEIGHT);
        sleep(s2);
    }

    private void keyboard(final KeyCode kc) {
        press(kc);
        release(kc);
        sleep(SHORT);
    }

    private void mainMenu(final int buttonNo) {
        ObservableList<Node> buttons;
        buttons = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();
        clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
        letPlayerSurvive();            // Make sure there are no obstacles
        sleep(LONG);
    }

    private void settings(final int buttonNo) {
        ObservableList<Node> buttons;
        buttons = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();
        clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
        sleep(SHORT);
    }

    private void accountScreen(final int buttonNo) {
        ObservableList<Node> buttons;
        buttons = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();
        clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
        sleep(SHORT);
    }

    private void shopScreen(final int buttonNo) {
        ObservableList<Node> buttons;
        buttons = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();

        clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
        sleep(SHORT);
    }

    private void shopBuyEquipSkin(final int skinNo) {
        ObservableList<Node> gridPaneNodes;
        gridPaneNodes = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();

        final TabPane tabpane = (TabPane) gridPaneNodes.get(0);
        final ScrollPane scrollpane =
                (ScrollPane) tabpane.getTabs().get(0).getContent();
        final HBox hbox = (HBox) scrollpane.getContent();
        final VBox vbox = (VBox) hbox.getChildren().get(skinNo);

        final int buyEquip = 3; // Is the same for each currentSkin
        clickOn(vbox.getChildren().get(buyEquip), MouseButton.PRIMARY);
        sleep(SHORT);
    }

    private void pausePopup(final int buttonNo) {
        if (AbstractScene.getPopup() == null) {
            fail("The Pause Popup is not available.");
        }
        ObservableList<Node> buttons;
        buttons = ((VBox) GameScene.getPopup().getContent().get(1))
                .getChildren();
        buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
        clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
        sleep(LONG);
    }

    private void playerDies() {
        Track.getInstance().getPlayer().die();
        OBSERVABLE.notify(
                GameObserver.Category.PLAYER,
                GameObserver.Player.COLLISION,
                AbstractObstacle.class.getSimpleName());
        sleep(SLEEP_FACTOR * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
        letPlayerSurvive();            // Make sure there are no obstacles
        sleep(LONG);
    }

    private void clickPopup(final int buttonNo) {
        if (AbstractScene.getPopup() != null) {
            ObservableList<Node> buttons;
            sleep(1);
            buttons = ((VBox) AbstractScene.getPopup().getContent().get(1))
                    .getChildren();
            buttons = ((HBox) buttons.get(buttons.size() - 1)).getChildren();
            try {
                clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
            } catch (ArrayIndexOutOfBoundsException e) {
                fail("ButtonNo " + buttonNo + " does not exist");
            }
            sleep(SHORT);
        }
    }

    private void clearTextField() {
        ObservableList<Node> children;
        children = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();
        final TextField text = (TextField) children.get(ACCOUNT_TEXTFIELD);
        Platform.runLater(text::clear);
    }
}
