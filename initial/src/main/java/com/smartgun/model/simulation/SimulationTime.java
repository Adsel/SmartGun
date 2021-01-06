package com.smartgun.model.simulation;

import java.util.Random;

public class SimulationTime {
    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int SECONDS_IN_MINUTE = 60;

    private int hours;
    private int minutes;
    private int seconds;

    public SimulationTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public static SimulationTime recieveStartingTime() {
        Random generator = new Random();
        return new SimulationTime(
                generator.nextInt(SimulationTime.HOURS_IN_DAY) + 1,
                generator.nextInt(SimulationTime.MINUTES_IN_HOUR) + 1,
                0
        );
    }
}
