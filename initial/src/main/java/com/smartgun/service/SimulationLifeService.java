package com.smartgun.service;

import com.smartgun.model.simulation.SimulationData;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.smartgun.shared.Config;
import com.smartgun.model.simulation.ClientStartingSimulationData;

import com.smartgun.model.incident.*;
import com.smartgun.model.headquarter.*;
import java.awt.Point;

@Service
public class SimulationLifeService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private List<Incident> incidents;

    SimulationLifeService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;

        // STORED INCIDENTS
        this.incidents = new ArrayList<>();

        // INPUT VARIABLES (FROM CLIENT)
        Integer COUNT_OF_PATROLS = 5;
        Point AMBULANCE_STARTING_POSITION = new Point(0, 0);

        // START SIMULATION
        MonitoringAgent agent = new MonitoringAgent();
        HeadQuarter headquarter = new HeadQuarter(5, agent);
        MainAgent coreAgent = new MainAgent(agent, headquarter.getPatrols());
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

    public void sendMessages() {
        simpMessagingTemplate.convertAndSend(
                Config.WS_MESSAGE_TRANSFER_DESTINATION,
                new SimulationData("Next portion of Data!")
        );
    }
}
