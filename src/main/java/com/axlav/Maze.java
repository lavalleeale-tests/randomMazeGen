package com.axlav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Maze {
    public boolean[][] publicMaze;
    public int[] playerPos;
    public String wallString;
    public String emptyString;
    /**
     * Generates a <code>boolean[][]</code> with specified dimensions
    * @param x The height of the maze to be generated
    * @param y The width of the maze to be generated
    **/
    public void generate(int x, int y){
        boolean[][] generatedMaze = new boolean[x][y];
        for (int row = 0; row < x; row++) {
            for (int column = 0; column < y; column++) {
                generatedMaze[row][column] = true;
            }
        }
        generatedMaze[1][0] = false;
        this.publicMaze = generatedMaze;
    }
    /**
     * Generates and set to <code>false</code> a path to solve any given <code>boolean[][]</code> maze
     **/
    public void genInitialSolution(){
        long startTime =  System.currentTimeMillis();
        int curx = 1;
        int cury = 0;
        int newSpotX;
        int newSpotY;

        while (true) {
            if (System.currentTimeMillis()>startTime+5000) {
                startTime =  System.currentTimeMillis();
                curx = 1;
                cury = 0;
                this.generate(this.publicMaze.length-1,this.publicMaze[0].length-1);
                System.out.println("reset");
            }
            Random r = new Random();
            int result = r.nextInt(4);
            switch (result) {
                case 0:
                    newSpotX = curx;
                    newSpotY = cury+1;
                    break;
                case 1:
                    newSpotX = curx;
                    newSpotY = cury-1;
                    break;
                case 2:
                    newSpotX = curx+1;
                    newSpotY = cury;
                    break;
                case 3:
                    newSpotX = curx-1;
                    newSpotY = cury;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value");
            }
            if (newSpotX == this.publicMaze.length-2 && newSpotY == this.publicMaze[0].length-1) {
                return;
            }
            if(validSpot(this.publicMaze, newSpotX, newSpotY)) {
                this.publicMaze[newSpotX][newSpotY] = false;
                curx = newSpotX;
                cury = newSpotY;
            }
        }
    }
    /**
     * Checks if a point is valid to solve until the end of the maze
     * @param maze The maze to check for valid point in
     * @param x The x value of the initial point
     * @param y The y value of the initial point
     **/
    public boolean validSpot(boolean[][] maze, int x, int y) {
        int curx;
        int cury;
        boolean[][] curMaze;
        boolean[][] newMaze;
        ArrayList<Object[]> toTry = new ArrayList<>();
        ArrayList<Object[]> tried = new ArrayList<>();
        toTry.add(new Object[]{x,y,maze.clone()});
        whileLoop:
        while (true) {
            //Check if no points left to try
            if (toTry.size()==0) {
                return false;
            }
            //Assign current position and maze
            curx = (int) toTry.get(toTry.size()-1)[0];
            cury = (int) toTry.get(toTry.size()-1)[1];
            curMaze = (boolean[][]) toTry.get(toTry.size()-1)[2];

            //Check if current points have already been
            for (Object[] point : tried) {
                if (Arrays.equals(point, new Object[]{curx, cury, curMaze})) {
                    continue whileLoop;
                }
            }
            //Append current point to array of tried points
            tried.add(new Object[]{curx, cury, curMaze});
            //Check if there is too long a backlog of points
            if (tried.size()> maze.length*maze[0].length/5) {
                toTry = new ArrayList<>();
                tried = new ArrayList<>();
                toTry.add(new Object[]{x,y,maze.clone()});
            }
            //Check if the current position is the final point
            if (curx == maze.length-2 && cury == maze[0].length-1) {
                return true;
            }
            toTry.remove(toTry.size()-1);

            //Check if the current spot does not intersect with other paths
            if (!validSingleSpot(curx,cury,curMaze)) {
                continue;
            }
            //Create new maze for new points
            newMaze = new boolean[curMaze.length][];
            for ( int i = 0; i < newMaze.length; i++ ) {
                newMaze[i] = curMaze[i].clone();
            }
            //Remove wall from current point
            newMaze[curx][cury] = false;
            //Add new points to array of points to try
            toTry.add(new Object[]{curx, cury-1, newMaze});
            toTry.add(new Object[]{curx-1, cury, newMaze});
            toTry.add(new Object[]{curx, cury+1, newMaze});
            toTry.add(new Object[]{curx+1, cury, newMaze});
        }
    }
    /**
     * Checks if a point has only only one <code>false</code> directly next to it
     * @param maze The maze to check a valid point in
     * @param x The x value of the initial point
     * @param y The y value of the initial point
     **/
    public boolean validSingleSpot(int x, int y, boolean[][] maze) {
        int emptyAdjacent = 0;
        try {
            if (!maze[x][y]) {
                return false;
            }
            if (!maze[x + 1][y]) {
                emptyAdjacent++;
            }
            if (!maze[x][y + 1]) {
                emptyAdjacent++;
            }
            if (!maze[x][y - 1]) {
                emptyAdjacent++;
            }
            if (!maze[x - 1][y]) {
                emptyAdjacent++;
            }

        } catch (ArrayIndexOutOfBoundsException ignored) {
            return false;
        }
        return emptyAdjacent == 1;
    }
    /**
     * Takes a <code>boolean[][]</code> maze and outputs a <code>String[]</code> representation
     * @return The <code>String[]</code> representation
     **/
    public char[][] genCharMaze(int playerX, int playerY) {
        boolean[][] maze = this.publicMaze;
        char[][] charMaze = new char[maze.length][];
        char[] rowChars;
        maze[maze.length-2][maze[0].length-1]= maze[maze.length-2][maze[0].length-2];
        for (int x = 0; x < maze.length; x++) {
            rowChars = new char[maze[x].length+1];
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y])     {
                    rowChars[y] = 'x';
                } else {
                    rowChars[y] = 'o';
                }
            }
            rowChars[maze[x].length]  = '\n';
            charMaze[x] = rowChars;
        }
        charMaze[playerX][playerY] = 'X';
        return charMaze;
    }
    public String[] printableMaze(char[][] maze) {
        String[] printableMaze = new String[maze.length];
        //Make sure the final exit of the maze is not a wall
        for (int x = 0; x < maze.length; x++) {
            printableMaze[x] = new String(maze[x]).replaceAll("x",this.wallString).replaceAll("o",this.emptyString);
        }
        return printableMaze;
    }
}
