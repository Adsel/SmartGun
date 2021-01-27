package com.smartgun.shared;

import com.smartgun.model.headquarter.AmbulanceBase;
import com.smartgun.model.headquarter.HeadQuarter;
import com.smartgun.model.headquarter.MainAgent;
import com.smartgun.model.headquarter.interfaces.IMainAgent;
import com.smartgun.model.incident.Event;
import com.smartgun.model.incident.Incident;
import com.smartgun.model.map.Map;
import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.simulation.SimulationTime;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ServerSimulationData {

    private HeadQuarter headQuarter;
    private AmbulanceBase ambulanceBase;
    private Map map;
    private List<Incident> incidents;
    private List<Event> events;
    private SimulationTime simulationTime;

    public ServerSimulationData() {}

    public ServerSimulationData(
            Integer[] patrolsPerDistrict,
            Map map,
            double patrolRadius, Integer ambulancesCount, Integer patrolCount
    ) {
        IMainAgent mainAgent = new MainAgent();
        Point ambulanceBasePosition = new Point(0, 0);
        headQuarter = new HeadQuarter(
                ambulancesCount,
                patrolsPerDistrict, map,
                mainAgent,
                ambulanceBasePosition,
                patrolRadius, patrolCount
        );
        headQuarter.generatePatrols(patrolsPerDistrict, patrolCount);
        this.map = map;
        this.ambulanceBase = new AmbulanceBase(map, 6);
        incidents = new ArrayList<>();
        events = new ArrayList<>();
    }


    public void addingIncidents(Incident incident) {
        incidents.add(incident);
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public Map getMap() { return map; }

    public List<Patrol> getPatrols() { return headQuarter.getPatrols(); }

    public void removeIncident(Incident incident) {
        incidents.remove(incident);
    }

    public void addEvent(Event event) {
        events.add(event);
        System.out.println("Added event " + event);
    }

    public void removeEvent(Event event) { events.remove(event); }

    public List<Event> getEvents() {
        return events;
    }

    public void restartEvents() {
        this.events = new ArrayList<>();
    }

    public SimulationTime getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(SimulationTime simulationTime) {
        this.simulationTime = simulationTime;
    }

    public void increaseSimulationTime() {
        this.simulationTime.increaseSimulationTime();
    }

    public String recieveTimeString() {
        return simulationTime.recieveTimeString();
    }

    public void moveAmbulances() {
        this.ambulanceBase.moveAmbulances();
    }

    public void movePatrols() {
        this.headQuarter.movePatrols();
    }

    public Patrol choosePatrolToIntervention(Point target){
        return this.headQuarter.choosePatrolToIntervention(target);
    }
}
