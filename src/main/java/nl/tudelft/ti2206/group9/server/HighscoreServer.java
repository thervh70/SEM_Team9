package nl.tudelft.ti2206.group9.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server that stores all highscores. Is a Command-Line application.
 * @author Maarten
 */
public final class HighscoreServer {

    /**
     * Port used for this server. 42042 is an unregistered port, according
     * to the IANA, the service that registers ports used for applications.
     * It might unofficially be used, but this server is also unofficial ;)
     * https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers
     *                                           #Registered_ports
     * https://www.iana.org/assignments/service-names-port-numbers/
     *                                           service-names-port-numbers.txt
     */
    public static final int PORT = 42042;

    /** Hiding public constructor. */
    private HighscoreServer() { }

    /**
     * @param args none
     * @throws IOException when something unexpected happens.
     */
    public static void main(final String... args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new HighscoreServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            logError("Could not listen on port " + PORT);
            System.exit(-1); // NOPMD - Command-line Interface
        }
    }

    /**
     * Logs the string <pre>message</pre> to the System.err stream.
     * @param message message to be printed to System.err.
     */
    static void logError(final String message) {
        System.err.println(message); // NOPMD - Command-line Interface
    }

    /**
     * Logs the string <pre>message</pre> to the System.out stream.
     * @param message message to be printed to System.out.
     */
    static void log(final String message) {
        System.out.println(message); // NOPMD - Command-line Interface
    }

}
