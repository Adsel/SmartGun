package com.smartgun.model.policeman;

import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.interfaces.IMainAgent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainAgent implements IMainAgent {
    private MonitoringAgent monitoringAgent;
    private List<Patrol> policePatrols;

    public MainAgent(MonitoringAgent monitoringAgent, List<Patrol> policePatrols) {
        this.monitoringAgent = monitoringAgent;
        this.policePatrols = policePatrols;
    }

    @Override
    public List<SmartWatch> smartWatchesWhichNeedsBackup() {
        return monitoringAgent.smartWatchesShootData();
    }

    @Override
    public Patrol choosePatrolToInterence() {
        List<Patrol> patrolsWhoObserve = new ArrayList<>();
        Random randomize = new Random();

        for (Patrol patrol : policePatrols) {
            if (Patrol.State.OBSERVE.equals(patrol)){
                patrolsWhoObserve.add(patrol);
            }
        }

        List<SmartWatch> smartWatchesWhichNeedsBackup = smartWatchesWhichNeedsBackup();

        int randomPatrol = randomize.nextInt(patrolsWhoObserve.size());
        // if i.d check all of them
//        if (!smartWatchesWhichNeedsBackup.isEmpty()) {
//            return patrolsWhoObserve.get(randomPatrol);
//        }
        return patrolsWhoObserve.get(randomPatrol);
    }

    @Override
    public Point coordinatesToSendAmbulance() {
        return monitoringAgent.coordinatesData();
    }
}
