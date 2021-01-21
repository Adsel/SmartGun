package com.smartgun.model.policeman;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.smartgun.model.map.Map;
import com.smartgun.model.map.Sector;
import com.smartgun.model.map.ShortestPathBFS;
import com.smartgun.model.policeman.interfaces.IPatrol;

public class Patrol implements IPatrol {

    private String id;
    // Jeden smartwatch na patrol
    private SmartWatch smartWatch;
    // Jeden samochód z nawigacją na patrol
    private Navigation navigation;

    // Dwóch policjantów na patrol, każdy ma jeden pistolet, ale tylko jeden ma pistolet
    private Policeman headPoliceman;
    private Policeman youngerPoliceman;

    private Point target;
    private State state;
    private Map map;
    private Sector sector;
    private Stack<Point> currentPathToDrive;
    private Point lastObservePoint;

    // TODO: posiadać pistolety
    public enum State {
        OBSERVE,
        INTERVENTION,
        BACK_TO_PATROL,
        BACKUP
    }

    public Patrol(
            String id,
            SmartWatch smartWatch,
            Navigation navigation,
            Policeman headPoliceman,
            Policeman youngerPoliceman,
            Map map,
            Sector sector
     //      , X connector
    ) {
     //   smartWatch.addConnectior(connector);
        this.id = id;
        this.smartWatch = smartWatch;
        this.navigation = navigation;
        this.headPoliceman = headPoliceman;
        this.youngerPoliceman = youngerPoliceman;
        this.map = map;
        this.sector = sector;
        this.state = State.OBSERVE;
        this.lastObservePoint = smartWatch.getCoordinates();
    }

    @Override
    public void setTarget(Point point, Patrol.State state) {
        this.target = point;
        this.state = state;
    }

    private void setUpCurrentPathToDrive(Point point){
        ShortestPathBFS shortestPathBFS = new ShortestPathBFS(this.map);
        Point patrolCurrentPoint = this.getCoordinates();

        ShortestPathBFS.Coordinate source =
                new ShortestPathBFS
                        .Coordinate(
                        patrolCurrentPoint.x,
                        patrolCurrentPoint.y);

        ShortestPathBFS.Coordinate destination = new ShortestPathBFS.Coordinate(point.x, point.y);

        List<Point> reversedPath = shortestPathBFS.solve(source,destination);
        Collections.reverse(reversedPath);

        currentPathToDrive = new Stack<>();
        currentPathToDrive.addAll(reversedPath);
    }

    public void goToInterventionAsBackup(Point point){
        this.setState(State.BACKUP);
        target = point;
        setUpCurrentPathToDrive(point);
    }

    public void goToIntervention(Point point){
        this.setState(State.INTERVENTION);
        target = point;
        setUpCurrentPathToDrive(point);
    }

    public void sendToObserve(){
        this.setState(State.OBSERVE);
        target = lastObservePoint;
        setUpCurrentPathToDrive(lastObservePoint);
    }
    public void move() {
        if (this.target == null && this.state == State.OBSERVE) {
            drawNewTarget();
            setUpCurrentPathToDrive(this.target);
        }
        try {
            smartWatch.setCoordinates(currentPathToDrive.pop());

        }catch (EmptyStackException e){
            target = null;
        }
        if(state == State.OBSERVE){
            lastObservePoint = smartWatch.getCoordinates();
        }
    }

    private void drawNewTarget(){
        Point currentPosition = this.smartWatch.getCoordinates();
        Direction direction = drawAvailableDirection(currentPosition);
        this.target = findTargetPointForDirection(currentPosition, direction);
    }

    private Point findTargetPointForDirection(Point startingPosition, Direction direction){
        List<Point> list = getRouteWithinSectorForDirection(startingPosition, direction);
        for (Point point : list){
            if(isCross(point)){
                return point;
            }
        }

        return list.get(list.size() - 1);
    }

    private List<Point> getRouteWithinSectorForDirection(Point startingPosition, Direction direction){
        List<Point> pointList = new ArrayList<>();
        Point currentPoint = new Point(startingPosition.x + direction.x, startingPosition.y + direction.y);

        while (sector.isInSector(currentPoint) && !map.isWall(currentPoint.x, currentPoint.y)){
            pointList.add(currentPoint);
            currentPoint = new Point(currentPoint.x + direction.x, currentPoint.y + direction.y);
        }

        if (pointList.size() == 0) {
            pointList.add(startingPosition);
        }

        return pointList;
    }

    private Direction drawAvailableDirection(Point currentPosition){
        List<Direction> directions = availableDirections(currentPosition);

        return directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()));
    }

    private List<Direction> availableDirections(Point currentPosition){

        return Arrays.stream(Direction.values()).filter(direction ->
             !map.isWall(currentPosition.x + direction.x, currentPosition.y + direction.y)
         ).collect(Collectors.toList());
    }

    private boolean isCross(Point currentPosition){
        return availableDirections(currentPosition).size() > 2;
    }

    public Point getCoordinates(){
        return this.smartWatch.getCoordinates();
    }

    public void setCoordinates(Point point){
        this.smartWatch.setCoordinates(point);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
// TODO: CYKLICZNIE POBIERAJ DANE OD SV
    //public void action
}
