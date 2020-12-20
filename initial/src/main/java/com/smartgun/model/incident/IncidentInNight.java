package com.smartgun.model.incident;

import com.smartgun.model.incident.interfaces.IIncident;

import java.awt.*;

public class IncidentInNight extends Incident implements IIncident {
    int startTime;
    int duration;
    Point place;

    public IncidentInNight(int startTime, int duration, Point place) {
        super(startTime, duration, place);
        this.startTime = startTime;
        this.duration = duration;
        this.place = place;
    }

    @Override
    public Integer[] interventionProbability() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Integer[] probabilityOfArrival() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
