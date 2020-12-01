package com.smartgun.shared;

import com.smartgun.model.simulation.InitialData;

public class Data {
    public static boolean isUser = false;
    public static InitialData data;

    // DEFAULTS
    private static Integer SECTOR_COUNT = 6;

    private static Integer ADDITIONAL_PATROLS = 3;

    private static Integer[] PATROLS_PER_DISTRICT = {
            1,
            2,
            3
    };

    private static Integer RADIUS_UNIT = 1;

    private static Integer[] PROBABLITY_OF_INTERVATION = {
            10,
            20,
            40
    };

    private static Integer[] PROBABLITY_OF_INTERVATION_NIGHT = {
            15,
            35,
            45
    };

    private static Integer[] INTERVATION_DURATION = {
            30,
            60
    };
    private static Integer[] PROBABLITY_OF_SHOOTING = {
            5,
            10,
            20
    };

    private static Integer[] PROBABLITY_OF_INTERVATION_TURNING_TO_SHOOTING = {
            5,
            12,
            35
    };

    private static Integer[] INTERVATION_DURATION = {
            20,
            60
    };

    private static Integer[] ACCURATE_POLICEMAN_SHOOT_PROBABLITY = {
            70,
            50
    };

    private static Integer[] ACCURATE_AGGRESSOR_SHOOT_PROBABLITY = {
            20,
            10
    };

    private static Integer[] SUCCESS_PATROL_APPROACH_PROBABLITY = {
            4,
            6,
            8
    };

    public static void setupData(InitialData data) {
        // SETTING STARTING DATA
        if (data.getIsRandomMap()) {
            Data.randMap();
        }

        Integer minimumPatrols = 0;
        Integer[] computedPatrols = data.getPatrolsPerDistrict();
        for (int i = 0; i < computedPatrols.length; i++) {
            if (computedPatrols[i] > Data.PATROLS_PER_DISTRICT[i]) {
                computedPatrols[i] = Data.PATROLS_PER_DISTRICT[i];
            }

            minimumPatrols += computedPatrols[i];
        }
        Integer minimumAmbulances = minimumPatrols;
        minimumPatrols += ADDITIONAL_PATROLS;
        data.setPatrolsPerDistrict(computedPatrols);

        if (data.getPatrolsCount() > minimumPatrols) {
            data.setPatrolsCount(data.getPatrolsCount());
        }

        if (data.getAmbulancesCount() > minimumAmbulances) {
            data.setPatrolsCount(data.getAmbulancesCount());
        }

        if (data.getPatrolRadius() < RADIUS_UNIT) {
            data.setPatrolRadius(RADIUS_UNIT);
        }

        if (data.getIsDayAndNightSystem()) {
            // ALL PARAMS WHICH DEPENDS ON 'DAY AND NIGHT' SYSTEM
        }



        Data.data = data;
        Data.isUser = true;
    }

    private static void randMap() {

    }
}
