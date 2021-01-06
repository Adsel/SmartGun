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

    public List<Point> recieveHospitalList(){return currentMap.recieveStringMap().recieveHospitalList();}

    public List<Point> recievePoliceOfficeList(){return currentMap.recieveStringMap().recievePoliceOfficeList();}

}
