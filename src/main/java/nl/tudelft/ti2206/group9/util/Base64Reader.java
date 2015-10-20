package nl.tudelft.ti2206.group9.util;

import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.Reader;

/**
 * This Reader decorates a Reader by decoding Base64 encrypted files.
 *
 * @author Mathias
 */
public class Base64Reader extends Reader {

    /** The Reader you decorate with this Base64Reader. */
    private Reader reader;
    /** The Base64 decoder. */
    private BASE64Decoder decoder = new BASE64Decoder();

    /** Constructor which sets the Reader to be decorated. */
    public Base64Reader(Reader rdr) {
        reader = rdr;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        char[] decodedCbuf = new char[cbuf.length - 1];
        for(int i = 0; i < decodedCbuf.length; i++) {
            byte[] array = decoder.decodeBuffer(String.valueOf(cbuf[i]));
            decodedCbuf[i] = (char) array[0];
        }
        return reader.read(decodedCbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
