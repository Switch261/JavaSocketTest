package de.socketTest.database.model;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
/*
// Simple Client with a unique name and a List of send messages
*/
@Entity
public class Client {

    @Id
    String name;

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> messages = new ArrayList<>();

    public Client() {
        this.name = "unnamed Client";
    }

    public Client(String name) {
        this.name = name;
    }

    //////////////////////////////////////////
    //              Methods                 //
    //////////////////////////////////////////

    public void addMessage(String message) {
        this.messages.add(message);
    }

    // needed for thymeleaf
    public String getName() {
        return this.name;
    }

    // needed for thymeleaf
    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
