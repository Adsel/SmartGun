package com.smartgun.model.policeman.interfaces;

import java.awt.Point;
import com.smartgun.model.policeman.Patrol;

public interface IPatrol {
    public void setTarget(Point point, Patrol.State state);
}
