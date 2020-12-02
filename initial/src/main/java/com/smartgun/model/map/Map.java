package com.smartgun.model.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Map {

    public static String sourceFilePath = "src\\main\\resources\\static\\maps\\";
    private char[][] map;
    private String mapPath;

    public Map(String mapName){
        File file = new File(sourceFilePath + mapName);
        this.mapPath = file.getAbsolutePath();
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
        for (int i=0; i < this.map.length; i++){
            System.out.println(this.map[i]);
        }
    }
}
