package com.smartgun.model.simulation;

public class InitialData {
    private boolean isRandomMap;
    private Integer patrolsCount;
    private Integer[] patrolsPerDistrict;
    private Integer ambulancesCount;
    private Integer patrolRadius;
    private Integer[] interventionProbablity;
    private Integer[] nightInterventionProbablity;
    private Integer[] interventionDuration;
    private Integer[] shootingProbablity;
    private Integer[] interventionToShootingProbablity;
    private Integer[] shootingDuration;
    private Integer accuratePolicemanShootProbablity;
    private Integer accuratePolicemanShootProbablityNight;
    private Integer accurateAggressorShootProbablity;
    private Integer accurateAggressorShootProbablityNight;
    private boolean isDayAndNightSystem;

    public InitialData() { }

    public boolean getIsRandomMap() {
        return this.isRandomMap;
    }

    public Integer getPatrolsCount() { return this.patrolsCount; }

    public Integer[] getPatrolsPerDistrict() { return this.patrolsPerDistrict; }

    public Integer getAmbulancesCount() { return this.ambulancesCount; }

    public Integer getPatrolRadius() { return this.patrolRadius; }

    public Integer[] getInterventionProbablity() { return this.interventionProbablity; }

    public Integer[] getNightInterventionProbablity() { return this.nightInterventionProbablity; }

    public Integer[] getInterventionDuration() { return this.interventionDuration; }

    public Integer[] getShootingProbablity() { return this.shootingProbablity; }

    public Integer[] getInterventionToShootingProbablity() { return this.interventionToShootingProbablity; }

    public Integer[] getShootingDuration() { return this.shootingDuration; }

    public Integer getAccuratePolicemanShootProbablity() { return this.accuratePolicemanShootProbablity; }

    public Integer getAccuratePolicemanShootProbablityNight() { return this.accuratePolicemanShootProbablityNight; }

    public Integer getAccurateAggressorShootProbablity() { return this.accurateAggressorShootProbablity; }

    public Integer getAccurateAggressorShootProbablityNight() { return this.accurateAggressorShootProbablityNight; }

    public boolean getIsDayAndNightSystem() { return this.isDayAndNightSystem; }


    public String toString() {
        return "{" +
                    "\'patrolsCount\':" +  this.getPatrolsCount() + "," +
                     "\'isRandomMap\':" +  this.getIsRandomMap() + "," +
                    "\'patrolsPerDistrict\':" + this.getPatrolsPerDistrict() + "," +
                    "\'ambulancesCount\':" + this.getAmbulancesCount() + "," +
                    "\'patrolRadius\':" + this.getPatrolRadius() + "," +
                    "\'interventionProbablity\':" + this.getInterventionProbablity() + "," +
                    "\'nightInterventionProbablity\':" + this.getNightInterventionProbablity() + "," +
                    "\'interventionDuration\':" + this.getInterventionDuration() + "," +
                    "\'shootingProbablity\':" + this.getShootingProbablity() + "," +
                    "\'interventionToShootingProbablity\':" + this.getInterventionToShootingProbablity() + "," +
                    "\'shootingDuration\':" + this.getShootingDuration() + "," +
                    "\'accuratePolicemanShootProbablity\':" + this.getAccuratePolicemanShootProbablity() + "," +
                    "\'accuratePolicemanShootProbablityNight\':" + this.getAccuratePolicemanShootProbablityNight() + "," +
                    "\'accurateAggressorShootProbablity\':" + this.getAccurateAggressorShootProbablity() + "," +
                    "\'accurateAggressorShootProbablityNight\':" + this.getAccurateAggressorShootProbablityNight() + "," +
                    "\'isDayAndNightSystem\':" + this.getIsDayAndNightSystem()
                + "}";
    }
}
