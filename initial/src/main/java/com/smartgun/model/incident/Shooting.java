package com.smartgun.model.incident;

import com.smartgun.model.map.Sector;

import java.awt.*;

public class Shooting extends Incident {
    private int shootingDuration;

    public Shooting(Incident incident, int shootingDuration, Sector sector) {
        super(incident.getStartTime(), incident.getDurationTime(),incident.getIncidentLocalization(),
                incident.getIncidentType(), sector);
        this.shootingDuration = shootingDuration;
    }

    public Shooting(int startTime, Point incidentPoint, IncidentType incidentType, int shootingDuration, Sector sector) {
        super(startTime, shootingDuration, incidentPoint, incidentType, sector);
        this.shootingDuration = shootingDuration;
    }

    public int getShootingDuration() {
        return shootingDuration;
    }

    public void setShootingDuration(int shootingDuration) {
        this.shootingDuration = shootingDuration;
    }
}
