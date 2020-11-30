package com.smartgun.shared;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathAlmost{
        // Size of the maze
    private int MAZE_SIZE_X;
    private int MAZE_SIZE_Y;

    public ShortestPathAlmost(int MAZE_SIZE_X,int MAZE_SIZE_Y) {
        this.MAZE_SIZE_X = MAZE_SIZE_X;
        this.MAZE_SIZE_Y = MAZE_SIZE_Y;
    }

    /* A utility function to print
    solution matrix sol[MAZE_SIZE_X][MAZE_SIZE_X] */
    private void printSolution(char sol[][])
    {
        for (int i = 0; i < MAZE_SIZE_X; i++) {
            for (int j = 0; j < MAZE_SIZE_Y; j++) {
                System.out.print(
                        " " + sol[i][j] + " ");
            }
            System.out.println();
        }
    }

    /* A utility function to check
        if x, y is valid index for MAZE_SIZE_X*MAZE_SIZE_X maze */
    private boolean isSafe(char maze[][], int x, int y)
    {
        // if (x, y outside maze) return false
        return (
                x >= 0 && x < MAZE_SIZE_X && y >= 0
                        && y < MAZE_SIZE_Y && maze[x][y] == '1'
        );
    }

    /* This function solves the Maze problem using
    Backtracking. It mainly uses solveMazeUtil()
    to solve the problem. It returns false if no
    path is possible, otherwise return true and
    prints the path in the form of 1s. Please note
    that there may be more than one solutions, this
    function prints one of the feasible solutions.*/
    public boolean solveMaze(char maze[][])
    {
        char sol[][] = new char[MAZE_SIZE_X][MAZE_SIZE_Y];

        if (solveMazeUtil(maze, 0, 0, sol) == false) {
            System.out.print("Solution doesn't exist");
            return false;
        }

        printSolution(sol);
        return true;
    }

    /* A recursive utility function to solve Maze
    problem */
    private boolean solveMazeUtil(char maze[][], int x, int y,
                                  char sol[][])
    {
        // if (x, y is goal) return true
        if (x == MAZE_SIZE_X - 1 && y == MAZE_SIZE_Y - 1 && maze[x][y] == '1') {
            sol[x][y] = '1';
            return true;
        }

        // Check if maze[x][y] is valid
        if (isSafe(maze, x, y) == true) {
            // mark x, y as part of solution path
            sol[x][y] = '1';

            /* Move forward in x direction */
            if (solveMazeUtil(maze, x + 1, y, sol))
                return true;

            /* If moving in x direction doesn't give
            solution then Move down in y direction */
            if (solveMazeUtil(maze, x, y + 1, sol))
                return true;

            /* If none of the above movements works then
            BACKTRACK: unmark x, y as part of solution
            path */
            sol[x][y] = '0';
            return false;
        }

        return false;
    }

}