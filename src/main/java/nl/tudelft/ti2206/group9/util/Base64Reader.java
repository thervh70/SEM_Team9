package nl.tudelft.ti2206.group9.util;


import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * This Reader decorates a Reader by decoding Base64 encrypted files.
 *
 * @author Mathias
 */
public class Base64Reader extends Reader {

    /** The Reader you decorate with this Base64Reader. */
    private Reader reader;
    /** The Base64 decoder. */
    private Base64.Decoder decoder = Base64.getDecoder();

    /** Constructor which sets the Reader to be decorated. */
    public Base64Reader(Reader rdr) {
        reader = rdr;
    }

    public String readString(String input) {
        byte[] bytes = decoder.decode(input);
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            GameObservable.OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.UNSUPPORTEDENCODINGEXCEPTION,
                    "Base64Reader.readString(String)", e.getMessage());
        }
        return "";
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        byte[] bytes = new String(cbuf).getBytes("UTF-8");
        byte[] decodedBytes = decoder.decode(bytes);
        char[] decodedCbuf = new String(decodedBytes, "UTF-8").toCharArray();
        return reader.read(decodedCbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
