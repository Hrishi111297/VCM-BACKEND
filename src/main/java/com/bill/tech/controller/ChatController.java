package com.bill.tech.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.service.impl.WebSocketService;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RestController
public class ChatController {

    private final WebSocketService webSocketService;

    public ChatController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @PostMapping("/ws/send-notification")
    public String sendNotification(@RequestParam String user, @RequestParam String message) {
        webSocketService.sendNotification("/topic/ws/" + user, message); // Send message to specific user
        return "Notification sent!";
    }

}

