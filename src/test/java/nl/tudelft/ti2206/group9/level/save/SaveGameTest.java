package nl.tudelft.ti2206.group9.level.save;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathias
 */
public class SaveGameTest {

    @Test
    public void testGetDefaultSaveDir() {
        assertEquals("sav/", SaveGame.getDefaultSaveDir());

        SaveGame.setDefaultSaveDir("save/");
        assertEquals("save/", SaveGame.getDefaultSaveDir());

        // Clean up after test
        SaveGame.setDefaultSaveDir("sav/");
    }

    @Test
    public void testGetSaveGames() {
        assertEquals(0, SaveGame.getSaveGames().size());
        SaveGame.getSaveGames().add("test");
        assertEquals(1, SaveGame.getSaveGames().size());
        assertEquals("test", SaveGame.getSaveGames().get(0));
    }
}
