package com.smartgun.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smartgun.service.SimulationLifeService;
import com.smartgun.shared.Data;

import com.smartgun.model.incident.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.util.Random;
import java.util.UUID;

@Component
public class Scheduler {
    private final SimulationLifeService simulationLifeService;
    private final int TIME_UNIT = 1;
    private final int TIME_LIMIT = 23;
    private final int TIME_MORNING = 7;
    private final int TIME_EVENING = 21;
    private int timestamp;


    public Scheduler(SimulationLifeService simulationLifeService) {
        this.timestamp = 0;
        this.simulationLifeService = simulationLifeService;
        System.out.println(timestamp);
    }

    @Scheduled(fixedRateString = "1000", initialDelayString = "0")
    public void schedulingTask() {
        if (Data.isUser) {
            simulationLifeService.sendMessages();
        }
    }

    @Scheduled(fixedRateString = "1000", initialDelayString = "0")
    public void lifeCycleTask() {
        if (Data.isUser) {
            timestamp += TIME_UNIT;
            if (timestamp > TIME_LIMIT) {
                timestamp = 0;
            }
            System.out.println(timestamp);

            // === CHECKS IF ANY INCIDENTS HAS BEEN OUTDATED ===
            simulationLifeService.checkIncidents(this.timestamp);

            Random rand = new Random();
            int incidentDurationTime = rand.nextInt(10) + 1;
            int n = rand.nextInt(10);
            // p(A) = 0,1; A - probability of incident Data.data ///////// LOSOWANIE


            if (timestamp > TIME_MORNING && timestamp < TIME_EVENING) {
                // DAY
                if (n == 8) {


                    Incident newDayIncident = new IncidentInDay(timestamp, incidentDurationTime, new Point(7,7));
                    Incident createdIncident = new Incident(this.timestamp, incidentDurationTime, new Point(0, 0));
                    simulationLifeService.addIncident(
                            newDayIncident
                    );

                    String incidentMode = createdIncident.isFiredIncident() ? "FIRED" : "NORMAL";
                    System.out.println("ADDED " + incidentMode + " INCIDENT!");
                }
            } else {
                // NIGHT
                int probabilityOfShooting = 40;

//                Data.data.getShootingProbablity().length
                System.out.println("SProb:"+Data.data.getShootingProbablity().toString()
                );
                if ( probabilityOfShooting < 30) {
                    Incident newNightIncident = new IncidentInNight(timestamp, incidentDurationTime, new Point(11,11));
                    simulationLifeService.addIncident(newNightIncident);
                    System.out.println(newNightIncident);
                } else {
                    Incident newNightShootingIncident = new ShootingIncidentInNight(timestamp, incidentDurationTime, new Point(22,22));
                    simulationLifeService.addIncident(newNightShootingIncident);
                    System.out.println(newNightShootingIncident);
                }
            }
        }
    }
}
