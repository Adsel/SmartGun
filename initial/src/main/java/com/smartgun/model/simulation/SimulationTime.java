package com.smartgun.model.simulation;

import com.smartgun.shared.Data;

import java.util.Calendar;
import java.util.Random;

public class SimulationTime {
    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int TIME_IN_REALITY_PER_SECOND = 1; // in minutes
    private static String WORD_SEPARATOR = "-";
    private static String FIELD_SEPARATOR = "_";
    private static String TIME_SEPARATOR = ":";

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

    public String recieveMonthWithPrefix() {
        return getMonth() >= 10 ? String.valueOf(getMonth()) : "0" + getMonth();
    }

    public String recieveDayWithPrefix() {
        return getDay() >= 10 ? String.valueOf(getDay()) : "0" + getDay();
    }

    public String recieveHourWithPrefix() {
        return hours >= 10 ? String.valueOf(hours) : "0" + hours;
    }

    public String recieveMinutesWithPrefix() {
        return minutes >= 10 ? String.valueOf(minutes) : "0" + minutes;
    }

    public String recieveSecondsWithPrefix() {
        return seconds >= 10 ? String.valueOf(seconds) : "0" + seconds;
    }

    public static String recieveTimeString() {
        SimulationTime time = Data.serverSimulationData.getSimulationTime();

        return time.getYear() + WORD_SEPARATOR +
                time.recieveMonthWithPrefix() + WORD_SEPARATOR +
                time.recieveDayWithPrefix() + FIELD_SEPARATOR +
                time.recieveHourWithPrefix() + WORD_SEPARATOR +
                time.recieveMinutesWithPrefix() + WORD_SEPARATOR +
                time.recieveSecondsWithPrefix();
    }

    public static String receiveDateString() {
        SimulationTime time = Data.serverSimulationData.getSimulationTime();

        return time.getYear() + WORD_SEPARATOR +
                time.recieveMonthWithPrefix() + WORD_SEPARATOR +
                time.recieveDayWithPrefix();
    }

    public static String receiveTimeString() {
        SimulationTime time = Data.serverSimulationData.getSimulationTime();

        return time.recieveHourWithPrefix() + TIME_SEPARATOR +
                time.recieveMinutesWithPrefix() + TIME_SEPARATOR +
                time.recieveSecondsWithPrefix();
    }
}
