package com.smartgun.model.policeman;

import com.smartgun.model.policeman.interfaces.IHeadquarter;

import java.awt.*;
import java.util.Map;

public class Headquarter implements IHeadquarter {

    private double patrolRadius;    //promień patrolowanego obszaru jeśli uniwersalny to przechowywany tutaj, jeśli nie to w "Patrol"
    private Map<Integer, Integer>  patrolsPerDistrict;  //ilość patroli na danej dzielnicy
    private Map<Integer, Integer> ambulancesPerDistrict;   //ilość ambulansów
    private MainAgent mainAgent;


    public Headquarter(double patrolRadius, Map<Integer, Integer> patrolsPerDistrict, Map<Integer, Integer> ambulancesPerDistrict, MainAgent mainAgent) {
        this.patrolRadius = patrolRadius;
        this.patrolsPerDistrict = patrolsPerDistrict;
        this.ambulancesPerDistrict = ambulancesPerDistrict;
        this.mainAgent = mainAgent;
    }


    @Override
    public void changePatrolRadius(int patrolId) {

    }

    @Override
    public void changePatrolPerDistrict(int districtId, int patrols) {
        this.patrolsPerDistrict.put(districtId,patrols);
    }

    @Override
    public void changeAmbulancePerDistrict(int districtId, int ambulances) {
        this.ambulancesPerDistrict.put(districtId, ambulances);
    }

    @Override
    public void sendPatrolTo(Point point) {
        mainAgent.coordinatesToSendAmbulance();
    }
}
