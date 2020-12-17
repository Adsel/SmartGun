package com.smartgun.model.simulation;

import com.smartgun.model.incident.Incident;
import com.smartgun.shared.Data;

import java.util.List;

public class SimulationData {
    //tutaj dodawać parametry, a uzupelniać w SimulationLifeService
    private String content;
    private List<Incident> incidents;

    public SimulationData(){}

    public SimulationData(String content, List<Incident> incidents) {
        this.content = content;
        this.incidents = incidents;
    }

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }
}
