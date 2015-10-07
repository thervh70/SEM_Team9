package nl.tudelft.ti2206.group9.util;

import static nl.tudelft.ti2206.group9.ShaftEscape.OBSERVABLE;
import sun.misc.BASE64Decoder; //NOPMD
import sun.misc.BASE64Encoder; //NOPMD

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * This class handles all encryption and decryption
 * of the JSON savefiles.
 * The encryption this class is performing is based on the Unicode
 * value of each character. Instead of writing the character to a
 * savefile, the Unicode is written to the file, making it hard(er)
 * to read manually.
 * @author Mathias
 */
public final class Security {

    /** Private constructor. */
    private Security() { }

    /**
     * Encrypt a given String.
     * @param input the String to be encrypted
     * @return the encrypted version of the input
     */
    public static String encrypt(final String input) {
        final BASE64Encoder encoder = new BASE64Encoder();
        try {
            final byte[] utf8 = input.getBytes("UTF8");
            return encoder.encode(utf8);
        } catch (UnsupportedEncodingException e) {
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.UNSUPPORTEDENCODINGEXCEPTION,
                    "Security.encrypt()", e.getMessage());
            return null;
        }
    }

    /**
     * Decrypt a given String.
     * @param input the String to be decrypted
     * @return the decrypted version of the input
     */
    public static String decrypt(final String input) {
        final BASE64Decoder decoder = new BASE64Decoder();
        try {
            final byte[] decodedBytes = decoder.decodeBuffer(input);
            return new String(decodedBytes, "UTF8");
        } catch (IOException e) {
            OBSERVABLE.notify(GameObserver.Category.ERROR,
                    GameObserver.Error.IOEXCEPTION,
                    "Security.decode()", e.getMessage());
            return null;
        }
    }
}
