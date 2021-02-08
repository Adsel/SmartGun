package com.smartgun.model.map;

import com.smartgun.shared.Data;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Sector {
    private int id;
    private SectorType sectorType;
    private Point leftUpperCorner;
    private Point rightBottomCorner;

    public Sector(int id, SectorType sectorType, Point leftUpperCorner, Point rightBottomCorner) {
        this.id = id;
        this.sectorType = sectorType;
        this.leftUpperCorner = leftUpperCorner;
        this.rightBottomCorner = rightBottomCorner;

    }

    public int getId() {
        return id;
    }

    public SectorType getSectorType() {
        return sectorType;
    }

    public Point getLeftUpperCorner() {
        return leftUpperCorner;
    }

    public Point getRightBottomCorner() {
        return rightBottomCorner;
    }

    public Integer getSectorTypeValue() { return sectorType.ordinal(); }

    public List<Point> getSectorPoints(Map map) {
        return map.recieveMapPoints().stream().filter(this::isInSector
        ).collect(Collectors.toList());
    }

    public boolean isInSector(Point point){
        return  point.x < this.getRightBottomCorner().y && point.x >= this.leftUpperCorner.y
        && point.y < this.getRightBottomCorner().x && point.y >= this.leftUpperCorner.x;
    }

    public Point generateIncidentLocalization() {
        char[][] map = Data.serverSimulationData.getMap().getMap();
        Random generator = new Random();
        int x;
        int y;
        do {
            x = generator.nextInt((int) this.rightBottomCorner.getX() - (int) this.leftUpperCorner.getX()) + (int) this.leftUpperCorner.getX();
            y = generator.nextInt((int) this.rightBottomCorner.getY() - (int) this.leftUpperCorner.getY()) + (int) this.leftUpperCorner.getY();
        } while (map[x] != null && map[x][y] != Map.WALL_CHARACTER);

        return new Point(y, x);
    }
}
