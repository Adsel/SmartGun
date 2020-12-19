package com.smartgun.data;

import com.smartgun.model.map.Map;

public class MapData {
    private Map map;

    public MapData(){}

    public MapData(Map map) {
        this.map = map;
    }

    public String getMapAsString() { return map.toString(); }

    public Map getMap() {
        return map;
    }

}
