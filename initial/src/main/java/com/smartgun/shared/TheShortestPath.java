package com.smartgun.shared;

import java.util.LinkedList;
import java.util.Queue;


public class TheShortestPath
{
    // Size of the maze
    private int MAZE_SIZE;

    public TheShortestPath(int MAZE_SIZE) {
        this.MAZE_SIZE = MAZE_SIZE;
    }

    /* A utility function to print
    solution matrix sol[MAZE_SIZE][MAZE_SIZE] */
    private void printSolution(int sol[][])
    {
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++)
                System.out.print(
                        " " + sol[i][j] + " ");
            System.out.println();
        }
    }

    /* A utility function to check
        if x, y is valid index for MAZE_SIZE*MAZE_SIZE maze */
    private boolean isSafe(int maze[][], int x, int y)
    {
        // if (x, y outside maze) return false
        return (
                x >= 0 && x < MAZE_SIZE && y >= 0
                && y < MAZE_SIZE && maze[x][y] == 1
        );
    }

    /* This function solves the Maze problem using
    Backtracking. It mainly uses solveMazeUtil()
    to solve the problem. It returns false if no
    path is possible, otherwise return true and
    prints the path in the form of 1s. Please note
    that there may be more than one solutions, this
    function prints one of the feasible solutions.*/
    public boolean solveMaze(int maze[][])
    {
        int sol[][] = new int[MAZE_SIZE][MAZE_SIZE];

        if (solveMazeUtil(maze, 0, 0, sol) == false) {
            System.out.print("Solution doesn't exist");
            return false;
        }

        printSolution(sol);
        return true;
    }

    /* A recursive utility function to solve Maze
    problem */
    private boolean solveMazeUtil(int maze[][], int x, int y,
                          int sol[][])
    {
        // if (x, y is goal) return true
        if (x == MAZE_SIZE - 1 && y == MAZE_SIZE - 1
                && maze[x][y] == 1) {
            sol[x][y] = 1;
            return true;
        }

        // Check if maze[x][y] is valid
        if (isSafe(maze, x, y) == true) {
            // mark x, y as part of solution path
            sol[x][y] = 1;

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
            sol[x][y] = 0;
            return false;
        }

        return false;
    }
}
