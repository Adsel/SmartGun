package com.smartgun.model.headquarter.interfaces;

import java.awt.Point;
import java.util.List;
import com.smartgun.model.policeman.SmartWatch;
import com.smartgun.model.policeman.Patrol;

public interface IMainAgent {
    List<SmartWatch> smartWatchesWhichNeedsBackup();
    Patrol choosePatrolToInterence();
    Point coordinatesToSendAmbulance();
    //    List<Patrol> generatePatrols(Integer countOfPatrols);
}
