package nl.tudelft.ti2206.group9.util;


import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

/**
 * This Reader decorates a Reader by decoding Base64 encrypted files.
 *
 * @author Mathias
 */
public class Base64Reader extends Reader {

    /** The Reader you decorate with this Base64Reader. */
    private final Reader reader;
    /** The Base64 decoder. */
    private final Base64.Decoder decoder = Base64.getDecoder();

    /** Constructor which sets the Reader to be decorated.
     * @param rdr the Reader to be decorated.
     */
    public Base64Reader(final Reader rdr) {
        super();
        reader = rdr;
    }

    /**
     * Read a String from a given file.
     *
     * The file to be read is passed through the internal reader. The String
     * that is read contains the entire content of the file and is decoded
     * before it is returned by this method.
     *
     * @return the decoded content of a file
     */
    public String readString() {
        final StringBuilder builder = new StringBuilder();
        String result = "";
        try {
            while (true) {
                final int readBytes = reader.read();
                if (readBytes == -1) {
                    break;
                }
                builder.append((char) readBytes);
            }
            final String encryptedString = builder.toString();
            final byte[] bytes = decoder.decode(encryptedString);
            result = new String(bytes, "UTF-8");
        } catch (IOException e) {
            GameObservable.OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.IOEXCEPTION,
                    "Base64Reader.readString(String)", e.getMessage());
        }
        return result;
    }

    @Override
    public int read(final char[] cbuf, final int off,
                    final int len) throws IOException {
        final byte[] bytes = new String(cbuf).getBytes("UTF-8");
        final byte[] decodedBytes = decoder.decode(bytes);
        final char[] decodedCbuf = new String(decodedBytes, "UTF-8")
                .toCharArray();
        return reader.read(decodedCbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
