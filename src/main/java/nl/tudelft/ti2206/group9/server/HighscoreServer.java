package nl.tudelft.ti2206.group9.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

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
    /** The Logger of this server, sends output to the console. */
    private static final Logger LOGGER = Logger.getLogger(
            HighscoreServer.class.getName());
    /** Whether the server is (should be) running. */
    private static boolean running = true;

    /** The ServerSocket of this server. This is a private field, because it
     *  is accessed in two separate threads. */
    private static ServerSocket serverSocket;
    /** The CLIThread of this server, handles console input. */
    private static CLIThread cliThread;

    /** Hiding public constructor. */
    private HighscoreServer() { }

    /**
     * @param args none
     * @throws IOException when something unexpected happens.
     */
    public static void main(final String... args) throws IOException {
        final Logger global = Logger.getLogger("");
        global.removeHandler(global.getHandlers()[0]);
        global.addHandler(new StdOutConsoleHandler());
        cliThread = new CLIThread();
        new Thread(cliThread, "CLIThread").start();
        log("Type \"stop\" or \"q\" to exit.");
        try (ServerSocket sock = new ServerSocket(PORT)) {
            serverSocket = sock;
            log("Server is now accepting clients.");
            while (running) {
                final Socket socket = serverSocket.accept();
                new Thread(new HighscoreServerThread(socket), "HsServerThread"
                        + socket.getRemoteSocketAddress().toString()).start();
            }
        } catch (IOException e) {
            if (running) { // Only log if running
                logError("Could not listen on port " + PORT + ", exiting!");
                cliThread.handleCommand("stop");
            }
        }
    }

    /** Used in the test and in the CLI to quit the server. */
    static void quit() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logError("IOException while stopping the server, exiting anyway.");
        }
    }

    /**
     * Logs the string <pre>message</pre> to the logger.
     * @param message message to be printed to the logger.
     */
    static void logError(final String message) {
        LOGGER.log(Level.WARNING, message);
    }

    /**
     * Logs the string <pre>message</pre> to the logger.
     * @param message message to be printed to the logger.
     * @param e Throwable to be printed.
     */
    static void logError(final String message, final Throwable e) {
        LOGGER.log(Level.SEVERE, message, e);
    }

    /**
     * Logs the string <pre>message</pre> to the logger.
     * @param message message to be printed to the logger.
     */
    static void log(final String message) {
        LOGGER.log(Level.INFO, message);
    }

    /** Thread that handles the console input on the server. */
    static class CLIThread implements Runnable {
        @Override
        public void run() {
            final Scanner sc = new Scanner(System.in, "UTF-8");
            String command;
            while (running) {
                command = sc.nextLine();
                handleCommand(command);
            }
            sc.close();
        }

        /**
         * @param command the command that is entered by the user.
         */
        private void handleCommand(final String command) {
            switch (command) {
            case "q":
            case "stop":
                quit();
                log("Server has been stopped.");
                break;
            default: break;
            }
        }
    }

    private static class StdOutConsoleHandler extends ConsoleHandler {
        public StdOutConsoleHandler() {
            setOutputStream(System.out);
        }
    }

}
