package com.bill.tech.controller;

//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class ChatController {
//
//    @MessageMapping("/message")
//    @SendTo("/topic/messages")
//    public String processMessage(String message) {
//        return "Server Response: " + message;
//    }
//}



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.service.impl.WebSocketService;

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

