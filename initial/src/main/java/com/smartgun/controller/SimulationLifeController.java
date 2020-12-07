package com.smartgun.controller;

import com.smartgun.data.MapData;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.smartgun.model.simulation.ClientStartingSimulationData;
import com.smartgun.service.SimulationLifeService;
import com.smartgun.shared.Data;
import com.smartgun.model.simulation.InitialData;

@Controller
public class SimulationLifeController {
    // SEND DATA TO CLIENT
    private final SimulationLifeService simulationLifeService;

    SimulationLifeController(SimulationLifeService simulationLifeService) {
        this.simulationLifeService = simulationLifeService;
    }

    // GET INITIAL DATA FROM CLIENT, THEN START SIMULATION
    @MessageMapping("/login")
    @SendTo("/topic/simulation")
    public ClientStartingSimulationData login(InitialData data) throws Exception {
        Data.setupData(data);

        // CHECK AFTER SETUP
        return new ClientStartingSimulationData(
                new MapData(Data.map.toString())
        );
    }
}