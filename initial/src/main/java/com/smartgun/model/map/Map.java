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
    public static final char POLICE_CHARACTER = 'S';
    public static final char WALL_CHARACTER = '#';

    private static final int WALL = 0;
    private static final int STREET = 1;

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

    public void cleanVisited() {
        int xLength = this.map.length;
        int yLength = this.map[0].length;

        visited = new boolean[xLength][yLength];
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

        int xLength = this.map[0].length;
        int yLength = this.map.length;

        //int[][] mapInt = new int[xLength][yLength];
        this.mapOfInt = new int[yLength][xLength];

        //Set 0 1 int[][] map from char[][] map
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {

                if (this.map[y][x] == WALL_CHARACTER) {
                    this.mapOfInt[y][x] = 0;
                } else if (this.map[y][x] == HOSPITAL_CHARACTER) {
                    this.hospitalList.add(new Point(x, y));
                    this.mapOfInt[y][x] = 0;
                } else if (this.map[y][x] == POLICE_CHARACTER) {
                    policeOfficeList.add(new Point(x, y));
                    this.mapOfInt[y][x] = 1;
                } else {
                    this.mapOfInt[y][x] = 1;
                }
            }
        }
        visited = new boolean[yLength][xLength];
    }

    public char[][] getMap() {
        return map;
    }

    public void printMap() {
        for (int i = 0; i < this.map.length; i++) {
            System.out.println(this.map[i]);
        }
    }

    public int[][] recieveMapOfint() {
        return this.mapOfInt;
    }

    public int recieveNumbersOfRows() {
        return this.mapOfInt.length;
    }

    public int recieveNumberOfColumns() {
        return this.mapOfInt[0].length;
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col) {
        if (row < 0 || col < 0 || row > mapOfInt.length - 1 || col > mapOfInt[0].length - 1) {
            return true;
        }
        return mapOfInt[row][col] == WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int row, int col) {
        if (row < 0 || row >= recieveNumbersOfRows() || col < 0 || col >= recieveNumberOfColumns()) {
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
        for (int x = 0; x < this.map.length; x++) {
            for (int y = 0; y < this.map[0].length; y++) {
                a = x;
                b = y;
                if (new Point(a, b).equals(path.stream().filter(point -> point.x == a && point.y == b).findFirst().orElse(null))) {
                    System.out.print(GREEN + mapOfInt[x][y] + RESET);
                } else {
                    System.out.print(mapOfInt[x][y]);
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

    public List<Sector> receiveSectors() { return sectors; }

    public List<Point> recieveHospitalList() {
        return hospitalList;
    }

    public List<Point> recievePoliceOfficeList() {
        return policeOfficeList;
    }
    //TODO: Maybe MapPoint(x,y, type) ?
    public List<Point> recieveMapPoints(){
        List<Point> list = new ArrayList<>();

        for (int x = 0; x < map.length; x++){
            for (int y = 0; y < map[x].length; y++){
                list.add(new Point(x,y));
            }
        }
        return list;
    }

}
