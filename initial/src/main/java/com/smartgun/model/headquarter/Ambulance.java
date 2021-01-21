package com.smartgun.model.headquarter;

import java.awt.Point;
import java.util.*;

import com.smartgun.model.map.Map;
import com.smartgun.model.map.ShortestPathBFS;
import com.smartgun.model.policeman.Patrol;

public class Ambulance {
    private int id;
    private Point actualPosition;
    private Point basePosition;
    private State state;
    private Stack<Point> currentPathToDrive;
    private Map map;

    public Ambulance(int id, Point actualPosition) {
        this.id = id;
        this.actualPosition = actualPosition;
    }

    public Ambulance(int id, Point actualPosition, Point basePosition, Map map) {
        this.id = id;
        this.actualPosition = actualPosition;
        this.basePosition = basePosition;
        this.state = State.WAITING;
        this.map = map;
    }

    public void move() {
        try {
            actualPosition.setLocation(currentPathToDrive.pop());

        }catch (EmptyStackException e){
            currentPathToDrive = null;
        }
    }

    private void setUpCurrentPathToDrive(Point point){
        ShortestPathBFS shortestPathBFS = new ShortestPathBFS(this.map);
        Point ambulanceCurrentPoint = this.getActualPosition();

        ShortestPathBFS.Coordinate source =
                new ShortestPathBFS
                        .Coordinate(
                        ambulanceCurrentPoint.x,
                        ambulanceCurrentPoint.y);

        ShortestPathBFS.Coordinate destination = new ShortestPathBFS.Coordinate(point.x, point.y);

        List<Point> reversedPath = shortestPathBFS.solve(source,destination);
        Collections.reverse(reversedPath);

        currentPathToDrive = new Stack<>();
        currentPathToDrive.addAll(reversedPath);
    }

    public void goToIntervention(Point point){
        this.setState(State.INTERVENTION);
        setUpCurrentPathToDrive(point);
    }

    public void backToBase(){
        this.setState(State.WAITING);
        setUpCurrentPathToDrive(basePosition);
    }

    public void setState(Ambulance.State state) {
        this.state = state;
    }

    public State getState(){
        return this.state;
    }

    public int getId() {
        return id;
    }

    public Point getActualPosition() {
        return actualPosition;
    }

    public void setActualPosition(Point actualPosition) {
        this.actualPosition = actualPosition;
    }

    public enum State{
        WAITING,
        INTERVENTION
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
