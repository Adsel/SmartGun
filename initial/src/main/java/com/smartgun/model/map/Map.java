package com.smartgun.model.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Map {
    public static String[] AVAILABLE_MAP_NAMES = {
        "samplemap.txt"
    };

    private char[][] map;
    private String mapPath;

    public Map(String path){
        this.mapPath = path;
    }

    public char[][] getMap() {
        return map;
    }

    public void loadMap(int number){
        try {
            this.mapPath = new File(AVAILABLE_MAP_NAMES[number]).getAbsolutePath();

            Scanner input = new Scanner(new BufferedReader(new FileReader(this.mapPath)));

            //getting number of rows from matrix map
            int rows = 1;
            while (input.hasNextLine()){
                rows++;
                input.nextLine();
            }
            //set numbers of rows in char matrix
            this.map = new char[rows][];

            //join every single line as char array finally is char array double matrix
            while (input.hasNextLine()) {
                for (int i = 0; i < rows; i++) {
                    char[] temp = input.nextLine().toCharArray();
                    this.map[i] = temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
