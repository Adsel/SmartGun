package com.smartgun.model.headquarter.interfaces;

import java.awt.Point;
import java.util.List;

import com.smartgun.model.map.Map;
import com.smartgun.model.map.Sector;
import com.smartgun.model.policeman.Navigation;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.policeman.SmartWatch;

public interface IHeadQuarter {
    List<Patrol> getPatrols();
    void sendPatrolTo(Point point);
    void generatePatrols(Integer[] integers, Integer patrolsCount);
    Point generatePatrolPosition(Sector sector, Map map);
    void addPatrol(SmartWatch sw, Navigation nv, Sector sector);
}
