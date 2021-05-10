OS Activity 3: Process Implementation, Server-Side Codebase
=========================
Simple implementation of client-server communication for the server-side interface. When run in the command line, it accepts one argument, `port`. When not supplied, it will default to port `6013`. Runs indefinitely until the `exit` keyword is received from the client.

<p>
Example console execute: 

<blockquote>
<pre>
java -jar chatbot-server.jar 5500
</pre>
</blockquote>

<p>
Or subsequently without arguments: 

<blockquote>
<pre>
java -jar chatbot-server.jar
</pre>
</blockquote>

### Checklist {#checklist}
- [x] Semi-finalize code
- [ ] Finalize JavaDocs and README
- [ ] Finalize all