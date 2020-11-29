package com.smartgun.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Map {
    private char[][] map;
    private int rows = 1;

    public Map(String path){
        loadMap(path);
    }

    public char[][] getMap() {
        return map;
    }

    public void loadMap(String path){
        try {
            //TODO universal method to get path of file

            //String path1 = "C:\\Projects\\SmartGun\\initial\\src\\main\\resources\\static\\samplemap.txt";
            Scanner input = new Scanner(new BufferedReader(new FileReader(path)));

            //getting number of rows from matrix map
            while (input.hasNextLine()){
                this.rows++;
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
