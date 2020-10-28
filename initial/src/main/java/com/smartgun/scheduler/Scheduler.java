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

    Scheduler(GreetingService greetingService) {
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
        System.out.println("[NO NEW EVENTS]");

        Random rand = new Random();
        int n = rand.nextInt(10);
        // p(A) = 0,1; A - probability of incident
        if (n == 4) {
            Incident createdIncident = new Incident();
            greetingService.addIncident(
                    createdIncident
            );

            String incidentMode = createdIncident.isFiredIncident() ? "FIRED" : "NORMAL";
            System.out.println("ADDED " + incidentMode + " INCIDENT!");
        }
    }
}