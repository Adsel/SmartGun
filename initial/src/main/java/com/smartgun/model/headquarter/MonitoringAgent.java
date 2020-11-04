package com.smartgun.model.headquarter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.smartgun.model.policeman.*;

import com.smartgun.model.headquarter.interfaces.IMonitoringAgent;

public class MonitoringAgent implements IMonitoringAgent{
    private List<SmartWatch> smartWatches;

    public MonitoringAgent() {
        this.smartWatches = new ArrayList();
    }

    //we'd see if its works, if not i would check equals method
    @Override
    public Point coordinatesData() {
        Point coordinates = null;

        for (SmartWatch sw : smartWatches) {
            if (sw.getFired()) {
                coordinates = sw.sendGeolocalization();
            }
        }
        return coordinates;
    }

    public void addSmartWatch(SmartWatch smartWatch) {
        this.smartWatches.add(smartWatch);
    }

    //sth not clr
    @Override
    public String interventionData(SmartWatch smartWatch) {
            if (smartWatch.getFired()){
                return "There was a shooting!";
            } else {
                return "Patrol has intervened. " +
                        "No one is hurt. It went smoothly.";
            }
    }

    //can get smartWatches which had shot
    @Override
    public List<SmartWatch> smartWatchesShootData() {
        List<SmartWatch> smartWatchesWhichHadShot = new ArrayList<>();
        for (SmartWatch smartWatch : smartWatches) {
            if (smartWatch.getFired()) {
                smartWatchesWhichHadShot.add(smartWatch);
            }
        }
        return smartWatchesWhichHadShot;
    }

    public List<SmartWatch> getSmartWatches() {
        return smartWatches;
    }

    @Override
    public String toString() {
        return "MonitoringAgent:" +
                "smartWatches=" + smartWatches;
    }
}
