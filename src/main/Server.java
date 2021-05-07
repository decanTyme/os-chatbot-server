package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple implementation of client-server communication for the server-side
 * interface. When run in the command line, it accepts one argument,
 * {@code port}. When not supplied, it will default to port {@code 6013}.
 * 
 * <p>
 * Example console execute: <blockquote>
 * 
 * <pre>
 * java -jar chatbot-server.jar 5500
 * </pre>
 * 
 * </blockquote>
 * <p>
 * Or subsequently without arguments: <blockquote>
 * 
 * <pre>
 * java -jar chatbot-server.jar
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * @author Danry Ague
 * @version 2.3.6
 */
public class Server {

    /**
     * Used to indicate/prefix error logs. Must be concatenated with the actual
     * error message.
     */
    final static String PREF_ERR = "[ERROR] ";

    /**
     * Used to indicate/prefix informational logs. Must be concatenated with the
     * actual information message.
     */
    final static String PREF_INF = "[INFO] ";

    /**
     * Used for developer convenience.
     */
    static PrintStream stdStream = System.out;

    /**
     * Generates a timestamp to be used.
     * 
     * @return a {@code String} timestamp
     */
    static String generateTimestamp() {
	return new java.util.Date().toString();
    }

    /**
     * Uses the {@link java.net.ServerSocket ServerSocket} class in order to create
     * a server that listens to a specified {@code port} number.
     * 
     * @param uri  the address of the live server.
     * @param port the port number of the live server.
     * 
     * @throws IOException
     */
    static void listen(int port) throws IOException {
	/** Declarations **/
	ServerSocket sock = new ServerSocket(port);
	String line;
	boolean flag = false;

	/* Output current server instance port when successfully connected */
	stdStream.println(PREF_INF + "Server listening at port " + port);

	while (!flag) {

	    /* Initial handshake to client when successfully connected */
	    Socket client = sock.accept();
	    PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
	    pout.println(generateTimestamp());
	    stdStream.println(PREF_INF + "A client has successfully connected.");

	    /* Send back to client */
	    InputStream in = client.getInputStream();
	    BufferedReader bin = new BufferedReader(new InputStreamReader(in));
	    while ((line = bin.readLine()) != null && !flag) {

		/* Check if user wants to exit */
		if (line.equals("exit")) {
		    stdStream.println(PREF_INF + "A client has been disconnected.");
		} else {

		    /* Otherwise, relay reply to client */
		    stdStream.println(generateTimestamp() + " | From client: " + line);
		    pout.println(line);
		}

	    }

	}
    }

    /**
     * Main entry point of the program. Checks first if arguments are present before
     * running the server.
     * 
     * @param args (optional) port
     */
    public static void main(String[] args) {
	int port;
	
	if (args.length != 0) {
	    port = Integer.parseInt(args[0]);
	} else {
	    /** Defaults, if there no console arguments **/
	    port = 6013;
	}
	
	// TODO: Handle args and exceptions
	try {
	    listen(port);
	} catch (IOException e) {
	    stdStream.println(PREF_ERR + e.getMessage());
	}
    }
}
