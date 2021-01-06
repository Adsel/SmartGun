package com.smartgun.shared;

import com.smartgun.model.headquarter.HeadQuarter;
import com.smartgun.model.headquarter.MainAgent;
import com.smartgun.model.headquarter.interfaces.IMainAgent;
import com.smartgun.model.incident.Incident;
import com.smartgun.model.map.Map;
import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Patrol;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ServerSimulationData {
    private HeadQuarter headQuarter;
    private Map map;
    private List<Incident> incidents;

    public ServerSimulationData() {}

    public ServerSimulationData(
            List<Sector> sectors, Integer[] patrolsPerDistrict,
            Map map,
            double patrolRadius, Integer ambulancesCount, Integer patrolCount
    ) {
        IMainAgent mainAgent = new MainAgent();
        Point ambulanceBasePosition = new Point(0, 0);
        headQuarter = new HeadQuarter(
                sectors, ambulancesCount,
                patrolsPerDistrict, map,
                mainAgent,
                ambulanceBasePosition,
                patrolRadius, patrolCount
        );
        headQuarter.generatePatrols(patrolsPerDistrict, patrolCount);
        this.map = map;
        incidents = new ArrayList<>();
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
}
