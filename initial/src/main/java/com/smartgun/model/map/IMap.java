package com.smartgun.model.map;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

public interface IMap {
    void loadMap() throws FileNotFoundException;
    char[][] getMap();
    void printMap();
    int[][] recieveMapOfint();
    int recieveNumbersOfRows();
    int recieveNumberOfColumns();
    boolean isExplored(int row, int col);
    boolean isWall(int row, int col);
    boolean isValidLocation(int row, int col);
    void setPath(List<Point> path);
    void printPath();

}
