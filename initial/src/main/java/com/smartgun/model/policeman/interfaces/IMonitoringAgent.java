package com.smartgun.model.policeman.interfaces;

import com.smartgun.model.policeman.SmartWatch;

import java.awt.*;
import java.util.List;

public interface IMonitoringAgent {
    Point coordinatesData();
    String interventionData(SmartWatch smartWatch);
    List<SmartWatch> smartWatchesShootData();
}
