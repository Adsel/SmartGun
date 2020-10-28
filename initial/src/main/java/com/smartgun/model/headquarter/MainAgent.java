package com.smartgun.model.policeman;

import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.MonitoringAgent;
import com.smartgun.model.policeman.interfaces.IMainAgent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainAgent implements IMainAgent {
    private MonitoringAgent monitoringAgent;
    private List<Patrol> policePatrols;
//    private Point ambulanceBasePosition;

    // INPUT PARAM: ambulanceBasePosition
    public MainAgent(MonitoringAgent monitoringAgent, List<Patrol> patrols) {
        this.monitoringAgent = monitoringAgent;
        this.policePatrols = patrols;
    }

    @Override
    public List<SmartWatch> smartWatchesWhichNeedsBackup() {
        return monitoringAgent.smartWatchesShootData();
    }

    @Override
    public Patrol choosePatrolToInterence() {
        // 1 - available entries
        // 0 - collisions
        /* SEARCH PATROLS USING THE SHORTEST PATH ALGORITM
        int maze[][] = {
                { 1, 0, 0, 0 },
                { 1, 1, 0, 1 },
                { 0, 1, 0, 0 },
                { 1, 1, 1, 1 }
        };

        TheShortestPath rat = new TheShortestPath(maze.length);
        boolean isSolution = rat.solveMaze(maze);
        if (isSolution) {
            // solution exists we can move our patrol
        } else {
            // solution doesn't exist, patrol can't reach destination
        }
        */


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
