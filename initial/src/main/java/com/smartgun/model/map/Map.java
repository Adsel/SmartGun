package com.smartgun.model.map;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Map implements IMap {

    public static final String GREEN = "\033[0;32m";
    public static final String RESET = "\033[0m";

    public static final char HOSPITAL_CHARACTER = 'H';
    public static final char POLICE_CHARACTER = 'P';
    public static final char WALL_CHARACTER = '#';

    private char[][] map;
    private String mapPath;
    private List<Sector> sectors;
    private int[][] mapOfInt;
    private boolean[][] visited;
    private List<Point> path;
    List<Point> hospitalList;
    List<Point> policeOfficeList;

    public Map(
            String mapName,
            List<Sector> sectors
    ){
        this.mapPath = (new File(Maps.FILE_DIRECTORY + mapName)).getAbsolutePath();
        this.sectors = sectors;
        this.hospitalList = new ArrayList<>();
        this.policeOfficeList = new ArrayList<>();
    }

    public void loadMap() throws FileNotFoundException {
        int xSize = 0;

        try {
            Scanner input = new Scanner(new BufferedReader(new FileReader(this.mapPath)));
            while (input.hasNextLine()) {
                xSize++;
                input.nextLine();
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.map = new char[xSize][];

        try {
            Scanner input = new Scanner(new BufferedReader(new FileReader(this.mapPath)));
            int i = 0;

            while (input.hasNextLine()) {
                this.map[i] = input.nextLine().toCharArray();
                i++;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int xLength = this.map.length;
        int yLength = this.map[0].length;

        this.hospitalList = new ArrayList<>();
        this.policeOfficeList = new ArrayList<>();

        //int[][] mapInt = new int[xLength][yLength];
        this.mapOfInt = new int[xLength][yLength];

        //Set 0 1 int[][] map from char[][] map
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {

                if (this.map[i][j] == WALL_CHARACTER) {
                    this.mapOfInt[i][j] = 0;
                } else if (this.map[i][j] == HOSPITAL_CHARACTER) {
                    hospitalList.add(new Point(i, j));
                    this.mapOfInt[i][j] = 0;
                } else if (this.map[i][j] == POLICE_CHARACTER) {
                    policeOfficeList.add(new Point(i, j));
                    this.mapOfInt[i][j] = 0;
                } else {
                    this.mapOfInt[i][j] = 1;
                }
            }
        }
        visited = new boolean[xLength][yLength];
    }

    public char[][] getMap() {
        return map;
    }

    public void printMap() {
        for (int i = 0; i < this.map.length; i++) {
            System.out.println(this.map[i]);
        }
    }

    public int[][] getMapOfInt() {
        return this.mapOfInt;
    }

    public int getNumbersOfRows() {
        return this.mapOfInt.length;
    }

    public int getNumberOfColumns() {
        return this.mapOfInt[0].length;
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col) {
        return mapOfInt[row][col] == 0;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int row, int col) {
        if (row < 0 || row >= getNumbersOfRows() || col < 0 || col >= getNumberOfColumns()) {
            return false;
        }
        return true;
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }

    int a;
    int b;

    //This method is just for check if the path is correctly solved
    public void printPath() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 60; j++) {
                a = i;
                b = j;
                if (new Point(a, b).equals(path.stream().filter(point -> point.x == a && point.y == b).findFirst().orElse(null))) {
                    System.out.print(GREEN + mapOfInt[i][j] + RESET);
                } else {
                    System.out.print(mapOfInt[i][j]);
                }
            }
            System.out.println();
        }
    }

    public String toString(){
        String mapString = "";
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                mapString += map[i][j];
            }
            mapString += "\n";
        }
        return mapString;
    }

    public List<Sector> getSectors() { return sectors; }
}
