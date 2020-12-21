package com.smartgun.scheduler;

import com.smartgun.model.map.Sector;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.smartgun.service.SimulationLifeService;
import com.smartgun.shared.Data;
import com.smartgun.model.incident.*;
import java.awt.*;
import java.util.Random;

@Component
public class Scheduler {
    private final SimulationLifeService simulationLifeService;
    private final int TIME_UNIT = 1;
    private final int TIME_LIMIT = 23;
    private final int TIME_MORNING = 7;
    private final int TIME_EVENING = 21;
    private int timestamp;
    private int simulationTime;
    private Random generator;
    private Integer CERTAINTY_PROBABILITY = 100;


    public Scheduler(SimulationLifeService simulationLifeService) {
        this.timestamp = 0;
        this.simulationTime = 0;
        this.simulationLifeService = simulationLifeService;
        this.generator = new Random();
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
            simulationTime += TIME_UNIT;
            if (timestamp > TIME_LIMIT) {
                timestamp = 0;
            }

            // === CHECKS IF ANY INCIDENTS HAS BEEN OUTDATED ===
            simulationLifeService.checkIncidents(this.timestamp);

            if (
                    !Data.data.getIsDayAndNightSystem()
                    || (timestamp > TIME_MORNING && timestamp < TIME_EVENING)
            ) {
                // DAY
                this.generateIncidents(true);
            } else {
                this.generateIncidents(false);
            }
        }
    }


    private boolean isAccident(Integer percentage) {
        if (generator.nextInt(CERTAINTY_PROBABILITY) < percentage) {
            return true;
        }

        return false;
    }

    private int genDurationTime(Integer min, Integer max) {
        return this.generator.nextInt(max) + min;
    }

    private void generateIncidents(boolean isDay) {
        for (Sector sector: Data.serverSimulationData.getMap().getSectors()) {
           generateIncidentsPerSector(sector, isDay);
        }
    }

    private void generateIncidentsPerSector(Sector sector, boolean isDay) {
        //Incident newDayIncident = new IncidentInDay(timestamp, incidentDurationTime, new Point(7,7));
        //Incident createdIncident = new Incident(this.timestamp, incidentDurationTime, new Point(0, 0));
        // String incidentMode = createdIncident.isFiredIncident() ? "FIRED" : "NORMAL";

        if (isDay) {
            Integer intervationProbablityPercentage = Data.data.getInterventionProbablity()[sector.getSectorTypeValue()];
            if (isAccident(intervationProbablityPercentage)) {
                Integer durationTime = genDurationTime(
                        Data.data.getInterventionDuration()[0],
                        Data.data.getInterventionDuration()[1]
                );
                // TODO:
                // GENERATE RANDOM POINT IN CURRENT SECTION
                addIncident(
                        new Incident(
                                this.simulationTime, durationTime, new Point(0, 0),
                                Incident.IncidentType.INTERVENTION
                        )
                );
                System.out.println("GENERATED INTERVENTION, HAVE FUN!");
            }
            // TODO: shooting, intervation_turning_to_shooting
        } else {
            Integer nightIntervationProbablity = Data.data.getInterventionProbablity()[sector.getSectorTypeValue()];
            // TODO: shooting, intervation_turning_to_shooting
        }
    }

    private void addIncident(Incident incident) {
        simulationLifeService.addIncident(incident);
    }
}
