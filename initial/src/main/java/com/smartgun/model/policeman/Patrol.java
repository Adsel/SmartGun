package com.smartgun.model.policeman;

import java.awt.Point;

import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.Policeman;
import com.smartgun.model.policeman.Navigation;

public class Patrol {
    // Jeden smartwatch na patrol
    private SmartWatch smartWatch;
    // Jeden samochód z nawigacją na patrol
    private Navigation navigation;
    // Dwóch policjantów na patrol, każdy ma jeden pistolet, ale tylko jeden ma pistolet
    private Policeman headPoliceman;
    private Policeman youngerPoliceman;


    // TODO: posiadać pistolety
    public enum State {
        OBSERVE,
        INTERVENTION,
        BACK_TO_PATROL,
        BACKUP
    }

    public Patrol(
            SmartWatch smartWatch,
            Navigation navigation,
            Policeman headPoliceman,
            Policeman youngerPoliceman
     //      , X connector
    ) {
     //   smartWatch.addConnectior(connector);
        this.smartWatch = smartWatch;
        this.navigation = navigation;
        this.headPoliceman = headPoliceman;
        this.youngerPoliceman = youngerPoliceman;
    }

    public void setTarget(Point point, Patrol.State state) {
        //
    }


    // TODO: CYKLICZNIE POBIERAJ DANE OD SV
    //public void action
}
