package com.smartgun.service;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.smartgun.shared.Config;
import com.smartgun.model.Greeting;

@Service
public class GreetingService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    GreetingService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessages() {
        simpMessagingTemplate.convertAndSend(
                Config.WS_MESSAGE_TRANSFER_DESTINATION,
                new Greeting("New greeting!")
        );
    }
}