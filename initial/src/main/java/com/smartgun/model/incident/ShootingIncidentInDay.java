package com.smartgun.model.incident;

import com.smartgun.model.incident.interfaces.IIncident;
import com.smartgun.model.incident.interfaces.IShootingIncident;
import com.smartgun.shared.Data;

import java.awt.*;

public class ShootingIncidentInDay extends Incident implements IShootingIncident {
    int startTime;
    int duration;
    Point place;

    public ShootingIncidentInDay(int startTime, int duration, Point place) {
        super(startTime, duration, place);
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

    @Override
    public Integer[] shootingProbability() {
        return Data.data.getShootingProbablity();
    }

    @Override
    public Integer[] shootingDuration() {
        return Data.data.getShootingDuration();
    }

    @Override
    public Integer accurateOfPolicemanShoot() {
        return Data.data.getAccuratePolicemanShootProbablity();
    }

    @Override
    public Integer accurateOfAggressorShoot() {
        return Data.data.getAccurateAggressorShootProbablity();
    }

    @Override
    public Integer nmbrOfInjuredPeople() {
        return 1;
    }
}
