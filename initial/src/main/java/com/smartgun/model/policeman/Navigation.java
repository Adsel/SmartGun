package com.smartgun.model.policeman;

import java.awt.Point;

import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.Policeman;
import com.smartgun.model.policeman.Navigation;

public class Navigation {
    // Nv zawiera SW
    private Patrol patrol;

    public Navigation() {
    }

    // GETS REQUESTS ABOUT INTERVENTION (INTERVENTION/BACKUP) FROM MC (MC->X->PATROL->NAVIGATION)
    public void setDestinationLocation(Point point, Patrol.State state) {
        this.patrol.setTarget(point, state);
    }

    public void setPatrol(Patrol patrol) {
        this.patrol = patrol;
    }

    // GETS DATA FROM SmartWatch (SW)
    public void getDataFromSmartWatch() {

    }
}