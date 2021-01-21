package com.smartgun.scheduler;

import com.smartgun.model.headquarter.Ambulance;
import com.smartgun.model.incident.Event;
import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.shared.Config;
import com.smartgun.shared.file.CsvRow;
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
    private List<CsvRow> csvData;

    public Scheduler(SimulationLifeService simulationLifeService) {
        this.timestamp = 0;
        this.simulationTime = 0;
        this.simulationLifeService = simulationLifeService;
        this.generator = new Random();
        this.fileManager = new FileManager();
        this.csvData = new ArrayList<>();
    }

    private void exportManager() {
        if (!sentStartingData) {
            // MAYBE IN FUTURE:
            // TODO: export data about initial probability in Data.data
            sentStartingData = true;
        } else if (this.simulationTime != 0 && this.simulationTime % Config.EXPORT_TO_CSV_INTERVAL == 0) {
            fileManager.exportToCsv(
                    Data.serverSimulationData.recieveTimeString(),
                    this.csvData
            );
            this.csvData = new ArrayList<>();
        }
    }

    @Scheduled(fixedRateString = "1000", initialDelayString = "0")
    public void lifeCycleTask() {
        if (Data.isUser) {
            // === CHANGING SIMULATION TIME
            increaseSimulationTime();

            // === CHECKS IF ANY INCIDENTS HAS BEEN OUTDATED ===
            checkIncidents(this.simulationTime);

            if (simulationTime % 10 == 0) {
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
                Data.serverSimulationData.removeIncident(incident);
                this.csvData.add(new CsvRow(
                        incident.getIncidentType().name(),
                        "Ended incident",
                        "(" + (int) incident.getIncidentLocalization().getX() + ":" + (int) incident.getIncidentLocalization().getY() + ")",
                        Data.serverSimulationData.recieveTimeString()
                ));
                incident.recieveChoosedPatrol().sendToObserve();
                System.out.println("ENDED AN INCIDENT");
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
                            Incident.IncidentType.SHOOTING, shootingDuration
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
                            Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING
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
                        incidentIntoShooting = new Shooting(incidentIntoShooting, shootingDuration);
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
                                    Incident.IncidentType.INTERVENTION
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
                        Incident.IncidentType.SHOOTING, shootingDuration);
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
                        Incident.IncidentType.INTERVENTION_TURNING_INTO_SHOOTING
                );

                if (checkIfWillBeShooting(
                        Data.data.getInterventionToShootingProbablity()[sector.getSectorTypeValue()] +
                                SimulationData.INCREASED_PROBABILITY_NIGHT)) {

                    int shootingDuration = genDurationTime(
                            Data.data.getShootingDuration()[0],
                            Data.data.getShootingDuration()[1]
                    );

                    incidentIntoShootingNight.setIncidentType(Incident.IncidentType.SHOOTING);
                    incidentIntoShootingNight = new Shooting(incidentIntoShootingNight, shootingDuration);
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
                                    Incident.IncidentType.SHOOTING
                            )
                    );
                }
            }
        }
    }

    private void addIncident(Incident incident) {
        Patrol choosed = Data.serverSimulationData.choosePatrolToIntervention(incident.getIncidentLocalization());
        if (choosed != null) {
            choosed.goToIntervention(incident.getIncidentLocalization());
            incident.setPatrolToIncident(choosed);
        }

        simulationLifeService.addIncident(incident);
        this.csvData.add(new CsvRow(
                incident.getIncidentType().name(),
                "Started incident",
                "(" + (int) incident.getIncidentLocalization().getX() + ":" + (int) incident.getIncidentLocalization().getY() + ")",
                Data.serverSimulationData.recieveTimeString()
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
                csvDesc = "aggressor has been hurt";
                description += " " + csvDesc;
            } else if (type == Event.EventType.POLICEMAN_HURTED) {
                if (isAccident(SimulationData.PROBABILITY_OF_MORTALITY)) {
                        csvDesc = "policeman has been killed";
                        description += " " + csvDesc;
                    } else {
                        csvDesc = "policeman has been hurt";
                        description += " " + csvDesc;
                    }
            }

            Data.serverSimulationData.addEvent(new Event(
                    description,
                    type,
                    incident.getIncidentLocalization()
            ));
            Data.serverSimulationData.removeIncident(incident);
            this.csvData.add(new CsvRow(
                    type.name(),
                    csvDesc,
                    "(" + (int) incident.getIncidentLocalization().getX() + ":" + (int) incident.getIncidentLocalization().getY() + ")",
                    Data.serverSimulationData.recieveTimeString()
            ));

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
