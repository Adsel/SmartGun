package com.smartgun.model.simulation;

import com.smartgun.data.MapData;
import com.smartgun.model.map.Map;

import java.awt.*;
import java.util.List;

public class ClientStartingSimulationData {
    private MapData currentMap;

    public ClientStartingSimulationData() {
    }

    public ClientStartingSimulationData(MapData map) {
        this.currentMap = map;
    }

    public MapData getCurrentMap(){ return currentMap; }

    public List<Point> getHospitalList(){return currentMap.getMap().getHospitalList();}

    public List<Point> getPoliceOfficeList(){return currentMap.getMap().getPoliceOfficeList();}

}
