package com.smartgun.service;

import com.smartgun.model.simulation.SimulationData;
import com.smartgun.shared.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.smartgun.shared.Config;
import com.smartgun.model.incident.*;

@Service
public class SimulationLifeService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    SimulationLifeService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void checkIncidents(int currentTime) {
        for (int i = 0; i < Data.serverSimulationData.getIncidents().size(); i++) {
            Incident incident = Data.serverSimulationData.getIncidents().get(i);
            if (incident.getEndTime() < currentTime) {
                Data.serverSimulationData.removeIncident(incident);
                System.out.println("ENDED AN INCIDENT");
            }
        }
    }

    public void addIncident(Incident incident) {
        Data.serverSimulationData.addingIncidents(incident);
    }

    // wysyłanie danych
    public void sendMessages() {
        simpMessagingTemplate.convertAndSend(
                Config.WS_MESSAGE_TRANSFER_DESTINATION,
                //tutaj zawartość główna - patrole, ambulanse, zdarzenia
                new SimulationData("Next portion of Data!", Data.serverSimulationData.getIncidents())
        );
    }
}
