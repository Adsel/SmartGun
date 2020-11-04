package com.smartgun.model.headquarter;

import com.smartgun.model.policeman.Patrol;

import java.awt.*;
import java.util.Objects;

public class Ambulance {
    private int id;
    private Patrol patrol;
    private Point point; //lokalizacja ambulansu, domyślnie ustawiana na jeden punkt na mapie, zmienia się po dodaniu patrolu

    public Ambulance(int id, Patrol patrol, Point point) {
        this.id = id;
        this.patrol = patrol;
        this.point = point;
    }

    public Ambulance(int id, Point point) {
        this.id = id;
        this.point = point;
    }

    public Patrol getPatrol() {
        return patrol;
    }

    public void setPatrol(Patrol patrol) {
        this.patrol = patrol;
    }

    public int getId() {
        return id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ambulance ambulance = (Ambulance) o;
        return id == ambulance.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
