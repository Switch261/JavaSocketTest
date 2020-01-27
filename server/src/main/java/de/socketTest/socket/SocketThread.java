package de.socketTest.socket;

import java.io.*;
import java.net.Socket;

/*
// SocketThread
// Main Method starts a SocketThread for each new client trying to connect
// SocketThread receives messages from the client
*/
public class SocketThread extends Thread {

    SocketService service;

    protected Socket ClientSocket;
    private String client;
    // Boolean for Started Communication; important for communication with Client (see void startCommunication()),
    // because a message is send to the client which could result in wrong behavior on  the client side
    private boolean communicationStarted = false;
    private PrintWriter out;

    public SocketThread(Socket clientSocket, SocketService service) {
        this.ClientSocket = clientSocket;
        this.service = service;
    }

    public void run() {
        System.out.print("New Connection established with ");
        InputStream inputStream = null;
        BufferedReader reader = null;

        String line;
        try {
            inputStream = ClientSocket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(ClientSocket.getOutputStream());
            // receive Client and report success
            client = reader.readLine();
            // Prints the client name
            System.out.println(client);
            out.println("RECEIVED");
            out.flush();
        } catch (IOException e) {
            return;
        }
        // Reports the new client to the SocketService
        service.reportClient(this, client);
        System.out.println("Waiting for Communication to Start...");
        while(!communicationStarted) {
            // Waiting for Communication to be started by Server
        }
        while (true) {
            // waiting for Client to send a message
            try {
                line = reader.readLine();
                if ((line == null) || line.equals("EXIT")) {
                    // receives Exit command from the Client
                    System.out.println("Closing Connection");
                    // removes Client in SocketService HashMap of connected Clients
                    service.removeClient(client);
                    // Closing connection to the client
                    ClientSocket.close();
                    return;
                } else {
                    // handling a normal message
                    System.out.println(client + ": " + line);
                    service.handleMessage(line, this);
                    // Successful received
                    out.println("RECEIVED");
                    out.flush();
                }
            } catch (java.net.SocketException e) {
                // Connection Lost handling
                System.out.println("Socket Connection Lost...");
                service.removeClient(client);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    // Method to start a Chat with a already connected Client
    public void startCommunication() {
        out.println("CHAT START");
        out.flush();
        communicationStarted = true;
        System.out.println("Communication with " + client + " started");
    }

    public boolean isCommunicationStarted() {
        return communicationStarted;
    }

    public String getClient() {
        return this.client;
    }
}
