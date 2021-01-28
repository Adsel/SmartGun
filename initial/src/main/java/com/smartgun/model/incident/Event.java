package com.smartgun.model.incident;

import com.smartgun.model.simulation.SimulationTime;

import java.awt.*;

public class Event {
    private String description;
    private EventType type;
    private String date;
    private String time;
    private int x;
    private int y;
    private Integer sectorID;
    private Integer durationTime;

    public enum EventType {
        POLICEMAN_MISSED_FIRE,
        AGGRESSOR_MISSED_FIRE,
        POLICEMAN_HURT,
        POLICEMAN_KILLED,
        AGGRESSOR_HURT,
        AMBULANCE_SENT,
        SUPPORT_SENT,
        INTERVENTION_FINISHED,
        SHOOTING_FINISHED,
        INTERVENTION_STARTED,
        SHOOTING_STARTED,
        INTERVENTION_TURNED_INTO_SHOOTING,
        PATROL_WAS_CHOSEN_TO_INTERVENTION,
        INCIDENT_DURATION
    }

    public Event() {
    }

    public Event(Point point, Integer sectorID , String description, EventType type) {
        this.description = description;
        this.type = type;
        this.date = SimulationTime.receiveDateString();
        this.time = SimulationTime.receiveTimeString();;
        this.x = (int) point.getX();
        this.y = (int) point.getY();
        this.sectorID = sectorID;
    }

    public Event(Point point, Integer sectorID , String description, EventType type, Integer durationTime) {
        this(point, sectorID, description, type);
        this.durationTime = durationTime;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void sectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "description='" + description + '\'' +
                ", type=" + type +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", sectorName='" + sectorID + '\'' +
                '}';
    }
}
