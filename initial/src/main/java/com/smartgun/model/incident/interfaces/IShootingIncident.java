package com.smartgun.model.incident.interfaces;

public interface IShootingIncident {
    Integer[] interventionProbability();
    Integer[] probabilityOfArrival();
    Integer[] shootingProbability();
    Integer[] shootingDuration();
    Integer accurateOfPolicemanShoot();
    Integer accurateOfAggressorShoot();
    Integer nmbrOfInjuredPeople();
}
