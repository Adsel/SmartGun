package com.smartgun.shared;

import com.smartgun.model.simulation.InitialData;

public class Data {
    public static boolean isUser = false;
    public static InitialData data;

    public static void setupData(InitialData data) {
        if (data.getIsRandomMap()) {
            Data.randMap();
        }
    }

    private static void randMap() {

    }
}
