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
    private Point ambulanceBasePosition;

    // INPUT PARAM: countOfPatrols
    // INPUT PARAM: ambulanceBasePosition
    public MainAgent(
            MonitoringAgent monitoringAgent,
            Integer countOfPatrols,
            Point ambulanceBasePosition
        ) {
        this.monitoringAgent = monitoringAgent;
        this.ambulanceBasePosition = ambulanceBasePosition;
        this.policePatrols = generatePatrols(countOfPatrols);
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

    @Override
    public List<Patrol> generatePatrols(Integer countOfPatrols){
        List<Patrol> generatedPatrols = new ArrayList<>();
            if (countOfPatrols != null) {
            for (int i = 0; i < countOfPatrols; i++) {
                Navigation navigation = new Navigation();
                SmartWatch smartWatch = new SmartWatch(
                        new Point(
                                (int)this.ambulanceBasePosition.getX(),
                                (int)this.ambulanceBasePosition.getY()
                        ),
                        navigation
                );

                this.monitoringAgent.addSmartWatch(smartWatch);

                generatedPatrols.add(
                        new Patrol(
                                smartWatch,
                                navigation,
                                new Policeman(true),
                                new Policeman(false)
                                // TODO WHEN X WILL BE ADDED: X connector;
                        )
                );
            }
        }

        return generatedPatrols;
    }
}
