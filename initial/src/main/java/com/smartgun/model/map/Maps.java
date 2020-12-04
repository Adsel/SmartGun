package com.smartgun.model.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.FileSystems;
import java.util.Arrays;
//C:\Projects\SmartGun\initial\src\main\resources\static\maps\samplemap.txt
public class Maps {
    private static String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    public static String FILE_DIRECTORY = "initial" + Maps.FILE_SEPARATOR + "src" + Maps.FILE_SEPARATOR +
            "main" + Maps.FILE_SEPARATOR + "resources" + Maps.FILE_SEPARATOR +
            "static" + Maps.FILE_SEPARATOR + "maps" + Maps.FILE_SEPARATOR;
    private static Integer DEFAULT_MAP_INDEX = 0;
    public static List<Map> AVAILABLE_MAPS = new ArrayList<>(Arrays.asList(
            new Map(
                    "samplemap.txt",
                    new ArrayList(Arrays.asList(
                            new Sector(1, SectorType.RED, new Point(0,0), new Point(10,30)),
                            new Sector(2, SectorType.YELLOW, new Point(0,31), new Point(10,60)),
                            new Sector(3, SectorType.YELLOW, new Point(11,0), new Point(20,30)),
                            new Sector(4, SectorType.GREEN, new Point(11,31), new Point(20,60)),
                            new Sector(5, SectorType.GREEN, new Point(21,0), new Point(30,30)),
                            new Sector(6, SectorType.YELLOW, new Point(21,30), new Point(30,60))
                    ))
            )
    ));

    public static Map getMap() {
        return Maps.AVAILABLE_MAPS.get(Maps.DEFAULT_MAP_INDEX);
    }

    public static Map getMap(Integer index) {
        return Maps.AVAILABLE_MAPS.get(index);
    }
}
