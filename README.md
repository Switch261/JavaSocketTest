# JavaSocketTest
Test Repository for Sockets with Spring Boot Web

## Server
### Web Server
Web-Address: Is show in the console output (if run on local device: http://localhost:8081/) <br>
Port: 8081 <br>
<br>
### Login Data
Username: user <br>
Password: password <br>

---
### Server Socket
Runs on Port 8082 <br>
To Establish a connection please follow the steps below: <br>

1. Connect to Server on the Address and Port above <br>
2. Send the Client Name to the Server (Name has to be known by the Server to Work Properly)
3. Wait for Server Response "RECEIVED" to verify that the Server successfully received your message
4. Wait for the Server to start the chat: Listen for the Server to send "CHAT START"
5. If the Server started the chat, you can write and send Messages to the Server. The Server will send you a response ("RECEIVED") if it received the message
6. To Close the Connection send a message with "EXIT" to the server
<br>
<br>
<i>Note that the SimpleClient.java Client already implements those steps</i>
---
---
## Client
<i>Note: This client is for command line only and has no UI</i> 
<br>
<br>
### Compile the Client
To compile the Client type <i>javac SimpleClient.java</i> in the command line
<br>
### Use the Client
To run the client type <i>java SimpleClient serverAddress clientName</i> and replace <i>serverAddress</i> with the ip address of your server and <i>clientName</i> with the Client you want to use (in this example <i>Client1</i> to <i>Client5</i>)<br>
<br>
After starting the Client, the client will try to connect to the Server. If the Client was successful a success message will appear in the console. <br>
Know the client will wait for the Server to start the chat. After the start of the chat by the Server the client is able to send messages to the Server.

## Source
<a href=https://www.baeldung.com/a-guide-to-java-sockets>Baeldung: Guide to Java Sockets<a> <br>
<a href=https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server>Stackoverflow: Socket Programming, multiple clients to one Server</a>