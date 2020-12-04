package com.smartgun.model.simulation;

import com.smartgun.data.MapData;

public class ClientStartingSimulationData {
    private MapData currentMap;

    public ClientStartingSimulationData() {
    }

    public ClientStartingSimulationData(MapData map) {
        this.currentMap = map;
    }

    public MapData getCurrentMap(){ return currentMap; }

}
