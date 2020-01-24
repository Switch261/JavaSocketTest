package de.socketTest.socket;

import de.socketTest.database.model.Client;
import de.socketTest.database.repository.ClientRepository;
import de.socketTest.web.exceptions.ClientNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SocketService {
    @Autowired
    ClientRepository clientRepository;

    HashMap<String, SocketThread> clientThreads= new HashMap<>();

    public SocketService() {

    }

    //////////////////////////////////////////
    //              Methods                 //
    //////////////////////////////////////////

    // Stores thread mapped by the corresponding client
    // important for sending messages to Client/to start the chat
    public void reportClient(SocketThread thread, String client) {
        if(clientThreads.containsKey(client)) {
            clientThreads.remove(client);
        }
        clientThreads.put(client, thread);
    }

    // Removes client from HashMap
    public void removeClient(String client) {
        clientThreads.remove(client);
    }

    // saves Message in Message List of the client
    public void handleMessage(String message, SocketThread thread) {
        Client client = getClientFromThread(thread);
        client.addMessage(message);
        clientRepository.save(client);
    }

    // Start Chat with the Client
    // Note that Communication between Client and Server has to be established
    public void startChatWithClient(String client) {
        if(!clientThreads.containsKey(client)) {
            throw new ClientNotFound();
        }
        if(!clientThreads.get(client).isCommunicationStarted()) {
            clientThreads.get(client).startCommunication();
        }
    }

    // Start chat with all clients
    public void startAllChats() {
        for (String client : getActiveConnections()) {
            startChatWithClient(client);
        }
    }

    // returns client corresponding to the thread
    public Client getClientFromThread(SocketThread thread) {
        return clientRepository.findOneByNameIgnoreCase(thread.getClient()).orElseThrow(ClientNotFound::new);
    }

    // Returns List of All Clients which are connected to the Server
    public ArrayList<String> getActiveConnections() {
        if(clientThreads.isEmpty()) {
            new ArrayList<String>();
        }
        return new ArrayList<>(clientThreads.keySet());
    }
}
