package com.smartgun.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.smartgun.model.InitClientMessage;
import com.smartgun.model.SimulationData;
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
    public void login(InitialData data) throws Exception {
        Data.setupData(data);

        // CHECK AFTER SETUP
        System.out.println(Data.data);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/simulation")
    public SimulationData dataForSimulation(InitClientMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay

        return new SimulationData("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
