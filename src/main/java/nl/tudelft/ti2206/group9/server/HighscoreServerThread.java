package nl.tudelft.ti2206.group9.server;

import static nl.tudelft.ti2206.group9.server.HighscoreDatabase.query;
import static nl.tudelft.ti2206.group9.server.HighscoreServer.log;
import static nl.tudelft.ti2206.group9.server.HighscoreServer.logError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Thread spawned by HighscoreServer every time a client connects.
 * @author Maarten
 */
public class HighscoreServerThread implements Runnable {

    /** Amount of spaces needed to pad a console log. */
    // private static final int PADDING_SPACES = 25; // Length of IP + 2
    /** The actual spaces that pad console logs. */
    private static final String PAD_STRING = "    ";
            // new String(new char[PADDING_SPACES]).replace("\0", " ");

    /** Socket connected to. */
    private final Socket socket;
    /** Text stream to client. */
    private PrintWriter toClient;
    /** Text stream from client. */
    private BufferedReader fromClient;
    /** The IP of the client. */
    private String clientIP = "<no IP yet>";

    /**
     * Default constructor.
     * @param connection Socket to connect to.
     */
    public HighscoreServerThread(final Socket connection) {
        socket = connection;
    }

    @Override
    public void run() {
        try {
            toClient = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), "UTF-8"), true);
            fromClient = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "UTF-8"));
            clientIP = socket.getRemoteSocketAddress().toString();
            padIP();
            log(clientIP + " is connected to me ("
                    + socket.getLocalSocketAddress().toString() + ")");

            boolean stayAlive = true;
            while (stayAlive) {
                stayAlive = communicate();
            }
            socket.close();
            log(clientIP + " disconnected.");
        } catch (IOException e) {
            logError("The socket connected to " + clientIP
                    + " had an IOException", e);
        }
    }

    /**
     * @return whether the communication is still alive.
     * @throws IOException when it could not read from the client.
     */
    private boolean communicate() throws IOException {
        final String from = fromClient.readLine();
        if (from == null || from.equals("exit")) {
            toClient.println("exit");
            return false;
        }
        String to = query(from);
        toClient.println(to);
        if (to.contains("\n")) {
            to = to.replaceAll("\n", "\n  " + PAD_STRING);
        }
        log(clientIP + " " + from + " ->\n  " + PAD_STRING + to);
        return true;
    }

    /** Pads the client's IP address with zeroes to enhance readability. */
    private void padIP() {
        try {
            final StringBuffer out = new StringBuffer();
            final Scanner sc = new Scanner(clientIP);
            sc.skip("/");
            out.append('/');
            sc.useDelimiter("\\.");
            for (int i = 0; i <= 2; i++) {
                out.append(String.format("%03d", sc.nextInt())).append('.');
            }
            sc.skip("\\.");
            sc.useDelimiter(":");
            out.append(String.format("%03d", sc.nextInt())).append(':')
            .append(String.format("%05d", sc.nextInt()));
            sc.close();
            clientIP = out.toString();
        } catch (InputMismatchException e) {
            logError("IP " + clientIP + " could not be padded");
        }
    }
}
