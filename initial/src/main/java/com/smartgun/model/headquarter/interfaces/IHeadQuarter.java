package com.smartgun.model.headquarter.interfaces;

import java.awt.Point;
import java.util.List;

import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Navigation;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.SmartWatch;

public interface IHeadQuarter {
    List<Patrol> getPatrols();
    void sendPatrolTo(Point point);
    void generatePatrols(Integer[] integers, Integer patrolsCount);
    Point generatePatrolPosition(Sector sector);
    void addPatrol(String id, SmartWatch sw, Navigation nv);
}
