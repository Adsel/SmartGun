package com.smartgun.model.map;

import java.awt.*;


public class Sector {
    private int id;
    private SectorType sectorType;
    private Point leftUpperCorner;
    private Point rightBottomCorner;
//    private int[][] sectorMap;
//    private Map map;

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

//    public void setSectorMap(Map map){
//      int x = this.rightBottomCorner.x - this.leftUpperCorner.x;
//      int y = this.rightBottomCorner.y - this.leftUpperCorner.y;
//
//      this.sectorMap = new int[x][y];
//
//      for (int i = 0; i < x; i++){
//          for (int j = 0; j < y; j++){
//              this.sectorMap[i][j] = map.getMapOfInt()[i][j];
//          }
//      }
//    }
//
//    public int[][] getSectorMap(){return this.sectorMap;}
}
