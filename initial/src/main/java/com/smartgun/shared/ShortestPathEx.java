package com.smartgun.shared;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ShortestPathEx{ public static void main(String[] args) {
        try {
            String content = Files.readString(Paths.get("src/com/maze/samplemap.txt"), StandardCharsets.US_ASCII);
            System.out.println("CONTENT##########");
            content = content.replace('.','1');
            content = content.replace('P','0');
            content = content.replace('H','0');
            content = content.replace('#','0');
//            System.out.println(content);
            System.out.println("CHAR MAP");
            findSolution(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void findSolution(String str)
    {
        int l = str.length();
        int k = 0;

        int column = 0;
        while (str.charAt(column) != '\n') {
            column++;
        }

        int row = 0;
        for (int i = 0; i < l; i++) {
            if (str.charAt(i) == '\n') {
                row++;
            }
        }

        int tmp = row;
        if (row * column < l) {
            row = column;
        }

        char s[][] = new char[row][column];

        // converting the string map into char map
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(k < str.length()) {
                    s[i][j] = str.charAt(k);
                }
                k++;
            }
        }


        // printing the map of # and .
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (s[i][j] == 0) {
                    break;
                }
                System.out.print(s[i][j]);
            }
        }

        System.out.println();

        // ShortestPathAlmost rat = new ShortestPathAlmost(row,column);
        // boolean isSolution = rat.solveMaze(s);
        // if (isSolution) {
        //     System.out.println("AAAAAAAAAAAA");
        //     // solution exists we can move our patrol
        // } else {
        //     System.out.println("BBBBBBBBBBBBBBBBBBB");
        //     // solution doesn't exist, patrol can't reach destination
        // }
        char maze[][] = {
				{ '1', '0', '0', '0' },
				{ '1', '1', '0', '1' },
				{ '0', '1', '0', '0' },
				{ '1', '1', '1', '1' }
		};

		// !!! Problem !!!
		ShortestPathAlmost rat = new ShortestPathAlmost(4,4);
		boolean isSolution = rat.solveMaze(maze);
		if (isSolution) {
			System.out.println("AAAAAAAAAAAA");
			// solution exists we can move our patrol
		} else {
			System.out.println("BBBBBBBBBBBBBBBBBBB");
			// solution doesn't exist, patrol can't reach destination
		}
    }
}