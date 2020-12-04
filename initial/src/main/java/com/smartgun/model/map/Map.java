package com.smartgun.model.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.List;

public class Map {

    private char[][] map;
    private String mapPath;
    private List<Sector> sectors;

    public Map(
            String mapName,
            List<Sector> sectors
    ){
        this.mapPath = (new File(Maps.FILE_DIRECTORY + mapName)).getAbsolutePath();
        this.sectors = sectors;
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
    }
    public char[][] getMap() {
        return map;
    }

    public void printMap(){
        for (int i = 0; i < this.map.length; i++){
            System.out.println(this.map[i]);
        }
    }

    public String toString(){
        String mapString = "" + this.mapPath;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                mapString += map[i][j];
            }
            mapString += "\n";
        }

        return mapString;
    }
}
