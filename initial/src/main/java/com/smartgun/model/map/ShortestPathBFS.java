package com.smartgun.model.map;

import com.smartgun.model.policeman.Patrol;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ShortestPathBFS implements IShortestPath{
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    private Map map;
    public ShortestPathBFS(Map map) {
        this.map = map;
        this.map.cleanVisited();
    }

    public List<Point> solve(Coordinate source, Coordinate destination) {
        LinkedList<Coordinate> nextToVisit = new LinkedList<>();
        Coordinate start = source;
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Coordinate cur = nextToVisit.remove();

            if (!map.isValidLocation(cur.getX(), cur.getY()) || map.isExplored(cur.getX(), cur.getY())) {
                continue;
            }

            if (map.isWall(cur.getX(), cur.getY())) {
                map.setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (destination.getX() == cur.getX() && destination.getY() ==  cur.getY()) {
                List<Point> list = new ArrayList<>();
                List<Coordinate> path = backtrackPath(cur);
                Collections.reverse(path);

                for (int i = 0; i < backtrackPath(cur).size(); i++){
                    list.add(new Point(path.get(i).getX(), path.get(i).getY()));
                }
                map.setPath(list);
                return list;
            }

            for (int[] direction : DIRECTIONS) {
                Coordinate coordinate = new Coordinate(cur.getX() + direction[0], cur.getY() + direction[1], cur);
                nextToVisit.add(coordinate);
                map.setVisited(cur.getX(), cur.getY(), true);
            }
        }
        return Collections.emptyList();
    }

    private List<Coordinate> backtrackPath(Coordinate cur) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;
        }

        return path;
    }

    public static class Coordinate {
        int x;
        int y;
        Coordinate parent;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
            this.parent = null;
        }

        public Coordinate(int x, int y, Coordinate parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        Coordinate getParent() {
            return parent;
        }

        public static Coordinate fromPoint(Point point){
            return new Coordinate(
                    point.x,
                    point.y);
        }
    }
}
