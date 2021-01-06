package com.smartgun.data;

import com.smartgun.model.map.Map;

public class MapData {
    private Map stringMap;

    public MapData(){}

    public MapData(Map map) {
        this.stringMap = map;
    }

    public String getMapAsString() { return stringMap.toString(); }

    public Map recieveStringMap() {
        return stringMap;
    }

}
