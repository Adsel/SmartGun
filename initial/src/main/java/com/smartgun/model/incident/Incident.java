package com.smartgun.model.incident;

import java.awt.Point;
import java.util.Random;

public class Incident {
    enum IncidentType {
        INTERVENTION_TURNING_INTO_SHOOTING,
        SHOOTING,
        INTERVENTION
    }

    private Point incidentPoint;
    private boolean willBeFire;
    private int startTime;
    private int endTime;

    Integer policeShooting;
    Integer agressorShooting;

    // TYP INCYDENTU


//    Integer[] interventionProbability, Integer policeShooting, Integer agressorShooting
    public Incident(int startTime, int durationTime, Point incidentPoint) {
        // TODO: random position
        this.incidentPoint = new Point(1, 1);
        this.startTime = startTime;
        this.endTime = startTime + endTime;
        this.incidentPoint = incidentPoint;
    }

    public boolean isFiredIncident() {
        return this.willBeFire;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public Point getIncidentPoint(){ return this.incidentPoint; }

}
