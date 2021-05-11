OS Activity 3: Process Implementation, Server-Side Codebase
=========================
Simple implementation of client-server communication for the server-side interface. When run in the command line,<sup>[1](#fn-jarfile)</sup> it accepts one argument, `port`. When not supplied, it will default to port `6013`. Runs indefinitely until the `exit` keyword is received from the client.

If the `--persist` argument is present, the `exit` keyword will be ignored.

Example console execute: 

<blockquote>
<pre>
java -jar chatbot-server.jar --port 5500
</pre>
</blockquote>

Or subsequently without arguments: 

<blockquote>
<pre>
java -jar chatbot-server.jar
</pre>
</blockquote>

**Note:** Needs a Java Runtime Environment installed. You may download from the [Java Official Website](https://www.java.com/en/download/).

## Definition of Terms
`port`: The server port that is open to clients (default: 6013)

`exit`: Server terminate keyword

## Description
This is a simple Java 'server' that runs in the command line where Java clients can connect to. It can only echo back any messages sent to it back to the client.

## Process Rundown
At successful runtime, it will display the `port` where the client can connect to. If the client is forcibly disconnected without using the keyword for some unknown reason, the program will terminate and an error message will be displayed. The program will refuse to run if the `port` is already binded (used) in another server instance.

When a client successfully connects, a handshake is sent to the client to confirm the connection in the form of the current date and time. Any messages from the client will be displayed with a timestamp for logging purposes but only the containing message will be echoed back to the client.

A successfully run server output with a connected client and its message will more or less look like so:

```
[INFO] Server listening at port 6013
[INFO] A client has successfully connected.
Mon May 10 15:30:12 PST 2021 | From client: Hello World!
[INFO] A client has been disconnected.
[INFO] Socket is closed.
[INFO] Terminating...
```

The connection and, subsequently, the program will be terminated if the program receives the word 'exit' without the quotes from the client. If the `--persist` argument is present, the keyword will be ignored.

## Checklist
- [x] Semi-finalize code
- [ ] Confirm with the instructor if the process is correct
- [ ] Finalize JavaDocs and README
- [ ] Finalize all

### Footnote
1. <a name="fn-jarfile"></a> You need to compile and export the code as a `jar` file first before you can execute it. Or you may download a [release version](https://github.com/epcraft93/os-chatbot-server/releases).
