package com.smartgun.model.policeman.interfaces;

import java.awt.*;

public interface IHeadquarter{

    void changePatrolRadius(int patrolId);
    void changePatrolPerDistrict(int districtId, int patrols);
    void changeAmbulancePerDistrict(int districtId, int ambulances);

    void sendPatrolTo(Point point);
}
