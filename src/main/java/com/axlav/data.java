package com.axlav;

public class data {
    private static boolean[][] initialSolution;
    public static void setInitialSolution(boolean[][] printableMaze) {
        data.initialSolution = printableMaze;
    }
    public static boolean[][] getInitialSolution() {
        return initialSolution;
    }
    private static String wallString;
    public static void setWallString(String wallString) {
        data.wallString = wallString;
    }
    public static String getWallString() {
        return wallString;
    }
    private static String emptyString;
    public static void setEmptyString(String emptyString) {
        data.emptyString = emptyString;
    }
    public static String getEmptyString() {
        return emptyString;
    }
    private static String charMaze;
    public static void setCharMaze(String charMaze) {
        data.charMaze = charMaze;
    }
    public static String getCharMaze() {
        return charMaze;
    }
}
