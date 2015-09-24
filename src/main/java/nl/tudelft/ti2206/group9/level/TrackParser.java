package nl.tudelft.ti2206.group9.level;

import nl.tudelft.ti2206.group9.entities.AbstractEntity;
import nl.tudelft.ti2206.group9.entities.Coin;
import nl.tudelft.ti2206.group9.entities.Fence;
import nl.tudelft.ti2206.group9.entities.Log;
import nl.tudelft.ti2206.group9.entities.Pillar;
import nl.tudelft.ti2206.group9.util.Point3D;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias
 */
public class TrackParser {

    /** Standard Y-value of the center of a Fence. */
    public static final double FENCE_CENTER_HEIGHT = 2.8;

    /**
     * Parse all txt-files into TrackParts.
     * @return List of TrackParts
     */
    public final List<TrackPart> parseTrack() {
        final List<TrackPart> partList = new ArrayList<TrackPart>();

        final File folder =
        		new File("src/main/resources/nl/tudelft/ti2206/group9/level");
        for (final File file : folder.listFiles()) {
            try {
            	final TrackPart part = parseTrackPart(file.getPath());
                partList.add(part);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return partList;
    }

    /**
     * Create a TrackPart by reading the trackpart textfile.
     * @param infile path to the textfile
     * @return TrackPart the created TrackPart
     * @throws IOException IOException
     */
    public final TrackPart parseTrackPart(final String infile)
    		throws IOException {
    	final URL path = new File(infile).toURI().toURL();
    	final InputStream stream = path.openStream();
        return parseTrackPart(stream);
    }

    /**
     * Create a TrackPart by reading an inputstream.
     * @param stream inputstream to be used
     * @return TrackPart the created TrackPart
     * @throws IOException IOException
     */
    protected final TrackPart parseTrackPart(final InputStream stream)
    		throws IOException {
    	final BufferedReader reader = new BufferedReader(
        		new InputStreamReader(stream, "UTF-8"));
    	final List<String> lines = new ArrayList<String>();
        while (reader.ready()) {
            lines.add(reader.readLine());
        }

        return parseTrackPart(lines);
    }

    /**
     * Create a TrackPart by reading a List of Strings (lines).
     * @param text the List of Strings to be used
     * @return TrakPart the created TrackPart
     */
    protected final TrackPart parseTrackPart(final List<String> text) {
        checkFormat(text);

        final int height = text.size();
        final int width = text.get(0).length();

        final char[][] map = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = text.get(y).charAt(x);
            }
        }
        return parseTrackPart(map);
    }

    /**
     * Check the format of the given text.
     * If th format is not right, it throws an exception
     * @param text the text to be checked
     */
    protected final void checkFormat(final List<String> text) {
        if (text == null || text.isEmpty()) {
            throw new NullPointerException("text is null"); //NOPMD
        }
    }

    /**
     * Create a TrackPart by reading a nested array of characters.
     * @param map the nested array of characters to be used
     * @return TrackPart the created TrackPart
     */
    // NOPMD used because PMD wants map to be a vararg parameter
    protected final TrackPart parseTrackPart(final char[][] map) { //NOPMD
    	final TrackPart part = new TrackPart();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
            	final char c = map[i][j];
                AbstractEntity entity;
                switch (c) { //NOPMD - default case contains no break, duh.
                case 'c': entity = new Coin(new Point3D(i - 1, 1, j)); break;
                case 'l': entity = new Log(new Point3D(i - 1, 1, j)); break;
                case 'p': entity = new Pillar(new Point3D(i - 1, 1, j)); break;
                case 'f': entity = new Fence(new Point3D(i - 1,
                    			FENCE_CENTER_HEIGHT, j)); break;
                default : continue;
                }
                part.addEntity(entity);
            }
        }
        part.setLength(map[0].length);
        return part;
    }
}
