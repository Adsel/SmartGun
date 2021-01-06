package com.smartgun.model.incident;

import java.awt.*;

public class Shooting extends Incident {
    private int shootingDuration;

    public Shooting(int startTime, int durationTime, Point incidentPoint, IncidentType incidentType, int shootingDuration) {
        super(startTime, durationTime, incidentPoint, incidentType);
        this.shootingDuration = shootingDuration;
    }

    public int getShootingDuration() {
        return shootingDuration;
    }

    public void setShootingDuration(int shootingDuration) {
        this.shootingDuration = shootingDuration;
    }
}
