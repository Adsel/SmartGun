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

import com.smartgun.model.policeman.*;
import com.smartgun.model.incident.*;
import java.awt.Point;

@Service
public class GreetingService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private List<Incident> incidents;

    GreetingService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;

        // STORED INCIDENTS
        this.incidents = new ArrayList<>();

        // INPUT VARIABLES (FROM CLIENT)
        Integer COUNT_OF_PATROLS = 5;
        Point AMBULANCE_STARTING_POSITION = new Point(0, 0);

        // START SIMULATION

        MonitoringAgent agent = new MonitoringAgent();
        HeadQuarter headQuarter = new HeadQuarter(5, agent);
        MainAgent coreAgent = new MainAgent(agent, headQuarter.getPatrols());

    }

    public void sendMessages() {
        simpMessagingTemplate.convertAndSend(
                Config.WS_MESSAGE_TRANSFER_DESTINATION,
                new Greeting("New greeting!")
        );
    }

    public void checkIncidents(int currentTime) {
        for (int i = 0; i < this.incidents.size(); i++) {
            Incident incident = this.incidents.get(i);
            if (incident.getEndTime() < currentTime) {
                this.incidents.remove(incident);
                System.out.println("ENDED AN INCIDENT");
            }
        }
    }

    public void addIncident(Incident incident) {
        this.incidents.add(incident);
    }
}