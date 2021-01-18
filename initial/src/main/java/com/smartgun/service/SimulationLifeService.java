package com.smartgun.service;

import com.smartgun.model.simulation.SimulationData;
import com.smartgun.model.simulation.SimulationTime;
import com.smartgun.shared.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


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

    public void setSimulationTime(SimulationTime simulationTime) {
        Data.serverSimulationData.setSimulationTime(simulationTime);
    }

    // sending data
    public void sendMessages() {
//        for (Incident i: Data.serverSimulationData.getIncidents()) {
//            System.out.println(i.getIncidentPoint());
//        }
        simpMessagingTemplate.convertAndSend(
                Config.WS_MESSAGE_TRANSFER_DESTINATION,
                new SimulationData(
                        Data.serverSimulationData.getSimulationTime(),
                        Data.serverSimulationData.getIncidents(),
                        Data.serverSimulationData.getEvents(),
                        Data.serverSimulationData.getPatrols()
                )
        );
    }
}
