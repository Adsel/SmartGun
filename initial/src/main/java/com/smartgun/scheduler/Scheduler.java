package com.smartgun.scheduler;

import com.smartgun.model.headquarter.Ambulance;
import com.smartgun.model.incident.Event;
import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.shared.Config;
import com.smartgun.shared.file.FileManager;
import com.smartgun.model.simulation.SimulationData;
import com.smartgun.model.simulation.SimulationTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.smartgun.service.SimulationLifeService;
import com.smartgun.shared.Data;
import com.smartgun.model.incident.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Scheduler {
    private final SimulationLifeService simulationLifeService;
    private final int TIME_UNIT = 1;
    private final int TIME_UNIT_DAYS = 1;
    private final int TIME_LIMIT = 23;
    private final int TIME_MORNING = 7;
    private final int TIME_EVENING = 21;
    private final boolean FLAG_POLICEMAN_STARTING_FIRE = true;
    private int timestamp;
    private int simulationTime;
    private Random generator;
    private Integer CERTAINTY_PROBABILITY = 100;
    private FileManager fileManager;
    private boolean sentStartingData = false;
    public static List<Event> csvData;

    public Scheduler(SimulationLifeService simulationLifeService) {
        this.timestamp = 0;
        this.simulationTime = 0;
        this.simulationLifeService = simulationLifeService;
        this.generator = new Random();
        this.fileManager = new FileManager();
        Scheduler.csvData = new ArrayList<>();
    }

    private void exportManager() {
//        if (!sentStartingData) {
//            // MAYBE IN FUTURE:
//            // TODO: export data about initial probability in Data.data
//            sentStartingData = true;
//        } else

        if (this.simulationTime != 0 && this.simulationTime % Config.EXPORT_TO_CSV_INTERVAL == 0) {
            fileManager.exportToCsv(
                    Data.serverSimulationData.recieveTimeString(),
                    Scheduler.csvData
            );
            Scheduler.csvData = new ArrayList<>();
        }
    }

    @Scheduled(fixedRateString = "500", initialDelayString = "0")
    public void lifeCycleTask() {
        if (Data.isUser) {
            // === CHANGING SIMULATION TIME
            increaseSimulationTime();

            // === CHECKS IF ANY INCIDENTS HAS BEEN OUTDATED ===
            checkIncidents(this.simulationTime);

            if (simulationTime % 5 == 0) {
                // === GENERATE EVENTS (LIKE POLICEMAN FIRED, etc.) ===
                this.generateEvents();

                // === GENERATE INCIDENTS ===
                this.generateIncidents(this.isDay());
            }

            // === MOVING PATROLS ===
            Data.serverSimulationData.movePatrols();

            // === MOVING AMBULANCES ===
            Data.serverSimulationData.moveAmbulances();

            // == SEND AND SAVE RESULTS ===
            simulationLifeService.sendMessages();
            exportManager();

            Data.serverSimulationData.restartEvents();
        }
    }

    private void increaseSimulationTime() {
        Data.serverSimulationData.increaseSimulationTime();
        timestamp += TIME_UNIT;
        simulationTime += TIME_UNIT;
        if (timestamp > TIME_LIMIT) {
            timestamp = 0;
        }
    }

    private void checkIncidents(int currentTime) {
        for (int i = 0; i < Data.serverSimulationData.getIncidents().size(); i++) {
            Incident incident = Data.serverSimulationData.getIncidents().get(i);
            if (incident.getEndTime() < currentTime) {
                String description = "(" + (int)incident.getIncidentLocalization().getX() + "," +
                        + (int)incident.getIncidentLocalization().getY() + ") Ended incident";
                Data.serverSimulationData.removeIncident(incident);
                incident.backPatrol();


                Event.EventType type;
                if (
                        incident.getIncidentType() == Incident.IncidentType.INTERVENTION ||
                        incident.getIncidentType() == Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING
                ) {
                    type = Event.EventType.INTERVENTION_FINISHED;
                } else {
                    type = Event.EventType.SHOOTING_FINISHED;
                }
                Data.serverSimulationData.addEvent(new Event(incident.getIncidentLocalization(), incident.getSectorId(),description, type));
                Scheduler.csvData.add(new Event(incident.getIncidentLocalization(), incident.getSectorId(), "Ended incident", type));
            }
        }
    }

    private boolean isDay() {
        return !Data.data.getIsDayAndNightSystem() || (timestamp > TIME_MORNING && timestamp < TIME_EVENING);
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
        for (Sector sector: Data.serverSimulationData.getMap().receiveSectors()) {
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
        if (isDay) {
            // DAY
            Integer interventionProbability = Data.data.getInterventionProbablity()[sector.getSectorTypeValue()];
            if (isAccident(interventionProbability)) {
                if (isShooting(Data.data.getShootingProbablity()[sector.getSectorTypeValue()])) {
                    // SHOOTING
                    int shootingDuration = genDurationTime(
                            Data.data.getShootingDuration()[0],
                            Data.data.getShootingDuration()[1]
                    );

                    addIncident(new Shooting(
                            simulationTime, sector.generateIncidentLocalization(),
                            Incident.IncidentType.SHOOTING, shootingDuration, sector
                    ));

                } else if (checkIfWillBeShooting(
                        Data.data.getInterventionToShootingProbablity()[sector.getSectorTypeValue()])) {
                    // INTERVENTION TURNING INTO SHOOTING
                    int durationTime = genDurationTime(
                            Data.data.getInterventionDuration()[0],
                            Data.data.getInterventionDuration()[1]
                    );

                    Incident incidentIntoShooting = new Incident(
                            this.simulationTime, durationTime, sector.generateIncidentLocalization(),
                            Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING, sector
                    );

                    if (checkIfWillBeShooting(
                            // icnreased probability of the shooting becouse of started accident - TURNING INTO SHOOTING
                            Data.data.getInterventionToShootingProbablity()[sector.getSectorTypeValue()] +
                                    SimulationData.INCREASED_PROBABILITY)) {

                        int shootingDuration = genDurationTime(
                                Data.data.getShootingDuration()[0],
                                Data.data.getShootingDuration()[1]
                        );

                        incidentIntoShooting.setIncidentType(Incident.IncidentType.SHOOTING);
                        incidentIntoShooting = new Shooting(incidentIntoShooting, shootingDuration, sector);
                    }

                    addIncident(incidentIntoShooting);
                    


                } else {
                    int durationTime = genDurationTime(
                            Data.data.getInterventionDuration()[0],
                            Data.data.getInterventionDuration()[1]
                    );
                    // TODO:
                    // GENERATE RANDOM POINT IN CURRENT SECTION
                    addIncident(
                            new Incident(
                                    this.simulationTime, durationTime, sector.generateIncidentLocalization(),
                                    Incident.IncidentType.INTERVENTION, sector
                            )
                    );
                }
            }
        }
        else {
            // NIGHT
            if (isShooting(Data.data.getShootingProbablity()[sector.getSectorTypeValue()])) {
                // SHOOTING
                int shootingDuration = genDurationTime(
                        Data.data.getShootingDuration()[0],
                        Data.data.getShootingDuration()[1]
                );

                Incident nightShooting = new Shooting(simulationTime, sector.generateIncidentLocalization(),
                        Incident.IncidentType.SHOOTING, shootingDuration, sector);
                addIncident(
                        nightShooting
                );
            } else if (checkIfWillBeShooting(Data.data.getInterventionToShootingProbablity()[sector.getSectorTypeValue()])) {
                // INTERVENTION TURNING INTO SHOOTING
                int durationTime = genDurationTime(
                        Data.data.getInterventionDuration()[0],
                        Data.data.getInterventionDuration()[1]
                );

                Incident incidentIntoShootingNight = new Incident(
                        this.simulationTime, durationTime, sector.generateIncidentLocalization(),
                        Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING, sector
                );

                if (checkIfWillBeShooting(
                        Data.data.getInterventionToShootingProbablity()[sector.getSectorTypeValue()] +
                                SimulationData.INCREASED_PROBABILITY_NIGHT)) {

                    int shootingDuration = genDurationTime(
                            Data.data.getShootingDuration()[0],
                            Data.data.getShootingDuration()[1]
                    );

                    incidentIntoShootingNight.setIncidentType(Incident.IncidentType.SHOOTING);
                    incidentIntoShootingNight = new Shooting(incidentIntoShootingNight, shootingDuration, sector);
                }

                addIncident(incidentIntoShootingNight);
            } else {
                if (isAccident(Data.data.getNightInterventionProbablity()[sector.getSectorTypeValue()])) {
                    int durationTime = genDurationTime(
                            Data.data.getInterventionDuration()[0],
                            Data.data.getInterventionDuration()[1]
                    );
                    addIncident(
                            new Incident(
                                    this.simulationTime, durationTime, sector.generateIncidentLocalization(),
                                    Incident.IncidentType.SHOOTING, sector
                            )
                    );
                }
            }
        }
    }

    private void addIncident(Incident incident) {
        String description = "(" + (int)incident.getIncidentLocalization().getX() + "," +
                + (int)incident.getIncidentLocalization().getY() + ") Started incident";
        Patrol choosed = Data.serverSimulationData.choosePatrolToIntervention(incident.getIncidentLocalization());
        if (choosed != null) {
            choosed.goToIntervention(incident.getIncidentLocalization());
            incident.setPatrolToIncident(choosed);
        }

        simulationLifeService.addIncident(incident);
        Event.EventType type;
        if (
                incident.getIncidentType() == Incident.IncidentType.INTERVENTION ||
                incident.getIncidentType() == Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING
        ) {
            type = Event.EventType.INTERVENTION_STARTED;
        } else {
            type = Event.EventType.SHOOTING_STARTED;
        }
        Event generatedEvent = new Event(
                incident.getIncidentLocalization(),
                incident.getSectorId(),
                description, type
        );
        Data.serverSimulationData.addEvent(generatedEvent);
        Scheduler.csvData.add(new Event(
                incident.getIncidentLocalization(),
                incident.getSectorId(),
                "Started Incident", type
        ));
    }

    private void setSimulationTime(SimulationTime simulationTime) {
        simulationLifeService.setSimulationTime(simulationTime);
    }

    private void generateEvents() {
        Data.serverSimulationData.restartEvents();
        for (int i = 0; i < Data.serverSimulationData.getIncidents().size(); i++) {
            Incident incident = Data.serverSimulationData.getIncidents().get(i);
            Incident.IncidentType type = incident.getIncidentType();
            if (type == Incident.IncidentType.INTERVENTION) {

            } else if (type == Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING) {

            } else if (type == Incident.IncidentType.SHOOTING) {
                if (this.isDay()) {
                    this.shootingTurn(
                            Data.data.getAccuratePolicemanShootProbablity(),
                            Data.data.getAccurateAggressorShootProbablity(),
                            incident
                    );
                } else {
                    this.shootingTurn(
                            Data.data.getAccuratePolicemanShootProbablityNight(),
                            Data.data.getAccurateAggressorShootProbablityNight(),
                            incident
                    );
                }
            }
        }
    }

    private boolean isFiredSuccessfully(Integer probability, Incident incident, Event.EventType type) {
        if (isAccident(probability)) {
            String csvDesc = "";
            String description = "(" + (int)incident.getIncidentLocalization().getX() + "," +
                     + (int)incident.getIncidentLocalization().getY() + ")";
            if (type == Event.EventType.AGGRESSOR_HURTED) {
                if (isAccident(SimulationData.PROBABILITY_OF_MORTALITY)) {
                    type = Event.EventType.POLICEMAN_KILLED;
                    csvDesc = "Aggressor has been killed";
                } else {
                    csvDesc = "Aggressor has been hurt";
                }
                description += " " + csvDesc;
            } else if (type == Event.EventType.POLICEMAN_HURTED) {
                if (isAccident(SimulationData.PROBABILITY_OF_MORTALITY)) {
                    type = Event.EventType.POLICEMAN_KILLED;
                    csvDesc = "Policeman has been killed";
                } else {
                    csvDesc = "Policeman has been hurt";
                }
                description += " " + csvDesc;
            }



            Event generatedEvent = new Event(
                    incident.getIncidentLocalization(), incident.getSectorId(),
                    description, type
            );
            Data.serverSimulationData.addEvent(generatedEvent);
            Scheduler.csvData.add(new Event(
                    incident.getIncidentLocalization(), incident.getSectorId(),
                    csvDesc, type
            ));

            Event finishedEvent = new Event(
                    incident.getIncidentLocalization(), incident.getSectorId(),
                    "(" + (int)incident.getIncidentLocalization().getX() + "," +
                            + (int)incident.getIncidentLocalization().getY() + ") Finished shooting",
                    Event.EventType.SHOOTING_FINISHED
            );
            Data.serverSimulationData.addEvent(finishedEvent);
            Scheduler.csvData.add(new Event(
                    incident.getIncidentLocalization(), incident.getSectorId(),
                    "Finished shooting",
                    Event.EventType.SHOOTING_FINISHED
            ));
            Data.serverSimulationData.removeIncident(incident);


            return true;
        }

        return false;
    }

    private boolean randTruth() {
        return (new Random()).nextBoolean();
    }

    private void shootingTurn(Integer policemanProb, Integer aggressorProb, Incident incident) {
        if (this.randTruth() == FLAG_POLICEMAN_STARTING_FIRE) {
            if (!isFiredSuccessfully(policemanProb, incident, Event.EventType.AGGRESSOR_HURTED)) {
                isFiredSuccessfully(aggressorProb, incident, Event.EventType.POLICEMAN_HURTED);
            }
        } else {
            if (!isFiredSuccessfully(policemanProb, incident, Event.EventType.POLICEMAN_HURTED)) {
                isFiredSuccessfully(aggressorProb, incident, Event.EventType.AGGRESSOR_HURTED);
            }
        }
    }
}
