package de.socketTest.socket;

import de.socketTest.database.repository.ClientRepository;

import java.io.*;
import java.net.Socket;

public class SocketThread extends Thread {

    SocketService service;

    protected Socket ClientSocket;
    private String client;
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
            System.out.println(client);
            out.println("RECEIVED");
            out.flush();
        } catch (IOException e) {
            return;
        }
        service.reportClient(this, client);
        System.out.println("Waiting for Communication to Start...");
        while(!communicationStarted) {
            // Waiting for Communication to be started
        }
        while (true) {
            try {
                line = reader.readLine();
                if ((line == null) || line.equals("EXIT")) {
                    System.out.println("Closing Connection");
                    service.removeClient(client);
                    ClientSocket.close();
                    return;
                } else {
                    System.out.println(client + ": " + line);
                    service.handleMessage(line, this);
                    // Successful received
                    out.println("RECEIVED");
                    out.flush();
                }
            } catch (java.net.SocketException e) {
                System.out.println("Socket Connection Lost...");
                service.removeClient(client);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

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
