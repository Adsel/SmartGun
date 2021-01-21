package com.smartgun.model.incident;

import java.awt.*;

public class Shooting extends Incident {
    private int shootingDuration;

    public Shooting(Incident incident, int shootingDuration) {
        super(incident.getStartTime(), incident.getDurationTime(),incident.getIncidentLocalization(),
                incident.getIncidentType());
        this.shootingDuration = shootingDuration;
    }

    public Shooting(int startTime, Point incidentPoint, IncidentType incidentType, int shootingDuration) {
        super(startTime, shootingDuration, incidentPoint, incidentType);
        this.shootingDuration = shootingDuration;
    }

    public int getShootingDuration() {
        return shootingDuration;
    }

    public void setShootingDuration(int shootingDuration) {
        this.shootingDuration = shootingDuration;
    }
}
