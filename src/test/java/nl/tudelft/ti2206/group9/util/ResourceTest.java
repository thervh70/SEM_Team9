package nl.tudelft.ti2206.group9.util;

import static nl.tudelft.ti2206.group9.util.GameObservable.OBSERVABLE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.tudelft.ti2206.group9.util.GameObserver.Category;
import nl.tudelft.ti2206.group9.util.GameObserver.Error;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class ResourceTest {

    private static final String TEST_FOLDER = "nl/tudelft/ti2206/group9/util";
    private static final String TEST_FILE = "resourceTest.txt";
    private static final String TEST_PATH = TEST_FOLDER + "/" + TEST_FILE;

    private static final String FAKE_PATH = "com/com/com";

    private GameObserver obs = mock(GameObserver.class);

    @Before
    public final void before() {
        OBSERVABLE.addObserver(obs);
    }

    @After
    public final void after() {
        OBSERVABLE.deleteObserver(obs);
    }

    @Test
    public final void testGetURL() {
        assertTrue(Resource.getURL(TEST_PATH).toString().endsWith(TEST_PATH));
        assertNull(Resource.getURL(FAKE_PATH));
        verify(obs).update(Matchers.refEq(OBSERVABLE), Matchers.refEq(
                new GameObserver.GameUpdate(
                Category.ERROR, Error.RESOURCEEXCEPTION,
                new Object[]{"Resource.getURL(String)", "getResource() == null",
                        FAKE_PATH})));
    }

    @Test
    public final void testGetURI() {
        assertTrue(Resource.getURI(TEST_PATH).toString().endsWith(TEST_PATH));
    }

    @Test
    public final void testGetURIString() {
        assertTrue(Resource.getURIString(TEST_PATH)
                .toString().endsWith(TEST_PATH));
    }

    @Test
    public final void testGetFolder() {
        final List<String> list = Resource.getFolder(TEST_FOLDER);
        for (final String entry : list) {
            if (entry.equals(TEST_FILE)) {
                return;
            }
        }
        fail("Test file not found in folder!");
    }

    @Test
    public final void testGetFakeFolder() {
        assertNull(Resource.getFolder(TEST_PATH));
    }

    @Test
    public final void testGetFakeFolder2() {
        assertNull(Resource.getFolder(FAKE_PATH));
    }

    @Test
    public final void testGetStream() {
        final InputStream stream = Resource.getStream(TEST_PATH);
        final String expString = "Reading test input! Yay!";
        final byte[] actual = new byte[expString.length()];
        final byte[] expected = expString.getBytes();
        try {
            stream.read(actual, 0, expString.length());
        } catch (IOException e) {
            fail("Could not read from Resource InputStream");
        }
        assertArrayEquals(expected, actual);
    }

    @Test
    public final void testGetFakeStream() {
        final InputStream stream = Resource.getStream(FAKE_PATH);
        assertNull(stream);
    }

}
