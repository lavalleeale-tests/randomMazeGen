package com.axlav;

public class data {
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
    private static int[] playerPos;
    public static void setPlayerPos(int[] playerPos) {
        data.playerPos = playerPos;
    }
    public static int[] getPlayerPos() {
        return playerPos;
    }
    private static boolean[][] maze;
    public static void setMaze(boolean[][] maze) {data.maze = maze;}
    public static boolean[][] getMaze() {
        return maze;
    }
}
