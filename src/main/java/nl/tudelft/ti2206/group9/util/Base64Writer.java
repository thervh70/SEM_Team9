package nl.tudelft.ti2206.group9.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * This Writer decorates a Writer by encrypting files with Base64.
 *
 * @author Mathias
 */
public class Base64Writer extends Writer {

    /** The Writer to be decorated with this Base64Writer. */
    private final Writer writer;
    /** The Base64 encoder. */
    private final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * Constructor which sets the Writer to be decorated.
     * @param wrtr the Writer to be decorated
     */
    public Base64Writer(final Writer wrtr) {
        super();
        writer = wrtr;
    }

    /**
     * Constructor which sets the Writer to be decorated.
     * @param stream the Stream to create a Writer for
     */
    public Base64Writer(final OutputStream stream) {
        super();
        writer = new BufferedWriter(
                new OutputStreamWriter(stream, StandardCharsets.UTF_8));
    }

    /**
     * Write an entire encoded String.
     *
     * The given input String is first encoded and then written to a file.
     * The file is given by the internal Writer.
     *
     * @param input the String to be encoded and written
     */
    public void writeString(final String input) {
        try {
            final String encodedInput =
                    encoder.encodeToString(input.getBytes("UTF-8"));
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
    public void write(final char[] cbuf, final int off,
                      final int len) throws IOException {
        final byte[] bytes = new String(cbuf).getBytes("UTF-8");
        final String encodedCbuf = encoder.encodeToString(bytes);
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
