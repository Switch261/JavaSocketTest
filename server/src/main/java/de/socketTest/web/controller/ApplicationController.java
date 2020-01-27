package de.socketTest.web.controller;

import de.socketTest.web.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {

    @Autowired
    ApplicationService service;

    @GetMapping("/")
    public String getMessages(Model model) {
        // shows all messages for every client
        model.addAttribute("clients", service.getAllClients());
        model.addAttribute("connections", service.getActiveConnections());
        return "page";
    }

    @PostMapping("/")
    public String clearMessages(Model model) {
        // deletes all messages fro every client
        service.clearAllMessages();
        model.addAttribute("clients", service.getAllClients());
        model.addAttribute("connections", service.getActiveConnections());
        return "page";
    }

    @GetMapping("/startCommunication/{client}")
    public String startCommunication(@PathVariable String client) {
        // start communication with the client
        service.startCommunication(client);
        return "redirect:/";
    }

    @PostMapping("/startAllCommunications/")
    public String startCommunication() {
        // starts communication with all clients
        service.startAllCommunications();
        return "redirect:/";
    }
}
