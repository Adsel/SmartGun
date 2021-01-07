package com.smartgun.model.incident;

import java.awt.*;

public class Event {
    private String description;
    private EventType type;
    private Point point;
    public enum EventType {
        MISSED_FIRE,
        POLICEMAN_HURTED,
        POLICEMAN_KILLED,
        AGGRESSOR_HURTED,
        AMBULANCE_SENT,
        SUPPORT_SENT,
        INTERVENTION_FINISHED,
        SHOOTING_FINISHED,
        INTERVENTION_STARTED,
        SHOOTING_STARTED,
        INTERVENTION_TURNED_INTO_SHOOTING
    }

    public Event() {
    }

    public Event(String description, EventType type, Point point) {
        this.description = description;
        this.type = type;
        this.point = point;
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

    public Point getPoint() { return point; }

    @Override
    public String toString() {
        return "Event{" +
                "description='" + description + '\'' +
                ", type=" + type +
                ", point=" + point +
                '}';
    }
}
