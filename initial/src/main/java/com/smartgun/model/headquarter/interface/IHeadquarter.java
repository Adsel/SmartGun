package com.smartgun.model.policeman.interfaces;

import java.awt.*;
import java.util.List;
import com.smartgun.model.policeman.Patrol;

public interface IHeadquarter{

    void changePatrolRadius(int patrolId);
    void changePatrolPerDistrict(int districtId, int patrols);
    void changeAmbulancePerDistrict(int districtId, int ambulances);

    void sendPatrolTo(Point point);

    List<Patrol> generatePatrols(Integer countOfPatrols);
}
