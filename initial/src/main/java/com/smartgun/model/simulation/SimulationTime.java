package com.smartgun.model.simulation;

import java.util.Calendar;
import java.util.Random;

public class SimulationTime {
    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int TIME_IN_REALITY_PER_SECOND = 15; // in minutes

    private int hours;
    private int minutes;
    private int seconds;
    private Calendar date;

    public SimulationTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.date = Calendar.getInstance();
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

    public int getYear() {
        return date.get(Calendar.YEAR);
    }

    public int getMonth() {
        return date.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public static SimulationTime recieveStartingTime() {
        Random generator = new Random();
        return new SimulationTime(
                generator.nextInt(SimulationTime.HOURS_IN_DAY) + 1,
                generator.nextInt(SimulationTime.MINUTES_IN_HOUR) + 1,
                0
        );
    }

    public void increaseSimulationTime() {
        this.minutes += TIME_IN_REALITY_PER_SECOND;
        if (this.minutes >= MINUTES_IN_HOUR) {
            this.minutes -= MINUTES_IN_HOUR;
            this.hours += 1;

            if (this.hours > HOURS_IN_DAY) {
                this.hours = 1;
                this.date.add(Calendar.DATE, 1);
            }
        }
    }
}
