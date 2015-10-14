package nl.tudelft.ti2206.group9.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

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
    /** Whether the server is (should be) running. */
    private static boolean running = true;

    /** The ServerSocket of this server. This is a private field, because it
     *  is accessed in two separate threads. */
    private static ServerSocket ss;

    /** Hiding public constructor. */
    private HighscoreServer() { }

    /**
     * @param args none
     * @throws IOException when something unexpected happens.
     */
    public static void main(final String... args) throws IOException {
        new Thread(new CLIThread()).start();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            ss = serverSocket;
            while (running) {
                new HighscoreServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            if (running) { // Only log if running
                logError("Could not listen on port " + PORT + ", exiting!");
            }
        }
    }

    /** Used in the test and in the CLI to quit the server. */
    static void quit() {
        running = false;
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

    /** Thread that handles the console input on the server. */
    static class CLIThread implements Runnable {
        @Override
        public void run() {
            final Scanner sc = new Scanner(System.in);
            String command;
            while (running) {
                command = sc.nextLine();
                switch (command) {
                case "stop":
                case "exit":
                case "evacuate":
                case "implode":
                    quit();
                    try {
                        ss.close();
                        log("Stopping the server.");
                    } catch (IOException e) {
                        logError("Could not stop server? Exiting anyway :D");
                    }
                    break;
                default: break;
                }
            }
            sc.close();
        }
    }

}
