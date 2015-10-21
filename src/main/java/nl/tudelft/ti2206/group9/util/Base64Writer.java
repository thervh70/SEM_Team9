package nl.tudelft.ti2206.group9.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Base64;

/**
 * This Writer decorates a Writer by encrypting files with Base64.
 *
 * @author Mathias
 */
public class Base64Writer extends Writer {

    /** The Writer to be decorated with this Base64Writer. */
    private Writer writer;
    /** The Base64 encoder. */
    private Base64.Encoder encoder = Base64.getEncoder();

    /** Constructor which sets the Writer to be decorated. */
    public Base64Writer(Writer wrtr) {
        writer = wrtr;
    }

    /**
     * Write an entire encoded String.
     * @param input the String to be encoded and written
     */
    public void writeString(String input) {
        try {
            String encodedInput = encoder.encodeToString(input.getBytes("UTF-8"));
            writer.write(encodedInput);
        } catch (UnsupportedEncodingException e) {
            GameObservable.OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.UNSUPPORTEDENCODINGEXCEPTION,
                    "Base64Writer.writeString()", e.getMessage());
        } catch (IOException e) {
            GameObservable.OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.IOEXCEPTION,
                    "Bas64Writer.writeString(String)", e.getMessage());
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        byte[] bytes = new String(cbuf).getBytes("UTF-8");
        String encodedCbuf = encoder.encodeToString(bytes);
        writer.write(encodedCbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
