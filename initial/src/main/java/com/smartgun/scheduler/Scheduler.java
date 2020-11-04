package com.smartgun.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smartgun.service.GreetingService;
import com.smartgun.shared.Data;

import com.smartgun.model.policeman.*;
import com.smartgun.model.incident.*;

import java.util.Random;

@Component
public class Scheduler {
    private final GreetingService greetingService;
    private int timestamp;

    public Scheduler(GreetingService greetingService) {
        this.timestamp = 0;
        this.greetingService = greetingService;
    }

    @Scheduled(fixedRateString = "3000", initialDelayString = "0")
    public void schedulingTask() {
        if (Data.isUser) {
            greetingService.sendMessages();
        }
    }

    @Scheduled(fixedRateString = "3000", initialDelayString = "0")
    public void lifeCycleTask() {
        this.timestamp += 3;

        // === CHECKS IF ANY INCIDENTS HAS BEEN OUTDATED ===
        greetingService.checkIncidents(this.timestamp);

        Random rand = new Random();
        int incidentDurationTime = rand.nextInt(10) + 1;
        int n = rand.nextInt(10);
        // p(A) = 0,1; A - probability of incident
        if (n == 4) {
            Incident createdIncident = new Incident(this.timestamp, incidentDurationTime);
            greetingService.addIncident(
                    createdIncident
            );

            String incidentMode = createdIncident.isFiredIncident() ? "FIRED" : "NORMAL";
            System.out.println("ADDED " + incidentMode + " INCIDENT!");
        }
    }
}
