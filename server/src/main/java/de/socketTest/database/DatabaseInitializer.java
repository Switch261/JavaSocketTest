package de.socketTest.database;

import de.socketTest.database.model.Client;
import de.socketTest.database.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Component
public class DatabaseInitializer implements ServletContextInitializer {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("Initializing the database");
        // Initialize the database with 5 Clients
        // Client4 with 1 message ang Client5 with 2 messages
        Client client1 = new Client("Client1");
        Client client2 = new Client("Client2");
        Client client3 = new Client("Client3");
        Client client4 = new Client("Client4");
        Client client5 = new Client("Client5");

        // creating Messages
        client4.addMessage("Message 1 for Client 4");
        client5.addMessage("Message 1 for Client 4");
        client5.addMessage("Message 2 for Client 5");

        // save Clients
        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);
        clientRepository.save(client4);
        clientRepository.save(client5);

        System.out.println("Initialisation ready");
    }
}
