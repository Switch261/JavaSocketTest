package de.socketTest.socket;

import de.socketTest.database.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.Socket;

public class SocketThread extends Thread {

    @Autowired
    ClientRepository clientRepository;

    protected Socket ClientSocket;

    public SocketThread(Socket clientSocket) {
        this.ClientSocket = clientSocket;
    }

    public void run() {
        System.out.println("New Connection established");
        InputStream inputStream = null;
        BufferedReader reader = null;
        PrintWriter out;
        try {
            inputStream = ClientSocket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            out = new PrintWriter(ClientSocket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = reader.readLine();
                if ((line == null) || line.equalsIgnoreCase("EXIT")) {
                    System.out.println("Closing Connection");
                    ClientSocket.close();
                    return;
                } else {
                    System.out.println(line);
                    handleMessage(line);
                    // Successful received
                    out.println("RECEIVED");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void handleMessage(String message) {
        System.out.println("Client: " + message.substring(0, message.indexOf(":")));
        System.out.println("Message " + message.substring(message.indexOf(":") + 1));
    }
}
