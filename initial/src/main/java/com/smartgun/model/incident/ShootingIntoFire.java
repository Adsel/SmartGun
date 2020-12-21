package com.smartgun.model.incident;

import java.awt.*;

public class ShootingIntoFire extends Incident {
    private boolean willBeFire;
    private int turningToShootingTime;

    public ShootingIntoFire(){}

    public ShootingIntoFire(
            int startTime, int durationTime,
            Point incidentPoint, boolean willBeFire,
            int turningToShootingTime, IncidentType incidentType
    ){
        super(startTime, durationTime, incidentPoint, incidentType);
        this.willBeFire = willBeFire;
        this.turningToShootingTime = turningToShootingTime;
    }

    public boolean isWillBeFire() {
        return willBeFire;
    }

    public void setWillBeFire(boolean willBeFire) {
        this.willBeFire = willBeFire;
    }

    public int getTurningToShootingTime() {
        return turningToShootingTime;
    }

    public void setTurningToShootingTime(int turningToShootingTime) {
        this.turningToShootingTime = turningToShootingTime;
    }

}
