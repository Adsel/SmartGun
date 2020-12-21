package com.smartgun.model.map;

import java.awt.*;


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
}
