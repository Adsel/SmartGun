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
    private Integer id;
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
    private boolean isAdditional;

    public enum State {
        OBSERVE,
        INTERVENTION,
        BACK_TO_PATROL,
        BACK_TO_BASE,
        BACKUP,
        BASE
    }

    public Patrol(
            Integer id,
            SmartWatch smartWatch,
            Navigation navigation,
            Policeman headPoliceman,
            Policeman youngerPoliceman,
            Map map,
            Sector sector,
            boolean isAdditional
    ) {
        this.id = id;
        this.smartWatch = smartWatch;
        this.navigation = navigation;
        this.headPoliceman = headPoliceman;
        this.youngerPoliceman = youngerPoliceman;
        this.map = map;
        this.sector = sector;
        this.state = isAdditional ? Patrol.State.BASE : Patrol.State.OBSERVE;
        this.lastObservePoint = smartWatch.getCoordinates();
        this.isAdditional = isAdditional;
    }

    public Integer getId() {
        return id;
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
        setUpCurrentPathToDrive(sendToIntervention(point));
    }

    public void sendToObserve(){
        if (this.isAdditional) {
            this.setState(State.BACK_TO_BASE);
            target = lastObservePoint;
            setUpCurrentPathToDrive(lastObservePoint);
        } else {
            this.setState(State.OBSERVE);
            target = lastObservePoint;
            setUpCurrentPathToDrive(lastObservePoint);
        }
    }

    public void move() {
        if (this.target == null && this.state == State.OBSERVE) {
            // NORMAL PATROLLING
            drawNewTarget();
            setUpCurrentPathToDrive(this.target);
        }

        if (this.state != State.BASE) {
            try {
                smartWatch.setCoordinates(currentPathToDrive.pop());

            } catch (EmptyStackException e) {
                target = null;
            }
        }

        if (state == State.OBSERVE){
            lastObservePoint = smartWatch.getCoordinates();
        }

        if (target == null && state == State.BACK_TO_BASE) {
            this.state = State.BASE;
        }
    }

    private void drawNewTarget(){
        Point currentPosition = this.smartWatch.getCoordinates();
        Direction direction = drawAvailableDirection(currentPosition);
        if (direction != null) {
            this.target = findTargetPointForDirection(currentPosition, direction);
        }
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

    public boolean isAdditional() {
        return isAdditional;
    }

    public void setAdditional(boolean additional) {
        isAdditional = additional;
    }

    public Point sendToIntervention(Point incidentLocalization) {
        Point destination = new Point();

        Point left = getFirstAvailablePointInDirection(incidentLocalization, Direction.LEFT);
        Point right = getFirstAvailablePointInDirection(incidentLocalization, Direction.RIGHT);
        Point up = getFirstAvailablePointInDirection(incidentLocalization, Direction.UP);
        Point down = getFirstAvailablePointInDirection(incidentLocalization, Direction.DOWN);

        List<Point> availablePoints = new ArrayList<>();
        if (left != null) {
            availablePoints.add(left);
        }

        if (right != null) {
            availablePoints.add(right);
        }

        if (up != null) {
            availablePoints.add(up);
        }

        if (down != null) {
            availablePoints.add(down);
        }

        ShortestPathBFS shortestPathBFS = new ShortestPathBFS(this.map);
        List<Point> pathLength = shortestPathBFS.solve(
                ShortestPathBFS.Coordinate.fromPoint(this.smartWatch.getCoordinates()),
                ShortestPathBFS.Coordinate.fromPoint(availablePoints.get(0))
        );

        for (int i = 1; i < availablePoints.size(); i++) {
            Point comparedPoint = availablePoints.get(i);
            List<Point> comparedPath = shortestPathBFS.solve(
                    ShortestPathBFS.Coordinate.fromPoint(this.smartWatch.getCoordinates()),
                    ShortestPathBFS.Coordinate.fromPoint(comparedPoint)
            );
            if (pathLength.size() > comparedPath.size()) {
                pathLength = comparedPath;
                destination = comparedPoint;
            }
        }

        if (destination != null) {
            System.out.println("GO TO " + destination);
            return destination;
        }
        return null;
    }

    private Point getFirstAvailablePointInDirection(Point startingPosition, Direction direction){
        Point currentPoint = new Point(startingPosition.x + direction.y, startingPosition.y + direction.x);

        System.out.println("OBECNY PUNKT " + currentPoint);
        while (map.isWall(currentPoint.y, currentPoint.x)){
            currentPoint = new Point(currentPoint.x + direction.y, currentPoint.y + direction.x);
            if(currentPoint.x >= map.recieveNumberOfColumns() - 1 ||
                    currentPoint.x < 0 || currentPoint.y < 0 ||
                    currentPoint.y >= map.recieveNumbersOfRows() - 1){
                return null;
            }
        }
        return currentPoint;
    }

    /*private Point getFirstAvailablePointForDirection(Point incidentPoint, Direction direction){
        Point currentPoint = new Point(incidentPoint.x + direction.y, incidentPoint.y + direction.x);

        while (map.isWall(currentPoint.y, currentPoint.x)
                && currentPoint.x < map.recieveNumberOfColumns() && currentPoint.x >= 0
                && currentPoint.y < map.recieveNumbersOfRows() && currentPoint.y >=0){
            currentPoint = new Point(currentPoint.x + direction.y, currentPoint.y + direction.x);
        }

        if(!map.isWall(currentPoint.y, currentPoint.x)){
            return currentPoint;
        }

        return null;
    }*/
    private List<Point> getRouteWithinSectorForDirection(Point startingPosition, Direction direction){
        List<Point> pointList = new ArrayList<>();
        Point currentPoint = new Point(startingPosition.x + direction.y, startingPosition.y + direction.x);

        while (sector.isInSector(currentPoint) && !map.isWall(currentPoint.y, currentPoint.x)){
            pointList.add(currentPoint);
            currentPoint = new Point(currentPoint.x + direction.y, currentPoint.y + direction.x);
        }

        if (pointList.size() == 0) {
            pointList.add(startingPosition);
        }

        return pointList;
    }

    private Direction drawAvailableDirection(Point currentPosition){
        List<Direction> directions = availableDirections(currentPosition);

        if (directions.size() <= 0) {
            return null;
        }
        return directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()));
    }

    private List<Direction> availableDirections(Point currentPosition){

        return Arrays.stream(Direction.values()).filter(direction ->
             !map.isWall(currentPosition.y + direction.x, currentPosition.x + direction.y)
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
