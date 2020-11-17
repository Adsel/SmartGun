package com.smartgun.model.simulation;

public class InitialData {
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
    private Boolean isDayAndNightSystem;
    private Boolean isRandomMap;


    public InitialData() {
        System.out.println("BAD");
    }

    public InitialData(
            Integer patrolsCount, Integer[] patrolsPerDistrict,
            Integer ambulancesCount, Integer patrolRadius, Integer[] interventionProbablity,
            Integer[] nightInterventionProbablity, Integer[] interventionDuration,
            Integer[] shootingProbablity, Integer[] interventionToShootingProbablity,
            Integer[] shootingDuration, Integer accuratePolicemanShootProbablity, Integer accuratePolicemanShootProbablityNight,
            Integer accurateAggressorShootProbablity, Integer accurateAggressorShootProbablityNight,
            Boolean isDayAndNightSystem, Boolean isRandomMap
    ) {
        System.out.println(patrolsCount);
        System.out.println(patrolsPerDistrict);
        System.out.println(ambulancesCount);
        System.out.println(patrolRadius);
        System.out.println(interventionProbablity);
        this.isRandomMap = isRandomMap;
        this.patrolsCount = patrolsCount;
        this.patrolsPerDistrict = patrolsPerDistrict;
        this.ambulancesCount = ambulancesCount;
        this.patrolRadius = patrolRadius;
        this.interventionProbablity = interventionProbablity;
        this.nightInterventionProbablity = nightInterventionProbablity;
        this.interventionDuration = interventionDuration;
        this.shootingProbablity = shootingProbablity;
        this.interventionToShootingProbablity = interventionToShootingProbablity;
        this.shootingDuration = shootingDuration;
        this.accuratePolicemanShootProbablity = accuratePolicemanShootProbablity;
        this.accuratePolicemanShootProbablityNight = accuratePolicemanShootProbablityNight;
        this.accurateAggressorShootProbablity = accurateAggressorShootProbablity;
        this.accurateAggressorShootProbablityNight = accurateAggressorShootProbablityNight;
        this.isDayAndNightSystem = isDayAndNightSystem;
        System.out.println(this);
    }

    public Boolean getIsRandomMap() {
        return this.isRandomMap;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "InitialData{" +
                "isRandomMap=" + isRandomMap +
                ", patrolsCount=" + patrolsCount +
                ", patrolsPerDistrict=" + java.util.Arrays.toString(patrolsPerDistrict) +
                ", ambulancesCount=" + ambulancesCount +
                ", patrolRadius=" + patrolRadius +
                ", interventionProbablity=" + java.util.Arrays.toString(interventionProbablity) +
                ", nightInterventionProbablity=" + java.util.Arrays.toString(nightInterventionProbablity) +
                ", interventionDuration=" + java.util.Arrays.toString(interventionDuration) +
                ", shootingProbablity=" + java.util.Arrays.toString(shootingProbablity) +
                ", interventionToShootingProbablity=" + java.util.Arrays.toString(interventionToShootingProbablity) +
                ", shootingDuration=" + java.util.Arrays.toString(shootingDuration) +
                ", accuratePolicemanShootProbablity=" + accuratePolicemanShootProbablity +
                ", accuratePolicemanShootProbablityNight=" + accuratePolicemanShootProbablityNight +
                ", accurateAggressorShootProbablity=" + accurateAggressorShootProbablity +
                ", accurateAggressorShootProbablityNight=" + accurateAggressorShootProbablityNight +
                ", isDayAndNightSystem=" + isDayAndNightSystem +
                '}';
    }
}
