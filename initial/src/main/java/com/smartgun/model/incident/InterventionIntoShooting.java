package com.smartgun.model.incident;

import java.awt.*;

public class InterventionIntoShooting extends Incident {
    private boolean willBeShooting;
    private int turningToShootingTime;

    public InterventionIntoShooting(
            int startTime, int durationTime, Point incidentPoint, IncidentType incidentType
//            int turningToShootingTime
    ){
        super(startTime, durationTime, incidentPoint, incidentType);
        willBeShooting = checkIfWillBeShooting();
//        this.turningToShootingTime = turningToShootingTime;
    }

    public boolean isWillBeShooting() {
        return willBeShooting;
    }

    public void setWillBeShooting(boolean willBeShooting) {
        this.willBeShooting = willBeShooting;
    }

    public int getTurningToShootingTime() {
        return turningToShootingTime;
    }

    public void setTurningToShootingTime(int turningToShootingTime) {
        this.turningToShootingTime = turningToShootingTime;
    }

    private boolean checkIfWillBeShooting() {
        return Math.random() < 0.5;
    }
}
