package com.smartgun.model.simulation;

import com.smartgun.model.incident.Event;
import com.smartgun.model.incident.Incident;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.shared.Data;

import java.util.List;

public class SimulationData {
    private Integer simulationTime;
    private List<Incident> incidents;
    private List<Event> events;
    private List<Patrol> patrols;
    public static final int SIMULATION_TIME_UNIT = 10; // 1 sec in real time it's 10 sec in simulation
    public static final int EVENTS_PROBABILITY_IN_TIME = 60 / SIMULATION_TIME_UNIT;
    public static final int FIRE_PROBABILITY_IN_TIME = 15 / SIMULATION_TIME_UNIT;
    public static final int INCREASED_PROBABILITY = 10;
    public static final int INCREASED_PROBABILITY_NIGHT = 15;
    public static final int PROBABILITY_OF_MORTALITY = 10;

    public SimulationData(){}

    public SimulationData(Integer simulationTime, List<Incident> incidents, List<Event> events, List<Patrol> patrols) {
        this.simulationTime = simulationTime;
        this.incidents = incidents;
        this.events = events;
        this.patrols = patrols;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Patrol> getPatrols() { return this.patrols; }

    public Integer getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(Integer simulationTime) {
        this.simulationTime = simulationTime;
    }
}
