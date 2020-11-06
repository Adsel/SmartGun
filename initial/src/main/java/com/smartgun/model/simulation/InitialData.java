package com.smartgun.model.simulation;

public class InitialData {
    private boolean isRandomMap;

    public InitialData() {
    }

    public InitialData(boolean isRandomMap) {
        this.isRandomMap = isRandomMap;
    }

    public boolean getIsRandomMap() {
        return this.isRandomMap;
    }

    public String toString() {
        return "{" +
                     "\'isRandomMap\':" +  this.getIsRandomMap()
                + "}";
    }
}
