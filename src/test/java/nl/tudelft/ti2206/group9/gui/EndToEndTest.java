package nl.tudelft.ti2206.group9.gui; // NOPMD - many imports

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
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
import nl.tudelft.ti2206.group9.server.HighscoreServerIntegrationTest;
import nl.tudelft.ti2206.group9.shop.CurrentItems;
import nl.tudelft.ti2206.group9.shop.ShopItemLoader;
import nl.tudelft.ti2206.group9.util.GameObserver;
import nl.tudelft.ti2206.group9.util.Logger;
import nl.tudelft.ti2206.group9.util.Point3D;
import org.junit.After;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EndToEndTest extends ApplicationTest {

    /** Saved to use <pre>rootNode()</pre>. */
    private Stage stage;

    /** Multiplier for Robot sleeps. */
    private static final long TARDINESS = 10;
    /** Amount of milliseconds the Robot sleeps when sleeping "short". */
    private static final long SHORT = 2 * TARDINESS;
    /** Amount of milliseconds the Robot sleeps when sleeping "long". */
    private static final long LONG = 5 * TARDINESS;
    /** Prologe sleep time. */
    private static final long PROLOGUE = 21000;
    /** Sleep countdown. */
    private static final long SLEEP_COUNTDOWN = 3500;
    /**
     * Sleep countdown.
     */
    private static final long SLEEP_CONNECT_TIMEOUT = 6000;
    /** Sleep factor playerDies. */
    private static final long SLEEP_FACTOR = 10;
    /** Amount of coins for e2e. */
    private static final int COINS = 9999;

    /** Delta for double equality. */
    private static final double DELTA = 0.000001;

    private static final int MAIN_START = 0;
    private static final int MAIN_SETTINGS = 1;
    private static final int MAIN_QUIT = 2;
    private static final int MAIN_ACCOUNTS = 3;
    private static final int MAIN_SHOP = 4;
    private static final int MAIN_HIGHSCORES = 5;

    private static final int ACCOUNT_LOAD = 0;
    private static final int ACCOUNT_NEW = 1;
    private static final int ACCOUNT_DEL = 2;
    private static final int ACCOUNT_TEXTFIELD = 3;
    private static final int ACCOUNT_LIST = 4;

    private static final int SETTINGS_BACK = 0;
    private static final int SETTINGS_SOUNDTRACK = 1;
    private static final int SETTINGS_SOUNDEFFECTS = 2;
    private static final int SETTINGS_SOUNDEFFECT_VOLUME = 6;
    private static final int SETTINGS_SOUNDTRACK_VOLUME = 7;
    private static final int MOVE_X_SLIDER = -100;
    private static final int MOVE_Y_SLIDER = 0;

    private static final int HIGHSCORES_BACK = 0;
    private static final int HIGHSCORES_UPDATE = 1;
    private static final int HIGHSCORES_INPUT = 2;
    private static final int HIGHSCORES_TABPANE = 3;

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
        HighscoreServerIntegrationTest.setUpBeforeClass();
    }

    @After
    public void end() {
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

        final String saveDir = SaveGame.getDefaultSaveDir();
        final String savBackup = "old" + saveDir;
        new File(saveDir + "Fred.ses").delete();
        if (new File(savBackup).exists()) {
            new File(saveDir).delete();
            new File(savBackup).renameTo(new File(saveDir));
            new File(savBackup).delete();
        }
        HighscoreServerIntegrationTest.tearDownAfterClass();
    }

    /**
     * Overview of the EndToEndTest:
     *
     *  - Click on screen to get passed the SplashScreen.
     *  - Go through the accounts, testing the new game button
     *      - First clicking without a name
     *      - Then clicking with a faulty name
     *      - Then clicking with a valid name
     *      - You are in the main menu
     *  ! ! ! Every other goThrough method begins and ends in the main menu.
     *  - Go through the settings by clicking on settings button in the
     *     main menu, toggle the settings
     *  - Go through the shop
     *      - Buy the first skin in the shop
     *      - Buy the second skin in the shop
     *      - Equip the second skin in the shop
     *      - Equip the first skin in the shop
     *      - Return to main menu
     *  - Go through the gameplay
     *      - Click the pause button and resume game
     *      - Go through Player movement
     *          - Move Left, Right, Right, Left
     *          - Jump, slide
     *      - Click the pause button and return to main menu
     *  - Go through account loading
     *      - Click account button in main menu
     *      - Select the account list
     *      - Click load
     *      - Return to main menu
     *  - Go through DeathPopup
     *      - Start game
     *      - Let player die; click retry
     *      - Let player die; click back to main menu
     *  - Go through Highscores
     *      - Try to connect to IP "l"
     *      - Click OK in Warning
     *      - Try to connect to IP "localhost"
     *      - Return to main menu
     *  - Click Quit
     */
    @Test
    public void test() { //NOPMD - assert is done in subs.
        clickOn(stage, MouseButton.PRIMARY);
        sleep(SHORT);

        goThroughAccounts1();
        goThroughGamePlay();
        goThroughSettings();
        goThroughShop();
        goThroughAccounts2();
        goThroughDeathPopup();
        goThroughHighscores();
        goThroughAccounts3();

        clickButton(MAIN_QUIT);
    }

    private void goThroughAccounts1() {
        clickButton(ACCOUNT_NEW);
        assertNull(AbstractScene.getPopup()); // Button disabled, so no popup
        clickButton(ACCOUNT_TEXTFIELD);
        typeFaultyName();
        clickButton(ACCOUNT_NEW);
        clickPopup(WARNING_OK);
        clickButton(ACCOUNT_TEXTFIELD);
        clearTextField(ACCOUNT_TEXTFIELD);
        typeName();
        clickButton(ACCOUNT_NEW);
        sleep(PROLOGUE);
        keyboard(KeyCode.ENTER); //Sleep during prologue and dismiss
    }

    private void goThroughSettings() {
        clickButton(MAIN_SETTINGS);

        // Soundtrack toggle test.
        assertTrue("Soundtrack should be enabled at startup.",
                State.isSoundtrackEnabled());
        clickButton(SETTINGS_SOUNDTRACK);
        assertFalse("Soundtrack disabled. (1)", State.isSoundtrackEnabled());
        clickButton(SETTINGS_SOUNDTRACK);
        assertTrue("Soundtrack enabled. (2)", State.isSoundtrackEnabled());
        // Soundtrack slider test.
        assertEquals(1.0 / 2.0, State.getSoundtrackVolume(), DELTA);
        settings(SETTINGS_SOUNDTRACK_VOLUME);
        assertEquals(0.0, State.getSoundtrackVolume(), DELTA);

        // Sound effects toggle test.
        assertTrue("Sound effects should be enabled at startup.",
                State.isSoundEffectsEnabled());
        clickButton(SETTINGS_SOUNDEFFECTS);
        assertFalse("Sound effects disabled. (1)",
                State.isSoundEffectsEnabled());
        clickButton(SETTINGS_SOUNDEFFECTS);
        assertTrue("Sound effects enabled. (2)", State.isSoundEffectsEnabled());
        // Sound effect slider test.
        assertEquals(1.0 / 2.0, State.getSoundEffectVolume(), DELTA);
        settings(SETTINGS_SOUNDEFFECT_VOLUME);
        assertEquals(0.0, State.getSoundEffectVolume(), DELTA);

        clickButton(SETTINGS_BACK);
    }

    private void goThroughShop() {
        State.setCoins(COINS); //Make sure player has enough coins
        clickButton(MAIN_SHOP);

        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());
        shopBuyEquipSkin(SHOP_SKIN_NOOB);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());
        shopBuyEquipSkin(SHOP_SKIN_ANDY);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());
        shopBuyEquipSkin(SHOP_SKIN_ANDY);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getAndySkin());
        shopBuyEquipSkin(SHOP_SKIN_NOOB);
        assertEquals(CurrentItems.getSkin(), ShopItemLoader.getNoobSkin());

        clickButton(SHOP_BACK);
    }

    private void goThroughGamePlay() {
        clickButton(MAIN_START);
        letPlayerSurvive();            // Stop E2E from failing by collision
        sleep(SLEEP_COUNTDOWN);

        keyboard(KeyCode.ESCAPE);
        sleep(LONG);
        clickPopup(PAUSE_RESUME);
        sleep(SLEEP_COUNTDOWN);

        moveAround();

        keyboard(KeyCode.ESCAPE);
        sleep(LONG);
        pausePopup(PAUSE_TOMAIN);
    }

    private void goThroughAccounts2() {
        clickButton(MAIN_ACCOUNTS);
        clickButton(ACCOUNT_LIST);
        clickButton(ACCOUNT_LOAD);
        assertNotNull(State.getPlayerName());
    }

    private void goThroughDeathPopup() {
        clickButton(MAIN_START);
        letPlayerSurvive();            // Stop E2E from failing by collision
        sleep(SLEEP_COUNTDOWN);
        playerDies();
        sleep(SHORT);
        clickPopup(DEATH_RETRY);
        letPlayerSurvive();            // Stop E2E from failing by collision
        sleep(SLEEP_COUNTDOWN);
        playerDies();
        clickPopup(DEATH_TOMAIN);
    }

    private void goThroughAccounts3() {
        clickButton(MAIN_ACCOUNTS);
        clickButton(ACCOUNT_LIST);
        clickButton(ACCOUNT_DEL);
        clickButton(ACCOUNT_TEXTFIELD);
        typeName();
        clickButton(ACCOUNT_NEW);
        sleep(PROLOGUE);
        keyboard(KeyCode.ENTER);
        sleep(SLEEP_COUNTDOWN);
        keyboard(KeyCode.ESCAPE);
        playerDies();
        clickPopup(DEATH_TOMAIN);
    }

    private void goThroughHighscores() {
        clickButton(MAIN_HIGHSCORES);

        clickButton(HIGHSCORES_INPUT);
        clearTextField(HIGHSCORES_INPUT);
        sleep(LONG);
        keyboard(KeyCode.L);
        clickButton(HIGHSCORES_UPDATE);
        sleep(SLEEP_CONNECT_TIMEOUT);
        clickPopup(WARNING_OK);

        clickButton(HIGHSCORES_INPUT);
        clearTextField(HIGHSCORES_INPUT);
        sleep(LONG);
        typeLocalhost();
        clickButton(HIGHSCORES_UPDATE);
        sleep(LONG);

        final TabPane pane = (TabPane) rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable().get(HIGHSCORES_TABPANE);
        final ListView<?> list =
                (ListView<?>) pane.getTabs().get(0).getContent();
        final String firstItem = (String) list.getItems().get(0);
        assertEquals("Fred", firstItem.substring(0, "Fred".length()));

        clickButton(HIGHSCORES_BACK);
    }

    private void typeName() {
        keyboard(KeyCode.CAPS);
        keyboard(KeyCode.F);
        keyboard(KeyCode.CAPS);
        keyboard(KeyCode.R);
        keyboard(KeyCode.E);
        keyboard(KeyCode.D);
    }

    private void typeLocalhost() {
        keyboard(KeyCode.L);
        keyboard(KeyCode.O);
        keyboard(KeyCode.C);
        keyboard(KeyCode.A);
        keyboard(KeyCode.L);
        keyboard(KeyCode.H);
        keyboard(KeyCode.O);
        keyboard(KeyCode.S);
        keyboard(KeyCode.T);
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

    private void settings(final int buttonNo) {
        ObservableList<Node> buttons;
        buttons = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();
        if (buttonNo <= SETTINGS_SOUNDEFFECTS) {
            clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
        } else {
            clickOn(buttons.get(buttonNo), MouseButton.PRIMARY);
            press(MouseButton.PRIMARY);
            moveBy(MOVE_X_SLIDER, MOVE_Y_SLIDER);
            release(MouseButton.PRIMARY);
        }
        sleep(SHORT);
    }

    private void playerDies() {
        Track.getInstance().getPlayer().die();
        OBSERVABLE.notify(
                GameObserver.Category.PLAYER,
                GameObserver.Player.COLLISION,
                AbstractObstacle.class.getSimpleName());
        sleep(SLEEP_FACTOR * InternalTicker.NANOS_PER_TICK / InternalTicker.E6);
    }

    private void letPlayerSurvive() {
        new PowerupInvulnerable(Point3D.ZERO).setCheat(true);
    }

    private void clickButton(final int buttonNo) {
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

    private void clickPopup(final int buttonNo) {
        if (AbstractScene.getPopup() == null) {
            fail("There is no popup available in AbstractScene!");
        } else {
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

    private void clearTextField(final int buttonNo) {
        ObservableList<Node> children;
        children = rootNode(stage).getScene().getRoot()
                .getChildrenUnmodifiable();
        final TextField text = (TextField) children.get(buttonNo);
        Platform.runLater(text::clear);
    }
}
