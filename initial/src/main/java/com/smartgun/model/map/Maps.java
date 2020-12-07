package com.smartgun.model.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.FileSystems;
import java.util.Arrays;

public class Maps {
    private static String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    public static String FILE_DIRECTORY = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR + "static" + FILE_SEPARATOR + "maps" + FILE_SEPARATOR;
    private static Integer DEFAULT_MAP_INDEX = 1;
    public static List<Map> AVAILABLE_MAPS = new ArrayList<>(Arrays.asList(
            new Map(
                    "map1.txt",
                    new ArrayList(Arrays.asList(
                    ))
            ),
            new Map(
                    "map2.txt",
                    new ArrayList<>(Arrays.asList(
                            new Sector(1,SectorType.RED, new Point(0,0), new Point(30,39)),
                            new Sector(2,SectorType.RED, new Point(0,31), new Point(30,78)),
                            new Sector(3,SectorType.YELLOW, new Point(31,0), new Point(51,78)),
                            new Sector(4,SectorType.GREEN, new Point(52,0), new Point(80,39)),
                            new Sector(5,SectorType.GREEN, new Point(52,40), new Point(80,78))
                    ))
            ),
            new Map(
                    "map3.txt",
                    new ArrayList(Arrays.asList(
                    ))
            )
    ));

    public static Map getMap() {
        return Maps.AVAILABLE_MAPS.get(Maps.DEFAULT_MAP_INDEX);
    }

    public static Map getRandMap() {
        Integer index = (int)(Math.random() * Maps.AVAILABLE_MAPS.size());
        return Maps.AVAILABLE_MAPS.get(index);
    }
}