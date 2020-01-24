package de.socketTest.web.service;

import de.socketTest.database.model.Client;
import de.socketTest.database.repository.ClientRepository;
import de.socketTest.socket.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ApplicationService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SocketService socketService;

    public ApplicationService() {

    }

    //////////////////////////////////////////
    //              Methods                 //
    //////////////////////////////////////////

    // Find one Client by Name
    public Client getClient(String name) {
        return clientRepository.findOneByNameIgnoreCase(name).get();
    }

    // Get all Clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Delete all Messages send by the Client
    public void clearMessages(Client client) {
        client.setMessages(new ArrayList<>());
        clientRepository.save(client);
    }

    // Delete all Messages send by all Clients
    public void clearAllMessages() {
        for(Client client: clientRepository.findAll()) {
            clearMessages(client);
        }
    }

    // Start Chat with the Client
    // Note that Communication between Client and Server has to be established
    public void startCommunication(String client) {
        socketService.startChatWithClient(client);
    }

    // Start chat with all clients
    public void startAllCommunications() {
        socketService.startAllChats();
    }

    // Returns List of All Clients which are connected to the Server
    public ArrayList<String> getActiveConnections() {
        return socketService.getActiveConnections();
    }
}
