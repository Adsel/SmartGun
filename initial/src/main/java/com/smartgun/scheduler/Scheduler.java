package com.smartgun.scheduler;

import com.smartgun.model.map.SectorType;
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

            // DAY
            this.generateIncidents(!Data.data.getIsDayAndNightSystem()
                    || (timestamp > TIME_MORNING && timestamp < TIME_EVENING));
        }
    }

    private boolean isAccident(Integer percentage) {
        if (generator.nextInt(CERTAINTY_PROBABILITY) < percentage) {
            return true;
        }

        return false;
    }

    private int genDurationTime(int min, int max) {
        return this.generator.nextInt(max) + min;
    }

    private void generateIncidents(boolean isDay) {
        for (Sector sector: Data.serverSimulationData.getMap().getSectors()) {
           generateIncidentsPerSector(sector, isDay);
        }
    }

    private boolean isShooting(int percent) {
        return generator.nextInt(CERTAINTY_PROBABILITY) < percent;
    }

    private boolean checkIfWillBeShooting(int percent) {
        return generator.nextInt(CERTAINTY_PROBABILITY) < percent;
    }

    private void generateIncidentsPerSector(Sector sector, boolean isDay) {
        //Incident newDayIncident = new IncidentInDay(timestamp, incidentDurationTime, new Point(7,7));
        //Incident createdIncident = new Incident(this.timestamp, incidentDurationTime, new Point(0, 0));
        // String incidentMode = createdIncident.isFiredIncident() ? "FIRED" : "NORMAL";

        if (isDay) {
            // DAY
            Integer interventionProbability = Data.data.getInterventionProbablity()[sector.getSectorTypeValue()];
            if (isAccident(interventionProbability)) {
                if (isShooting(Data.data.getShootingProbablity()[sector.getSectorTypeValue()])) {
                    // SHOOTING
                    int durationTime = genDurationTime(
                            Data.data.getInterventionDuration()[0],
                            Data.data.getInterventionDuration()[1]
                    );

                    int shootingDuration = genDurationTime(
                            Data.data.getShootingDuration()[0],
                            Data.data.getShootingDuration()[1]
                    );

                    Incident shooting = new Shooting(simulationTime, durationTime, new Point(0,0),
                            Incident.IncidentType.SHOOTING, shootingDuration);
                    addIncident(
                            shooting
                    );
                    System.out.println("SHOOTING!!, time: " + timestamp +
                            ", sector: "+ sector.getSectorType().toString());

                } else if (checkIfWillBeShooting(Data.data.getInterventionToShootingProbablity()[sector.getSectorTypeValue()])) {
                    // INTERVENTION TURNING INTO SHOOTING
                    int durationTime = genDurationTime(
                            Data.data.getInterventionDuration()[0],
                            Data.data.getInterventionDuration()[1]
                    );

                    addIncident(
                            new Incident(
                                    this.simulationTime, durationTime, new Point(5, 5),
                                    Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING
                            )
                    );
                    System.out.println("TURNING INTO SHOOTING, time: " + timestamp +
                            ", sector: "+ sector.getSectorType().toString());

                } else {
                    int durationTime = genDurationTime(
                            Data.data.getInterventionDuration()[0],
                            Data.data.getInterventionDuration()[1]
                    );
                    // TODO:
                    // GENERATE RANDOM POINT IN CURRENT SECTION
                    addIncident(
                            new Incident(
                                    this.simulationTime, durationTime, new Point(1, 1),
                                    Incident.IncidentType.INTERVENTION
                            )
                    );
                    System.out.println("CASUAL INTERVENTION, time: " + timestamp +
                            ", sector: "+ sector.getSectorType().toString());
                }
            }
        } else {
            // NIGHT
            if (isShooting(Data.data.getShootingProbablity()[sector.getSectorTypeValue()])) {
                // SHOOTING
                int durationTime = genDurationTime(
                        Data.data.getInterventionDuration()[0],
                        Data.data.getInterventionDuration()[1]
                );

                int shootingDuration = genDurationTime(
                        Data.data.getShootingDuration()[0],
                        Data.data.getShootingDuration()[1]
                );

                Incident nightShooting = new Shooting(simulationTime, durationTime, new Point(0,0),
                        Incident.IncidentType.SHOOTING, shootingDuration);
                addIncident(
                        nightShooting
                );

                System.out.println("NIGHT SHOOTING!!, time: " + timestamp +
                        ", sector: "+ sector.getSectorType().toString());
            } else if (checkIfWillBeShooting(Data.data.getInterventionToShootingProbablity()[sector.getSectorTypeValue()])) {
                // INTERVENTION TURNING INTO SHOOTING
                int durationTime = genDurationTime(
                        Data.data.getInterventionDuration()[0],
                        Data.data.getInterventionDuration()[1]
                );
                addIncident(
                        new Incident(
                                this.simulationTime, durationTime, new Point(5, 5),
                                Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING
                        )
                );
                System.out.println("TURNING INTO SHOOTING IN THE NIGHT, time: " + timestamp +
                        ", sector: "+ sector.getSectorType().toString());
            } else {
                if (isAccident(Data.data.getNightInterventionProbablity()[sector.getSectorTypeValue()])) {
                    int durationTime = genDurationTime(
                            Data.data.getInterventionDuration()[0],
                            Data.data.getInterventionDuration()[1]
                    );
                    addIncident(
                            new Incident(
                                    this.simulationTime, durationTime, new Point(4, 4),
                                    Incident.IncidentType.SHOOTING
                            )
                    );
                    System.out.println("CASUAL NIGHT INTERVENTION, time: " + timestamp +
                            ", sector: "+ sector.getSectorType().toString());
                }
            }
        }
    }

    private void addIncident(Incident incident) {
        simulationLifeService.addIncident(incident);
    }
}
