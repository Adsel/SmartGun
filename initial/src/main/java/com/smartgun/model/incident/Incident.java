package com.smartgun.model.incident;

import java.awt.Point;
import java.util.Random;

public class Incident {
    private Point incidentPoint;
    private boolean willBeFire;
    private int startTime;
    private int endTime;
    // TYP INCYDENTU

    public Incident(int startTime, int durationTime, Point position) {
        // TODO: random position
        this.incidentPoint = new Point(1, 1);
        this.startTime = startTime;
        this.endTime = startTime + endTime;
        this.incidentPoint = position;
    }

    public boolean isFiredIncident() {
        return this.willBeFire;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public Point getIncidentPoint(){ return this.incidentPoint; }
}
