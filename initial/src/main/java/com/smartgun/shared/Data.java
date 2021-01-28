package com.smartgun.shared;

import com.smartgun.model.headquarter.interfaces.IMainAgent;
import com.smartgun.model.map.Map;
import com.smartgun.model.map.Maps;
import com.smartgun.model.map.Sector;
import com.smartgun.model.map.SectorType;
import com.smartgun.model.policeman.Patrol;
import com.smartgun.model.simulation.InitialData;
import com.smartgun.model.simulation.SimulationTime;
import com.smartgun.shared.file.CsvInitRow;
import com.smartgun.shared.file.FileManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    // główne zmienne statyczne do zarządzania danymi
    public static boolean isUser = false;
    // poprawione dane WE - incjujące symulacją
    public static InitialData data;
    // pozostałe dane,
    public static ServerSimulationData serverSimulationData;


    // DEFAULTS
    private static Integer IS_NOT_SETUP = -1;

    private static Integer SECTOR_COUNT = 6;

    public static Integer ADDITIONAL_PATROLS = 3;

    private static Integer[] PATROLS_PER_DISTRICT = {
            3,
            2,
            1
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

    private static Integer[] SHOOTING_DURATION = {
            20,
            60
    };

    private static Integer ACCURATE_POLICEMAN_SHOOT_PROBABLITY_DAY = 70;
    private static Integer ACCURATE_POLICEMAN_SHOOT_PROBABLITY_NIGHT = 50;
    private static Integer ACCURATE_AGGRESSOR_SHOOT_PROBABLITY_DAY = 20;
    private static Integer ACCURATE_AGGRESSOR_SHOOT_PROBABLITY_NIGHT = 10;

    private static Integer[] FAIL_APPROACH_PROBABLITY = {
            4,
            6,
            8
    };

    public static void setupData(InitialData data) throws FileNotFoundException {
        List<CsvInitRow> listOfCsvInitRows = new ArrayList<>();
        // SETTING STARTING DATA
        Map map;
        if (data.getIsRandomMap()) {
            map = Maps.getRandMap();
        } else {
            map = Maps.getMap();
        }
        map.loadMap();

        // PATROLS AND AMBULANCES
        Integer[] computedPatrols = data.getPatrolsPerDistrict();
        for (int i = 0; i < computedPatrols.length; i++) {
            if (computedPatrols[i] < Data.PATROLS_PER_DISTRICT[i]) {
                computedPatrols[i] = Data.PATROLS_PER_DISTRICT[i];
            }
        }
        data.setPatrolsPerDistrict(computedPatrols);
        listOfCsvInitRows.add(new CsvInitRow("Patrols per district count", "For dangerous district [RED]", computedPatrols[0], "PCS"));
        listOfCsvInitRows.add(new CsvInitRow("Patrols per district count", "For medium-dangerous district [YELLOW]", computedPatrols[1],"PCS"));
        listOfCsvInitRows.add(new CsvInitRow("Patrols per district count", "For safety district [GREEN]", computedPatrols[2], "PCS"));

        Integer minimumPatrols = ADDITIONAL_PATROLS;
        for (Sector sector: map.receiveSectors()) {
            minimumPatrols += SectorType.valueOf(sector.getSectorType().toString()).ordinal() + 1;
        }

        if (data.getPatrolsCount() < minimumPatrols) {
            data.setPatrolsCount(minimumPatrols);
        }
        listOfCsvInitRows.add(new CsvInitRow("All patrols count", "Includes addition patrols", minimumPatrols, "PCS"));
        listOfCsvInitRows.add(new CsvInitRow("Additional patrols count", "Includes addition patrols", ADDITIONAL_PATROLS, "PCS"));

        Integer minimumAmbulances = SECTOR_COUNT;
        if (data.getAmbulancesCount() < minimumAmbulances) {
            data.setAmbulancesCount(minimumAmbulances);
        }
        // listOfCsvInitRows.add(new CsvInitRow("Ambulances count", "", minimumAmbulances));

        listOfCsvInitRows.add(new CsvInitRow("Count of sectors", "", SECTOR_COUNT, "PCS"));

        if (data.getPatrolRadius() < RADIUS_UNIT) {
            data.setPatrolRadius(RADIUS_UNIT);
        }
        // listOfCsvInitRows.add(new CsvInitRow("Range of radius unit", "", RADIUS_UNIT));

        // INTERVENTIONS
        Integer[] intervProb = data.getInterventionProbablity();
        for (int i = 0; i < intervProb.length; i++) {
            if (intervProb[i] == Data.IS_NOT_SETUP) {
                intervProb[i] = PROBABLITY_OF_INTERVATION[i];
            }
        }
        data.setInterventionProbablity(intervProb);
        listOfCsvInitRows.add(new CsvInitRow("Intervention probability in day", "For dangerous district [RED]", intervProb[0], "%"));
        listOfCsvInitRows.add(new CsvInitRow("Intervention probability in day", "For medium-dangerous district [YELLOW]", intervProb[1], "%"));
        listOfCsvInitRows.add(new CsvInitRow("Intervention probability in day", "For safety district [GREEN]", intervProb[2], "%"));

        Integer[] intervDur = data.getInterventionDuration();
        for (int i = 0; i < intervDur.length; i++) {
            if (intervDur[i] == Data.IS_NOT_SETUP) {
                intervDur[i] = INTERVATION_DURATION[i];
            }
        }
        data.setInterventionDuration(intervDur);
        listOfCsvInitRows.add(new CsvInitRow("Minimum intervention duration", "", intervDur[0], "SEC"));
        listOfCsvInitRows.add(new CsvInitRow("Maximum intervention duration", "", intervDur[1], "SEC"));

        // SHOOTINGS
        Integer[] shootingProb = data.getShootingProbablity();
        for (int i = 0; i < shootingProb.length; i++) {
            if (shootingProb[i] == Data.IS_NOT_SETUP) {
                shootingProb[i] = PROBABLITY_OF_SHOOTING[i];
            }
        }
        data.setShootingProbablity(shootingProb);
        listOfCsvInitRows.add(new CsvInitRow("Shooting probability in day", "For dangerous district [RED]", shootingProb[0], "%"));
        listOfCsvInitRows.add(new CsvInitRow("Shooting probability in day", "For medium-dangerous district [YELLOW]", shootingProb[1], "%"));
        listOfCsvInitRows.add(new CsvInitRow("Shooting probability in day", "For safety district [GREEN]", shootingProb[2], "%"));

        Integer[] shootingToInterProb = data.getInterventionToShootingProbablity();
        for (int i = 0; i < shootingToInterProb.length; i++) {
            if (shootingToInterProb[i] == Data.IS_NOT_SETUP) {
                shootingToInterProb[i] = PROBABLITY_OF_INTERVATION_TURNING_TO_SHOOTING[i];
            }
        }
        data.setInterventionToShootingProbablity(shootingToInterProb);

        Integer[] shootingDur = data.getShootingDuration();
        for (int i = 0; i < shootingDur.length; i++) {
            if (shootingDur[i] == Data.IS_NOT_SETUP) {
                shootingDur[i] = SHOOTING_DURATION[i];
            }
        }
        data.setShootingDuration(shootingDur);

        listOfCsvInitRows.add(new CsvInitRow("Shooting turns into intervention probability in day", "For dangerous district [RED]", shootingProb[0], "%"));
        listOfCsvInitRows.add(new CsvInitRow("Shooting turns into intervention probability in day", "For medium-dangerous district [YELLOW]", shootingProb[1], "%"));
        listOfCsvInitRows.add(new CsvInitRow("Shooting turns into intervention probability in day", "For safety district [GREEN]", shootingProb[2], "%"));

        // ACCURATE SHOOTS
        if (data.getAccuratePolicemanShootProbablity() == Data.IS_NOT_SETUP) {
            data.setAccuratePolicemanShootProbablity(ACCURATE_POLICEMAN_SHOOT_PROBABLITY_DAY);
        }
        listOfCsvInitRows.add(new CsvInitRow("Accurate policeman shoot probability in day", "", data.getAccuratePolicemanShootProbablity(), "%"));

        if (data.getAccurateAggressorShootProbablity() == Data.IS_NOT_SETUP) {
            data.setAccurateAggressorShootProbablity(ACCURATE_AGGRESSOR_SHOOT_PROBABLITY_DAY);
        }
        listOfCsvInitRows.add(new CsvInitRow("Accurate aggressor shoot probability in day", "", data.getAccurateAggressorShootProbablity(), "%"));

        //APROACH
        Integer[] failAproach = data.getApproachProbablity();
        for (int i = 0; i < failAproach.length; i++) {
            if (failAproach[i] == Data.IS_NOT_SETUP) {
                failAproach[i] = FAIL_APPROACH_PROBABLITY[i];
            }
        }
        data.setApproachProbablity(failAproach);

        // TODO: Aproach in listOfCsvInitRows

        // ALL PARAMS WHICH DEPENDS ON 'DAY AND NIGHT' SYSTEM
        if (data.getIsDayAndNightSystem()) {
            Integer[] intervProbNight = data.getNightInterventionProbablity();
            for (int i = 0; i < intervProbNight.length; i++) {
                if (intervProbNight[i] == Data.IS_NOT_SETUP) {
                    intervProbNight[i] = PROBABLITY_OF_INTERVATION_NIGHT[i];
                }
            }
            data.setNightInterventionProbablity(intervProbNight);
            listOfCsvInitRows.add(new CsvInitRow("Intervention probability at the night", "For dangerous district [RED]", intervProbNight[0], "%"));
            listOfCsvInitRows.add(new CsvInitRow("Intervention probability at the night", "For medium-dangerous district [YELLOW]", intervProbNight[1], "%"));
            listOfCsvInitRows.add(new CsvInitRow("Intervention probability at the night", "For safety district [GREEN]", intervProbNight[2], "%"));

            if (data.getAccurateAggressorShootProbablityNight() == Data.IS_NOT_SETUP) {
                data.setAccurateAggressorShootProbablityNight(ACCURATE_AGGRESSOR_SHOOT_PROBABLITY_NIGHT);
                listOfCsvInitRows.add(new CsvInitRow("Accurate aggressor shoot probability in night", "", data.getAccurateAggressorShootProbablityNight(), "%"));
            }

            if (data.getAccuratePolicemanShootProbablityNight() == Data.IS_NOT_SETUP) {
                data.setAccuratePolicemanShootProbablityNight(ACCURATE_POLICEMAN_SHOOT_PROBABLITY_NIGHT);
                listOfCsvInitRows.add(new CsvInitRow("Accurate policeman shoot probability in night", "", data.getAccuratePolicemanShootProbablityNight(), "%"));
            }
        }

        listOfCsvInitRows.add(new CsvInitRow("Day and Night system", "Determines if night has got another params than day", Data.data.getIsDayAndNightSystem() ? 1 : 0, "TRUE/FALSE" ));

        Data.serverSimulationData = new ServerSimulationData(
                data.getPatrolsPerDistrict(),
                map, data.getPatrolRadius(), data.getAmbulancesCount(),
                data.getPatrolsCount()
        );

        Data.data = data;

        FileManager fileManager = new FileManager();
        fileManager.exportInitToCsv("ExportedInitialData_", listOfCsvInitRows);
        Data.isUser = true;
    }
}
