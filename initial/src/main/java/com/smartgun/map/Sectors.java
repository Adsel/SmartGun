package com.smartgun.map;

import java.awt.*;
import java.util.List;

public class Sectors {
    private List<Sector> sectors;
    public Sectors(Map map) {
        createSectors(map);
    }

    public void createSectors(Map map){
        sectors.add(new Sector(1,SectorType.RED,new Point(0,0),new Point(30,10)));
        sectors.add(new Sector(2,SectorType.YELLOW,new Point(31,0),new Point(60,10)));
        sectors.add(new Sector(3,SectorType.YELLOW,new Point(0,11),new Point(30,20)));
        sectors.add(new Sector(4,SectorType.GREEN,new Point(31,11),new Point(60,20)));
        sectors.add(new Sector(5,SectorType.GREEN,new Point(0,21),new Point(30,30)));
        sectors.add(new Sector(1,SectorType.YELLOW,new Point(31,21),new Point(60,30)));
    }
}
