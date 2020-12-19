package com.smartgun.model.map;

import java.awt.*;
import java.util.List;
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

    public List<Point> getSectorPoints(Map map) {
        return map.getMapPoints().stream().filter(this::isInSector
        ).collect(Collectors.toList());
    }

    public boolean isInSector(Point point){
        return  point.x < this.getRightBottomCorner().x && point.x >= this.leftUpperCorner.x
        && point.y < this.getRightBottomCorner().y && point.y >= this.leftUpperCorner.y;
    }
}
