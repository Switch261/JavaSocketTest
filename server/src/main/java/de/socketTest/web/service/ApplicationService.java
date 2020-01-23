package de.socketTest.web.service;

import de.socketTest.database.model.Client;
import de.socketTest.database.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationService {

    @Autowired
    ClientRepository clientRepository;

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
}
