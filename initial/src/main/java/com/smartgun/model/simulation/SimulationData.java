package com.smartgun.model.simulation;

import com.smartgun.model.incident.Event;
import com.smartgun.model.incident.Incident;
import com.smartgun.shared.Data;

import java.util.List;

public class SimulationData {
    private List<Incident> incidents;
    private List<Event> events;

    public SimulationData(){}

    public SimulationData(List<Incident> incidents, List<Event> events) {
        this.incidents = incidents;
        this.events = events;
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
}
