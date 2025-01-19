package com.bill.tech.service.impl;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate template;

    public WebSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendNotification(String destination, String message //we can add object as a parameter too
    		) {
        template.convertAndSend(destination, message); // Send message to destination
    }
}
