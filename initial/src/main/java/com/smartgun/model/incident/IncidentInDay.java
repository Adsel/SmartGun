package com.smartgun.model.incident;

import com.smartgun.model.incident.interfaces.IIncident;
import com.smartgun.shared.Data;

import java.awt.*;

public class IncidentInDay extends Incident implements IIncident {
    int startTime;
    int duration;
    Point place;

    public IncidentInDay(int startTime, int duration, Point place) {
        super(startTime,duration,place);
        this.startTime = startTime;
        this.duration = duration;
        this.place = place;
    }

    @Override
    public Integer[] interventionProbability() {
        return Data.data.getInterventionProbablity();
    }

    @Override
    public Integer[] probabilityOfArrival() {
        return Data.data.getApproachProbablity();
    }
}
