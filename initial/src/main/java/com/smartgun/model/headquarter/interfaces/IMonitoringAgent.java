package com.smartgun.model.headquarter.interfaces;

import java.awt.Point;
import java.util.List;
import com.smartgun.model.policeman.SmartWatch;

public interface IMonitoringAgent {
    Point coordinatesData();
    String interventionData(SmartWatch smartWatch);
    List<SmartWatch> smartWatchesShootData();
}
