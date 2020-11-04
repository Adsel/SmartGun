package com.smartgun.model.policeman;

import java.awt.Point;
import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.Policeman;
import com.smartgun.model.policeman.interfaces.INavigation;

public class Navigation implements INavigation {
    // Nv zawiera SW
    private Patrol patrol;

    public Navigation() {
    }

    // GETS REQUESTS ABOUT INTERVENTION (INTERVENTION/BACKUP) FROM MC (MC->X->PATROL->NAVIGATION)
    @Override
    public void setDestinationLocation(Point point, Patrol.State state) {
        this.patrol.setTarget(point, state);
    }

    @Override
    public void setPatrol(Patrol patrol) {
        this.patrol = patrol;
    }

    // GETS DATA FROM SmartWatch (SW)
    @Override
    public void getDataFromSmartWatch() {

    }
}
