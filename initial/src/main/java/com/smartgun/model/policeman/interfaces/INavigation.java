package com.smartgun.model.policeman.interfaces;

import java.awt.Point;
import com.smartgun.model.policeman.Patrol;

public interface INavigation {
    public void setDestinationLocation(Point point, Patrol.State state);
    public void setPatrol(Patrol patrol);
    public void getDataFromSmartWatch();
}
