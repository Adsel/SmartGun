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
    private Integer[] approachProbablity;
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

    public Integer[] getApproachProbablity() { return this.approachProbablity; }

    public boolean getIsDayAndNightSystem() { return this.isDayAndNightSystem; }


    public boolean isRandomMap() {
        return isRandomMap;
    }

    public void setRandomMap(boolean randomMap) {
        isRandomMap = randomMap;
    }

    public void setPatrolsCount(Integer patrolsCount) {
        this.patrolsCount = patrolsCount;
    }

    public void setPatrolsPerDistrict(Integer[] patrolsPerDistrict) {
        this.patrolsPerDistrict = patrolsPerDistrict;
    }

    public void setAmbulancesCount(Integer ambulancesCount) {
        this.ambulancesCount = ambulancesCount;
    }

    public void setPatrolRadius(Integer patrolRadius) {
        this.patrolRadius = patrolRadius;
    }

    public void setInterventionProbablity(Integer[] interventionProbablity) {
        this.interventionProbablity = interventionProbablity;
    }

    public void setNightInterventionProbablity(Integer[] nightInterventionProbablity) {
        this.nightInterventionProbablity = nightInterventionProbablity;
    }

    public void setInterventionDuration(Integer[] interventionDuration) {
        this.interventionDuration = interventionDuration;
    }

    public void setShootingProbablity(Integer[] shootingProbablity) {
        this.shootingProbablity = shootingProbablity;
    }

    public void setInterventionToShootingProbablity(Integer[] interventionToShootingProbablity) {
        this.interventionToShootingProbablity = interventionToShootingProbablity;
    }

    public void setShootingDuration(Integer[] shootingDuration) {
        this.shootingDuration = shootingDuration;
    }

    public void setAccuratePolicemanShootProbablity(Integer accuratePolicemanShootProbablity) {
        this.accuratePolicemanShootProbablity = accuratePolicemanShootProbablity;
    }

    public void setAccuratePolicemanShootProbablityNight(Integer accuratePolicemanShootProbablityNight) {
        this.accuratePolicemanShootProbablityNight = accuratePolicemanShootProbablityNight;
    }

    public void setAccurateAggressorShootProbablity(Integer accurateAggressorShootProbablity) {
        this.accurateAggressorShootProbablity = accurateAggressorShootProbablity;
    }

    public void setAccurateAggressorShootProbablityNight(Integer accurateAggressorShootProbablityNight) {
        this.accurateAggressorShootProbablityNight = accurateAggressorShootProbablityNight;
    }

    public void setDayAndNightSystem(boolean dayAndNightSystem) {
        isDayAndNightSystem = dayAndNightSystem;
    }

    public void setApproachProbablity(Integer[] approachProbablity) {
        this.approachProbablity = approachProbablity;
    }

    private String stringBuilder(Integer[] integers) {
        String json = "[";
        for (int i = 0; i < integers.length; i++) {
            if (i != 0) {
                json += ", " + integers[i];
            } else {
                json += integers[i];
            }
        }
        json += "]";

        return json;
    }

    public String toString() {
        return "{" +
                    "\'patrolsCount\':" +  this.getPatrolsCount() + "," +
                     "\'isRandomMap\':" +  this.getIsRandomMap() + "," +
                    "\'patrolsPerDistrict\':" + stringBuilder(getPatrolsPerDistrict()) + "," +
                    "\'ambulancesCount\':" + this.getAmbulancesCount() + "," +
                    "\'patrolRadius\':" + this.getPatrolRadius() + "," +
                    "\'interventionProbablity\':" + stringBuilder(getInterventionProbablity()) + "," +
                    "\'nightInterventionProbablity\':" + stringBuilder(getNightInterventionProbablity()) + "," +
                    "\'interventionDuration\':" + stringBuilder(getInterventionDuration()) + "," +
                    "\'shootingProbablity\':" + stringBuilder(getShootingProbablity()) + "," +
                    "\'interventionToShootingProbablity\':" + stringBuilder(getInterventionToShootingProbablity()) + "," +
                    "\'shootingDuration\':" + stringBuilder(getShootingDuration()) + "," +
                    "\'accuratePolicemanShootProbablity\':" + this.getAccuratePolicemanShootProbablity() + "," +
                    "\'accuratePolicemanShootProbablityNight\':" + this.getAccuratePolicemanShootProbablityNight() + "," +
                    "\'accurateAggressorShootProbablity\':" + this.getAccurateAggressorShootProbablity() + "," +
                    "\'accurateAggressorShootProbablityNight\':" + this.getAccurateAggressorShootProbablityNight() + "," +
                    "\'approachProbablity\':" + stringBuilder(getApproachProbablity()) + "," +
                    "\'isDayAndNightSystem\':" + this.getIsDayAndNightSystem()
                + "}";
    }
}
