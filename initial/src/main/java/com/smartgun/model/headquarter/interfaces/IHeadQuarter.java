package com.smartgun.model.headquarter.interfaces;

import java.awt.Point;
import java.util.List;
import com.smartgun.model.policeman.Patrol;

public interface IHeadQuarter {
    void changePatrolRadius(int patrolId);
    void changePatrolPerDistrict(int districtId, int patrols);
    void changeAmbulancePerDistrict(int districtId, int ambulances);
    void sendPatrolTo(Point point);
    List<Patrol> generatePatrols(Integer countOfPatrols);
}
