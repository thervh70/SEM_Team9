package nl.tudelft.ti2206.group9.util;

import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.Writer;

/**
 * This Writer decorates a Writer by encrypting files with Base64.
 *
 * @author Mathias
 */
public class Base64Writer extends Writer {

    /** The Writer to be decorated with this Base64Writer. */
    private Writer writer;
    /** The Base64 encoder. */
    private BASE64Encoder encoder = new BASE64Encoder();

    /** Constructor which sets the Writer to be decorated. */
    public Base64Writer(Writer wrtr) {
        writer = wrtr;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        byte[] encodedBytes = encoder.encode(new String(cbuf)
                .getBytes("UTF-8")).getBytes();
        char[] encodedCbuf = new char[encodedBytes.length - 1];
        for (int i = 0; i < encodedCbuf.length; i++) {
            encodedCbuf[i] = (char) encodedBytes[i];
        }
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
