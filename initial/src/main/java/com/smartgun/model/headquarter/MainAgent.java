package com.smartgun.model.headquarter;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.smartgun.model.headquarter.interfaces.IMainAgent;
import com.smartgun.model.headquarter.MonitoringAgent;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.SmartWatch;

public class MainAgent implements IMainAgent {
    private MonitoringAgent monitoringAgent;
    // private Point ambulanceBasePosition;
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

    public List<Patrol> getPolicePatrols() {
        return policePatrols;
    }

}
