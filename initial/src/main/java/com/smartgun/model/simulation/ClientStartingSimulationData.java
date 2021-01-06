package com.smartgun.model.simulation;

import com.smartgun.data.MapData;
import com.smartgun.model.map.Map;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class ClientStartingSimulationData {
    private MapData currentMap;
    private SimulationTime time;
    private boolean isDayAndNightSystem;

    public ClientStartingSimulationData() {
    }

    public ClientStartingSimulationData(MapData map, boolean isDayAndNightSystem) {
        this.currentMap = map;
        this.time = SimulationTime.recieveStartingTime();
        this.isDayAndNightSystem = isDayAndNightSystem;
    }

    public MapData getCurrentMap(){ return currentMap; }

    public List<Point> recieveHospitalList(){ return currentMap.recieveStringMap().recieveHospitalList(); }

    public List<Point> recievePoliceOfficeList(){ return currentMap.recieveStringMap().recievePoliceOfficeList(); }

    public SimulationTime getTime(){ return this.time; }

    public boolean getIsDayAndNightSystem() { return this.isDayAndNightSystem; }
}
