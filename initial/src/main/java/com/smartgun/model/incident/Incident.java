package com.smartgun.model.incident;

import java.awt.Point;

public class Incident {
    public static enum IncidentType {
        INTERVENTION_TURNING_INTO_SHOOTING,
        SHOOTING,
        INTERVENTION
    }

    private Point incidentLocalization;
    private int startTime;
    private int endTime;
    private IncidentType incidentType;
    private int durationTime;

    public Incident(){}

    public Incident(
            int startTime, int durationTime, Point incidentPoint,
            IncidentType incidentType
    ) {
        this.startTime = startTime;
        this.endTime = startTime + durationTime;
        this.incidentLocalization = incidentPoint;
        this.incidentType = incidentType;
        this.durationTime = durationTime;
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
}
