package com.smartgun.model.incident;

import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Patrol;

import java.awt.Point;

public class Incident {
    public static enum IncidentType {
        INTERVENTION_TURNING_INTO_SHOOTING,
        SHOOTING,
        INTERVENTION
    }

    private Point incidentLocalization;
    private Sector sector;
    private int startTime;
    private int endTime;
    private IncidentType incidentType;
    private int durationTime;
    private Patrol patrol;
    private boolean willBeShooting;
    private int turningIntoShootingTime;

    public Incident(){}

    public Incident(
            int startTime, int durationTime, Point incidentPoint,
            IncidentType incidentType, Sector sector
    ) {
        this.startTime = startTime;
        this.endTime = startTime + durationTime;
        this.incidentLocalization = incidentPoint;
        this.incidentType = incidentType;
        this.durationTime = durationTime;
        this.sector = sector;
        this.willBeShooting = false;
        turningIntoShootingTime = 0;
    }

    public Incident(
            int startTime, int durationTime, Point incidentPoint,
            IncidentType incidentType, Sector sector, int turningIntoShootingTime
    ) {
        this.startTime = startTime;
        this.endTime = startTime + durationTime;
        this.incidentLocalization = incidentPoint;
        this.incidentType = incidentType;
        this.durationTime = durationTime;
        this.sector = sector;
        this.willBeShooting = true;
        this.turningIntoShootingTime = turningIntoShootingTime;
    }

    public Point getIncidentLocalization() {
        return incidentLocalization;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public IncidentType getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(IncidentType incidentType) {
        this.incidentType = incidentType;
    }

    public int getDurationTime() {
        return durationTime;
    }
    
    public void setPatrolToIncident(Patrol patrol) {
        this.patrol = patrol;
    }

    public Patrol recieveChoosedPatrol() { return patrol; }

    public void backPatrol() {
        if (this.patrol != null) {
            this.patrol.sendToObserve();
        }
    }

    public Integer getSectorId() {
        return this.sector.getId();
    }

    public boolean isWillBeShooting() {
        return willBeShooting;
    }

    public void setWillBeShooting(boolean willBeShooting) {
        this.willBeShooting = willBeShooting;
    }

    public int getTurningIntoShootingTime() {
        return turningIntoShootingTime;
    }

    public void setTurningIntoShootingTime(int turningIntoShootingTime) {
        this.turningIntoShootingTime = turningIntoShootingTime;
    }
}
