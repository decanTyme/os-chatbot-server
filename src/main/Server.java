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
 * {@code port}. When not supplied, it will default to port {@code 6013}. Runs
 * indefinitely until the {@code exit} keyword is received the from client.
 * 
 * <p>
 * If {@code --persist} argument is present, the {@code exit} keyword is
 * ignored.
 * 
 * <p>
 * Example console execute: <blockquote>
 * 
 * <pre>
 * java -jar chatbot-server.jar --port 5500
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
 * If the `--persist` argument is present, the keyword will be ignored.
 * 
 * @author Danry Ague
 * @version 2.3.8-alpha
 */
public class Server {

    static boolean persist = false;

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
     * Provides a delay in milliseconds. Useful for making sure that the user sees
     * an output before it is cleared.
     * 
     * @param ms desired delay in milliseconds
     */
    static void delay(int ms) {
	try {
	    Thread.sleep(ms);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

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

	/* Output current server instance port when server is successfully started */
	stdStream.println(PREF_INF + "Server listening at port " + port);

	do {

	    /* Initial handshake to client */
	    Socket client = sock.accept();
	    PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
	    pout.println(generateTimestamp());
	    stdStream.println(PREF_INF + "A client has successfully connected.");

	    /* Echo back to client */
	    InputStream in = client.getInputStream();
	    BufferedReader bin = new BufferedReader(new InputStreamReader(in));
	    while ((line = bin.readLine()) != null) {

		/** Check if user wants to exit **/
		if (line.equals("exit")) {
		    stdStream.println(PREF_INF + "A client has been disconnected.");
		    pout.println(line);
		} else {

		    /* Otherwise, relay reply to client */
		    stdStream.println(generateTimestamp() + " | From client: " + line);
		    pout.println(line);
		}

	    }

	} while (persist);

	/** Properly close the connection **/
	sock.close();
    }

    /**
     * Main entry point of the program. Checks first if arguments are present before
     * running the server.
     * 
     * @param args (optional) port
     */
    public static void main(String[] args) {
	int port = 6013; // Default port

	/** Check for arguments **/
	if (args.length != 0) {
	    for (int i = 0; i < args.length; i++) {
		switch (args[i]) {
		case "--port":
		case "-p":
		    try {
			port = Integer.parseInt(args[i + 1]);
		    } catch (NumberFormatException e) {
			stdStream.println("Invalid port, using defaults.");
			delay(1000);
		    }
		    i++;
		    break;
		case "--persist":
		    persist = true;
		    break;
		default:
		    stdStream.println("Unknown command: " + args[i]);
		    i++;
		}
	    }
	}

	try {
	    listen(port); // Start server
	} catch (IOException e) {
	    if (e.getMessage().contains("closed")) {
		stdStream.println(PREF_INF + e.getMessage() + ".");
	    } else {
		stdStream.println(PREF_ERR + e.getMessage() + ".");
	    }
	    stdStream.println(PREF_INF + "Terminating...");
	}
	stdStream.close();
    }
}
