package de.socketTest.web.controller;

import de.socketTest.web.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {

    @Autowired
    ApplicationService service;

    @GetMapping("/")
    public String getMessages(Model model) {
        model.addAttribute("clients", service.getAllClients());
        return "page";
    }

    @PostMapping("/")
    public String clearMessages(Model model) {
        service.clearAllMessages();
        model.addAttribute("clients", service.getAllClients());
        return "page";
    }
}
